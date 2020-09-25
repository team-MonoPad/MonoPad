package com.project.monopad

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest


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
                        mUser.value = User(true, firebaseUser.uid, firebaseUser.email!!, firebaseUser.displayName!!)
                    }
                }
                else {
                    Log.e("SEULGI", "signInWithEmail:failure", task.exception)
                }
            }
    }

    fun createUserWithEmailAndPassword(email : String, password : String, name : String) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val firebaseUser = mFirebaseAuth.currentUser
                    if (firebaseUser != null) {
                        mUser.value = User(true, firebaseUser.uid, firebaseUser.email!!, "")
                        val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(name).build()
                        firebaseUser.updateProfile(profileUpdates)
                    }
                } else {
                    Log.w("SEULGI", "createUserWithEmail:failure", task.exception)
                }
            }
    }

    fun signOut() {
        mFirebaseAuth.signOut()
    }
}