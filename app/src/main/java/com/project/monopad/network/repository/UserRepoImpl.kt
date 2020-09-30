package com.project.monopad.network.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.project.monopad.network.remote.datasource.MovieRemoteDataSource
import com.project.monopad.network.remote.datasource.UserRemoteDataSource
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Single


class UserRepoImpl(private val userRemoteDataSource: UserRemoteDataSource) : UserRepo{

    override fun getCurrentUser() = userRemoteDataSource.getCurrentUser()

    override fun signInWithEmailAndPassword(email : String, password : String)
            = userRemoteDataSource.signInWithEmailAndPassword(email, password)

    override fun createUserWithEmailAndPassword(email : String, password : String, name : String)
            = userRemoteDataSource.createUserWithEmailAndPassword(email, password, name)

    override fun checkIfEmailAlreadyExist(email : String)
            = userRemoteDataSource.checkIfEmailAlreadyExist(email)

    override fun signOut()
            = userRemoteDataSource.signOut()
}