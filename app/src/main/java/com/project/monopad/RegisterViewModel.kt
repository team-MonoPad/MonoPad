package com.project.monopad

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.monopad.AuthRepository
import com.project.monopad.User

class RegisterViewModel : ViewModel() {

    private var mAuthRepository: AuthRepository
    private var mUser: MutableLiveData<User>

    init {
        mAuthRepository = AuthRepository()
        mUser = mAuthRepository.getCurrentUser()
    }

    fun getCurrentUser() = mUser

    fun createUserWithEmailAndPassword(email: String, password: String, name : String) {
        mAuthRepository.createUserWithEmailAndPassword(email, password, name)
    }

}