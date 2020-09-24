package com.project.monopad.ui.login

import android.content.Context
import android.content.Intent
import android.util.Patterns
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.monopad.AuthRepository
import com.project.monopad.User


class LoginViewModel : ViewModel(){

    private var mAuthRepository : AuthRepository
    private var mUser : MutableLiveData<User>

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()

    init {
        mAuthRepository = AuthRepository()
        mUser = mAuthRepository.getCurrentUser()
    }

    fun getCurrentUser() = mUser

    fun signInWithEmail(email : String, password : String){
        mAuthRepository.signInWithEmailAndPassword(email, password)
    }

    fun onLoginButtonClick(view: View) {

    }

    fun onResisterButtonClick(view: View) {
        val context: Context = view.getContext()
        val intent = Intent(context, RegisterActivity::class.java)
        context.startActivity(intent)
    }



}