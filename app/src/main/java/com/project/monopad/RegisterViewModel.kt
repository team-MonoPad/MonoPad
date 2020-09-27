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
import com.project.monopad.AuthRepository
import com.project.monopad.User

class RegisterViewModel(application : Application) : AndroidViewModel(application) {

    private var mAuthRepository: AuthRepository
    private var mUser: MutableLiveData<User>

    private val _showErrorToast = MutableLiveData<Event<String>>()
    val showErrorToast: LiveData<Event<String>> = _showErrorToast

    val name = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordCheck = MutableLiveData<String>()

    init {
        mAuthRepository = AuthRepository(application)
        mUser = mAuthRepository.getCurrentUser()
    }

    fun getCurrentUser() = mUser

    fun createUserWithEmailAndPassword(email: String, password: String, passwordCheck: String, name : String) {
        mAuthRepository.createUserWithEmailAndPassword(email, password, name)
    }

    fun onResisterButtonClick(view: View) {
        val name_s = name.value
        val email_s = email.value
        val password_s = password.value
        val passwordCheck_s = passwordCheck.value

        if(!LoginPatternCheckUtil.isValidName(name_s)){
            _showErrorToast.value = Event("이름 유효 x")
        }
        else if (!LoginPatternCheckUtil.isValidEmail(email_s)) {
            _showErrorToast.value = Event("이메일 유효 x")
        }
        else if (!LoginPatternCheckUtil.isValidPassword(password_s)){
            _showErrorToast.value = Event("비번 유효 x")
        }
        else if (!LoginPatternCheckUtil.checkPassword(password_s,passwordCheck_s)) {
            _showErrorToast.value = Event("비번 다름")
        }
        else {
            mAuthRepository.createUserWithEmailAndPassword(email_s!!, password_s!!, name_s!!)
        }
    }
}