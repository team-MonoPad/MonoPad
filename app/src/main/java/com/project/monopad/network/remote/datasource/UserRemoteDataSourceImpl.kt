package com.project.monopad.network.remote.datasource

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Completable
import io.reactivex.Single

class UserRemoteDataSourceImpl(private val mFirebaseAuth: FirebaseAuth) : UserRemoteDataSource {

    override fun getCurrentUser(): FirebaseUser? = mFirebaseAuth.currentUser

    override fun signInWithEmailAndPassword(email : String, password : String) = Completable.create { emitter ->
        Log.e("SEULGI LOGIN 3","repo에서 이메일 로직 호출")
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if(!emitter.isDisposed){
                    if (task.isSuccessful) {
                        Log.e("SEULGI LOGIN 4","task successful")
                        emitter.onComplete()
                    }
                    else {
                        emitter.onError(task.exception!!)
                    }
                }
            }
    }

    override fun createUserWithEmailAndPassword(email : String, password : String, name : String) = Completable.create  { emitter ->
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

    override fun checkIfEmailAlreadyExist(email : String) = Single.create<Boolean> { emitter ->
        mFirebaseAuth.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener{ task ->
                if(!emitter.isDisposed){
                    if(task.isSuccessful){
                        emitter.onSuccess(task.getResult()?.signInMethods!!.isEmpty())
                    }
                    else {
                        emitter.onError(task.exception!!)
                    }
                }
            }
    }

    override fun signOut() {
        mFirebaseAuth.signOut()
    }


}