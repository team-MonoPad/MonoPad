package com.project.monopad.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.view.View
import com.project.monopad.R
import com.project.monopad.ui.view.login.AuthListener
import com.project.monopad.util.LoginPatternCheckUtil
import com.project.monopad.ui.view.login.RegisterActivity
import com.project.monopad.network.repository.UserRepoImpl
import com.project.monopad.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class LoginViewModel(private val repo : UserRepoImpl) : BaseViewModel(){

    var mLoginListener: AuthListener? = null

    var email: String? = null
    var password: String? = null


    fun getCurrentUser() = repo.getCurrentUser()

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
                }, {
                    mLoginListener?.onFailure(it.message!!)
                })
            )
        }
    }

    fun signOut() {
        repo.signOut()
    }

    fun onLoginButtonClick(view: View) {
        signInWithEmail(email, password)
    }

    fun onResisterButtonClick(view: View) {
        val context: Context = view.getContext()
        val intent = Intent(context, RegisterActivity::class.java)
        context.startActivity(intent)
    }

}