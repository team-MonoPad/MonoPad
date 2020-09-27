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


class LoginViewModel(application: Application) : AndroidViewModel(application){

    private var mAuthRepository : AuthRepository
    private var mUser : MutableLiveData<User>

    private val _showErrorToast = MutableLiveData<Event<String>>()
    val showErrorToast: LiveData<Event<String>> = _showErrorToast

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    init {
        mAuthRepository = AuthRepository(application)
        mUser = mAuthRepository.getCurrentUser()
    }

    fun getCurrentUser() = mUser

    fun signInWithEmail(email : String, password : String){
        mAuthRepository.signInWithEmailAndPassword(email,password)
    }

    fun signOut() {
        mAuthRepository.signOut()
    }

    fun onLoginButtonClick(view: View) {
        val email_s = email.value
        val password_s = password.value

        if (isValidEmailAndPassword(email_s, password_s)) {
            signInWithEmail(email_s!!, password_s!!)
        }
    }

    fun onResisterButtonClick(view: View) {
        val context: Context = view.getContext()
        val intent = Intent(context, RegisterActivity::class.java)
        context.startActivity(intent)
    }

    fun isValidEmailAndPassword(email: String?, password: String?) : Boolean {
        if (!(LoginPatternCheckUtil.isValidEmail(email) && LoginPatternCheckUtil.isValidPassword(password))) {
            _showErrorToast.value = Event("이메일 비번 다시 확인")
            return false
        }
        return true
    }
}