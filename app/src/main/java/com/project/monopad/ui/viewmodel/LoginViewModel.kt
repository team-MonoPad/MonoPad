package com.project.monopad.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.AndroidViewModel
import com.project.monopad.ui.AuthListener
import com.project.monopad.util.LoginPatternCheckUtil
import com.project.monopad.ui.view.RegisterActivity
import com.project.monopad.network.repository.AuthRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class LoginViewModel(application: Application) : AndroidViewModel(application){

    private var mAuthRepository =
        AuthRepository(application)
    var mLoginListener: AuthListener? = null

    var email: String? = null
    var password: String? = null


    //by lazy : 호출 시점에 초기화 -> 메모리 낭비 방지
    val user by lazy {
        mAuthRepository.getCurrentUser()
    }

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

    fun setLoginListener(authListener: AuthListener) {
        this.mLoginListener = authListener
    }

    fun signInWithEmail(email : String?, password : String?){
        if(!LoginPatternCheckUtil.isValidEmailAndPassword(
                email,
                password
            )
        ){
            //로그인 실패
            mLoginListener?.onFailure("가입 x 사용자 / 비번 오류")
        }
        else {
            //로그인 성공
            mLoginListener?.onStarted()
            val disposable = mAuthRepository.signInWithEmailAndPassword(email!!, password!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mLoginListener?.onSuccess()
                }, {
                    mLoginListener?.onFailure(it.message!!)
                })
            addDisposable(disposable)
        }
    }

    fun signOut() {
        mAuthRepository.signOut()
    }

    fun onLoginButtonClick(view: View) {
        signInWithEmail(email, password)
    }

    fun onResisterButtonClick(view: View) {
        val context: Context = view.getContext()
        val intent = Intent(context, RegisterActivity::class.java)
        context.startActivity(intent)
    }

/*    private val _showErrorToast = MutableLiveData<Event<String>>()
    val showErrorToast: LiveData<Event<String>> = _showErrorToast*/

}