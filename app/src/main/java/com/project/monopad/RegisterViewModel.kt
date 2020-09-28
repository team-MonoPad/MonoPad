package com.project.monopad

import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.UserProfileChangeRequest
import com.project.monopad.AuthRepository
import com.project.monopad.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RegisterViewModel(application : Application) : AndroidViewModel(application) {

    interface EmailCheckListener {
        fun onSuccess(isEmailCheckSucccesful: Boolean)
        fun onFailure(message: String)
    }

    private var mAuthRepository= AuthRepository(application)
    var mRegisterListener: AuthListener? = null
    var mEmailCheckListener: EmailCheckListener? = null

    var name: String? = null
    var email: String? = null
    var password: String? = null
    var passwordCheck: String? = null

    var isEmailCheckSucccesful = false

    /*BaseViewModel*/
    private val compositeDisposable = CompositeDisposable()
    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
    /*BaseViewModel*/

    fun setRegisterListener(authListener: AuthListener) {
        this.mRegisterListener = authListener
    }

    fun setEmailCheckListener(emailCheckListener: EmailCheckListener) {
        this.mEmailCheckListener = emailCheckListener
    }

    fun createUserWithEmailAndPassword(email: String?, password: String?, passwordCheck: String?, name : String?) {
        // rxbinding..?
        if(!LoginPatternCheckUtil.isValidName(name)){
            mRegisterListener?.onFailure("이름 오류")
        }
        else if(!(LoginPatternCheckUtil.isValidEmail(email) && isEmailCheckSucccesful == true)) {
            mRegisterListener?.onFailure("이메일 중복체크 부탁")
        }
        else if(!LoginPatternCheckUtil.isValidPassword(password)) {
            mRegisterListener?.onFailure("비번 오류")
        }
        else if(!LoginPatternCheckUtil.checkPassword(password, passwordCheck)) {
            mRegisterListener?.onFailure("비번 다름")
        }
        else {
            mRegisterListener?.onStarted()
            val disposable = mAuthRepository.createUserWithEmailAndPassword(email!!, password!!, name!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(name).build()
                    mAuthRepository.getCurrentUser()!!.updateProfile(profileUpdates)

                    mRegisterListener?.onSuccess()
                }, {
                    mRegisterListener?.onFailure(it.message!!)
                })
            addDisposable(disposable)
        }
    }

    fun checkIfEmailAlreadyExist(email : String) {
        val disposable = mAuthRepository.checkIfEmailAlreadyExist(email)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ it ->
                isEmailCheckSucccesful = it
                mEmailCheckListener?.onSuccess(it)
            }, {
                mEmailCheckListener?.onFailure(it.message!!)
            })
        addDisposable(disposable)
    }

    fun onResisterButtonClick(view: View) {
        createUserWithEmailAndPassword(email, password, passwordCheck, name)
    }

    fun onEmailCheckButtonClick(view: View) {
        if (LoginPatternCheckUtil.isValidEmail(email))
            checkIfEmailAlreadyExist(email!!)
    }

/*
    private val _showErrorToast = MutableLiveData<Event<String>>()
    val showErrorToast: LiveData<Event<String>> = _showErrorToast*/
}