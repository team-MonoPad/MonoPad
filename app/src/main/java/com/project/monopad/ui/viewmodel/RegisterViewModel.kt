package com.project.monopad.ui.viewmodel

import android.view.View
import com.google.firebase.auth.UserProfileChangeRequest
import com.project.monopad.R
import com.project.monopad.ui.view.login.AuthListener
import com.project.monopad.network.repository.UserRepoImpl
import com.project.monopad.ui.view.login.EmailCheckListener
import com.project.monopad.ui.base.BaseViewModel
import com.project.monopad.util.LoginPatternCheckUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RegisterViewModel(private val repo : UserRepoImpl) : BaseViewModel() {

    private val TAG = "RegisterViewModel"

    var mRegisterListener: AuthListener? = null
    var mEmailCheckListener: EmailCheckListener? = null

    var isEmailCheckSucccesful = false

    var name: String? = null
    var email: String? = null
    var password: String? = null
    var passwordCheck: String? = null

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
        addDisposable( repo.isAvailableEmail(email)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ it ->
                isEmailCheckSucccesful = it
                mEmailCheckListener?.onEmailCheckSuccess(it)
            }, {
                mEmailCheckListener?.onEmailCheckFailure(it.message!!)
            }))
    }

    fun onEmailCheckButtonClick(view: View) {
        if (LoginPatternCheckUtil.isValidEmail(email))
            isAvailableEmail(email!!)
    }

    fun onResisterButtonClick(view: View) {
        val resources = view.resources
        if(!LoginPatternCheckUtil.isValidName(name)){
            mRegisterListener?.onFailure(resources.getString(R.string.message_name_error))
        }
        else if(!LoginPatternCheckUtil.isValidEmail(email) || !isEmailCheckSucccesful) {
            mRegisterListener?.onFailure(resources.getString(R.string.message_plz_email_check))
        }
        else if(!LoginPatternCheckUtil.isValidPassword(password)) {
            mRegisterListener?.onFailure(resources.getString(R.string.message_password_error))
        }
        else if(!LoginPatternCheckUtil.checkPassword(password, passwordCheck)) {
            mRegisterListener?.onFailure(resources.getString(R.string.message_password_inconsistent))
        }
        else {
            createUserWithEmailAndPassword(email!!, password!!, passwordCheck!!, name!!)
        }
    }
}