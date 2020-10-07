package com.project.monopad.network.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.project.monopad.network.remote.api.UserApiClient
import com.project.monopad.network.remote.datasource.MovieRemoteDataSource
import com.project.monopad.network.remote.datasource.UserRemoteDataSource
import com.project.monopad.util.LoginMode
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Single


class UserRepoImpl(private val userRemoteDataSource: UserRemoteDataSource) : UserRepo{

    var autoLogin : Boolean = false
    var loginMode : LoginMode = LoginMode.EMAIL

    override fun getCurrentFirebaseUser() = userRemoteDataSource.getCurrentFirebaseUser()

    override fun isAvailableEmail(email : String)
            = userRemoteDataSource.isAvailableEmail(email)

    override fun createUserWithEmailAndPassword(email : String, password : String, name : String)
            = userRemoteDataSource.createUserWithEmailAndPassword(email, password, name)

    override fun signInWithEmailAndPassword(email : String, password : String)
            = userRemoteDataSource.signInWithEmailAndPassword(email, password)

    override fun signInWithGoogle(task: Task<GoogleSignInAccount>) : Completable
            = userRemoteDataSource.signInWithGoogle(task)

    override fun signOut(mode : LoginMode)
            = userRemoteDataSource.signOut(mode)
}