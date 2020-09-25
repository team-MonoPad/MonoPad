package com.project.monopad

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class LoginViewModel : ViewModel(){

    private var mAuthRepository : AuthRepository
    private var mUser : MutableLiveData<User>

    init {
        mAuthRepository = AuthRepository()
        mUser = mAuthRepository.getCurrentUser()
    }

    fun getCurrentUser() = mUser

    fun signInWithEmail(email : String, password : String){
        mAuthRepository.signInWithEmailAndPassword(email, password)
    }

    fun signOut() {
        mAuthRepository.signOut()
    }

    fun onLoginButtonClick(view: View) {

    }

    fun onResisterButtonClick(view: View) {
        val context: Context = view.getContext()
        val intent = Intent(context, RegisterActivity::class.java)
        context.startActivity(intent)
    }

}