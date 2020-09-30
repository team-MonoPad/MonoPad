package com.project.monopad.ui.viewmodel

import android.view.View
import com.google.firebase.auth.UserProfileChangeRequest
import com.project.monopad.ui.AuthListener
import com.project.monopad.network.repository.UserRepoImpl
import com.project.monopad.ui.base.BaseViewModel
import com.project.monopad.util.LoginPatternCheckUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RegisterViewModel(private val repo : UserRepoImpl) : BaseViewModel() {

    interface EmailCheckListener {
        fun onSuccess(isEmailCheckSucccesful: Boolean)
        fun onFailure(message: String)
    }

    var mRegisterListener: AuthListener? = null
    var mEmailCheckListener: EmailCheckListener? = null

    var name: String? = null
    var email: String? = null
    var password: String? = null
    var passwordCheck: String? = null

    var isEmailCheckSucccesful = false


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
        else if(!LoginPatternCheckUtil.isValidEmail(email) || isEmailCheckSucccesful == false) {
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
            addDisposable(repo.createUserWithEmailAndPassword(email!!, password!!, name!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(name).build()
                    repo.getCurrentUser()!!.updateProfile(profileUpdates)

                    mRegisterListener?.onSuccess()
                }, {
                    mRegisterListener?.onFailure(it.message!!)
                })
            )
        }
    }

    fun checkIfEmailAlreadyExist(email : String) {
        addDisposable( repo.checkIfEmailAlreadyExist(email)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ it ->
                isEmailCheckSucccesful = it
                mEmailCheckListener?.onSuccess(it)
            }, {
                mEmailCheckListener?.onFailure(it.message!!)
            }))
    }

    fun onResisterButtonClick(view: View) {
        createUserWithEmailAndPassword(email, password, passwordCheck, name)
    }

    fun onEmailCheckButtonClick(view: View) {
        if (LoginPatternCheckUtil.isValidEmail(email))
            checkIfEmailAlreadyExist(email!!)
    }

}