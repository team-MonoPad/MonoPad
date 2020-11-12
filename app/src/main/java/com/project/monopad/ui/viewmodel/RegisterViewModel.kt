package com.project.monopad.ui.viewmodel

import com.google.firebase.auth.UserProfileChangeRequest
import com.project.monopad.data.repository.UserRepoImpl
import com.project.monopad.ui.base.BaseViewModel
import com.project.monopad.ui.view.login.AuthListener
import com.project.monopad.ui.view.login.EmailCheckListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RegisterViewModel(private val repo : UserRepoImpl) : BaseViewModel() {

    private var mRegisterListener: AuthListener? = null
    private var mEmailCheckListener: EmailCheckListener? = null

    var isEmailCheckSuccessful = false

    fun setRegisterListener(listener : AuthListener){
        this.mRegisterListener = listener
    }

    fun setEmailCheckListener(listener : EmailCheckListener){
        this.mEmailCheckListener = listener
    }

    fun createUserWithEmailAndPassword(email: String, password: String, passwordCheck: String, name : String) {
        mRegisterListener?.onStarted()
        addDisposable(repo.createUserWithEmailAndPassword(email, password, name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(name).build()
                repo.getCurrentFirebaseUser()!!.updateProfile(profileUpdates)
                mRegisterListener?.onSuccess()
            }, {
                mRegisterListener?.onFailure(it.message!!)
            })
        )
    }

    fun isAvailableEmail(email : String){
        addDisposable(repo.isAvailableEmail(email)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isEmailCheckSuccessful = it
                mEmailCheckListener?.onEmailCheckSuccess(it)
            }, {
                mEmailCheckListener?.onEmailCheckFailure(it.message!!)
            }))
    }

    companion object{
        private const val TAG = "RegisterViewModel"
    }

}