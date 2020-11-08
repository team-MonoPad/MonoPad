package com.project.monopad.ui.viewmodel

import android.content.Intent
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.project.monopad.R
import com.project.monopad.ui.view.login.AuthListener
import com.project.monopad.util.LoginPatternCheckUtil
import com.project.monopad.ui.view.login.RegisterActivity
import com.project.monopad.data.repository.UserRepoImpl
import com.project.monopad.ui.base.BaseViewModel
import com.project.monopad.util.LoginMode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class LoginViewModel(private val repo : UserRepoImpl) : BaseViewModel(){
    private val TAG = "LoginViewModel"

    var mLoginListener: AuthListener? = null

    var email: String? = null
    var password: String? = null

    var autoLogin : Boolean
        get() { return repo.autoLogin }
        set(value) { repo.autoLogin = value }

    fun getCurrentFirebaseUser() = repo.getCurrentFirebaseUser()

    fun signInWithEmail(email : String?, password : String?){
        if(!LoginPatternCheckUtil.isValidEmailAndPassword(email, password)){
            mLoginListener?.onFailure(R.string.message_login_error.toString())
        }
        else {
            mLoginListener?.onStarted()
            addDisposable(repo.signInWithEmailAndPassword(email!!, password!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mLoginListener?.onSuccess()
                    repo.loginMode = LoginMode.EMAIL
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
            }, {
                mLoginListener?.onFailure(it.message!!)
            })
        )
    }

    fun onAutoLoginButtonClick(view: View) {
        view.isSelected = !view.isSelected
        repo.autoLogin = view.isSelected
    }

    fun onLoginButtonClick(view: View) {
        signInWithEmail(email, password)
    }

    fun onResisterButtonClick(view: View) {
        view.context.run {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

}