package com.project.monopad.ui.viewmodel

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.project.monopad.data.repository.UserRepoImpl
import com.project.monopad.ui.base.BaseViewModel
import com.project.monopad.ui.view.login.AuthListener
import com.project.monopad.util.LoginMode
import com.project.monopad.util.isNotValidEmailAndPassword
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class LoginViewModel(private val repo : UserRepoImpl) : BaseViewModel(){

    private var mLoginListener: AuthListener? = null

    var isAutoLoginSet : Boolean
        get() = repo.isAutoLoginSet
        set(value) { repo.isAutoLoginSet = value }

    var isLoggedIn : Boolean
        get() = repo.isLoggedIn
        set(value) { repo.isLoggedIn = value }

    fun setLoginListener(listener : AuthListener){
        this.mLoginListener = listener
    }

    fun getCurrentFirebaseUser() = repo.getCurrentFirebaseUser()

    fun signInWithEmail(email : String?, password : String?){
        if(isNotValidEmailAndPassword(email, password)){
            mLoginListener?.onFailure("가입되지 않은 사용자이거나 비밀번호 오류입니다.")
        }
        else {
            mLoginListener?.onStarted()
            addDisposable(repo.signInWithEmailAndPassword(email!!, password!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mLoginListener?.onSuccess()
                    repo.loginMode = LoginMode.EMAIL
                    isLoggedIn = true
                }, {
                    mLoginListener?.onFailure(it.message!!)
                })
            )
        }
    }

    fun signInWithGoogle(task: Task<GoogleSignInAccount>) {
        mLoginListener?.onStarted()
        addDisposable(repo.signInWithGoogle(task)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mLoginListener?.onSuccess()
                repo.loginMode = LoginMode.GOOGLE
                isLoggedIn = true
            }, {
                mLoginListener?.onFailure(it.message!!)
            })
        )
    }

    fun handleGoogleSignInResult(data: Intent?){
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        signInWithGoogle(task)
    }

    fun signOut() {
        addDisposable(repo.signOut(repo.loginMode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mLoginListener?.onSuccess()
                isLoggedIn = false
            }, {
                mLoginListener?.onFailure(it.message!!)
            })
        )
    }

    companion object{
        private const val TAG = "LoginViewModel"
    }
}