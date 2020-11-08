package com.project.monopad.data.remote.datasource

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.project.monopad.data.remote.api.UserApiClient
import com.project.monopad.util.LoginMode
import io.reactivex.Completable
import io.reactivex.Single

class UserRemoteDataSourceImpl(private val userApiClient: UserApiClient) : UserRemoteDataSource {

    override fun getCurrentFirebaseUser(): FirebaseUser? = userApiClient.firebaseClient.currentUser

    override fun createUserWithEmailAndPassword(email : String, password : String, name : String) = Completable.create  { emitter ->
        userApiClient.firebaseClient.createUserWithEmailAndPassword(email, password)
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

    override fun isAvailableEmail(email : String) = Single.create<Boolean> { emitter ->
        userApiClient.firebaseClient.fetchSignInMethodsForEmail(email)
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

    override fun signInWithEmailAndPassword(email : String, password : String) = Completable.create { emitter ->
        userApiClient.firebaseClient.signInWithEmailAndPassword(email, password)
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

    override fun signInWithGoogle(task: Task<GoogleSignInAccount>): Completable = Completable.create{ emitter ->
        val account = task.getResult(ApiException::class.java)!!
        val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
        userApiClient.firebaseClient.signInWithCredential(credential)
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

    override fun signOut(mode : LoginMode) : Completable = Completable.create { emitter ->
        try {
            when (mode) {
                LoginMode.EMAIL ->
                    userApiClient.firebaseClient.signOut()
                LoginMode.GOOGLE ->
                    userApiClient.firebaseClient.signOut()
            }
            emitter.onComplete()
        } catch (t : Throwable){
            emitter.onError(t)
        }
    }

}