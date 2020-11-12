package com.project.monopad.data.repository

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.project.monopad.data.remote.datasource.UserRemoteDataSource
import com.project.monopad.util.LoginMode
import io.reactivex.Completable


class UserRepoImpl(private val userRemoteDataSource: UserRemoteDataSource) : UserRepo{

    var isLoggedIn : Boolean = false
    var isAutoLoginSet : Boolean = false
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