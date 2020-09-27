package com.project.monopad

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest


class AuthRepository(application : Application) {

    //private val mFirebaseSource : FirebaseSource

    private val mFirebaseAuth = FirebaseAuth.getInstance()
    private val mUser = MutableLiveData<User>()
    private val mApplcation = application

    fun getCurrentUser() = mUser

    fun signInWithEmailAndPassword(email : String, password : String){
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val firebaseUser = mFirebaseAuth.currentUser

                    val uid = firebaseUser!!.uid
                    val email = firebaseUser!!.email!!
                    val displayName = firebaseUser!!.displayName!!
                    mUser.value = User(uid, email, displayName)

                    PreferenceManager.setString(mApplcation,"user_id", uid)
                    PreferenceManager.setString(mApplcation,"user_displayname", displayName)
                }
                else {
                    Log.e("SEULGI", "signInWithEmail:failure", task.exception)
                    //invalid password or invalid user toast
                }
            }
    }

    fun createUserWithEmailAndPassword(email : String, password : String, name : String) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val firebaseUser = mFirebaseAuth.currentUser

                    val uid = firebaseUser!!.uid
                    val email = firebaseUser!!.email!!
                    val displayName = ""
                    mUser.value = User(uid, email, displayName)

                    val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(name).build()
                    firebaseUser!!.updateProfile(profileUpdates)
                } else {
                    Log.w("SEULGI", "createUserWithEmail:failure", task.exception)
                }
            }
    }

    fun signOut() {
        mFirebaseAuth.signOut()
    }
}