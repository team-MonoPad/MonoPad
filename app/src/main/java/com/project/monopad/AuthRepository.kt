package com.project.monopad

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class AuthRepository {

    private val mFirebaseAuth = FirebaseAuth.getInstance()
    private val mUser = MutableLiveData<User>()

    fun getCurrentUser() = mUser

    fun signInWithEmailAndPassword(email : String, password : String){

        mFirebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val firebaseUser = mFirebaseAuth.currentUser
                    if (firebaseUser != null) {
                        mUser.value = User(firebaseUser.uid, //firebaseUser.displayName!!,
                             firebaseUser.email!!, true)
                    }
                }
                else {
                    Log.e("SEULGI", "signInWithEmail:failure", task.exception)
                }
            }
    }

}