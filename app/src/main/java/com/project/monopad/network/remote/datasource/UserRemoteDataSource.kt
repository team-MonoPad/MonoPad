package com.project.monopad.network.remote.datasource

import com.google.firebase.auth.FirebaseUser
import io.reactivex.Completable
import io.reactivex.Single

interface UserRemoteDataSource {

    fun getCurrentUser() : FirebaseUser?
    fun signInWithEmailAndPassword(email: String, password: String) : Completable
    fun createUserWithEmailAndPassword(email: String, password: String, name: String) : Completable
    fun checkIfEmailAlreadyExist(email : String) : Single<Boolean>
    fun signOut()

}