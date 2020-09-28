package com.project.monopad

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Single


class AuthRepository(application : Application) {

    private val mApplcation = application
    private val mFirebaseAuth = FirebaseAuth.getInstance()
    private var isLoginSuccessed = false
    private var isRegisterSuccessed = false

    fun getCurrentUser() = mFirebaseAuth.currentUser

    fun signInWithEmailAndPassword(email : String, password : String) = Completable.create { emitter ->
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if(!emitter.isDisposed){
                    if (task.isSuccessful) {
                        emitter.onComplete()
                    }
                    else {
                        emitter.onError(task.exception!!)
                    }
                }
            }
    }

    fun createUserWithEmailAndPassword(email : String, password : String, name : String) = Completable.create  { emitter ->
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if(!emitter.isDisposed){
                    if (task.isSuccessful) {
                        emitter.onComplete()
                    }
                    else {
                        emitter.onError(task.exception!!)
                    }
                }
            }

    }

    fun checkIfEmailAlreadyExist(email : String) = Observable.create(ObservableOnSubscribe<Boolean>() { emitter ->
        mFirebaseAuth.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener{ task ->
                if(!emitter.isDisposed){
                    if(task.isSuccessful){
                        Log.e("SEULGI 1",""+email)
                        emitter.onNext(task.getResult()?.signInMethods!!.isEmpty())
                        emitter.onComplete()
                    }
                    else {
                        Log.e("SEULGI 2",""+email)
                        emitter.onError(task.exception!!)
                    }
                }
            }
    })

    fun signOut() {
        mFirebaseAuth.signOut()
    }
}