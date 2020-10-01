package com.project.monopad.ui.viewmodel

import android.view.View
import com.google.firebase.auth.UserProfileChangeRequest
import com.project.monopad.ui.view.login.AuthListener
import com.project.monopad.network.repository.UserRepoImpl
import com.project.monopad.ui.view.login.EmailCheckListener
import com.project.monopad.ui.base.BaseViewModel
import com.project.monopad.util.LoginPatternCheckUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RegisterViewModel(private val repo : UserRepoImpl) : BaseViewModel() {

    var mRegisterListener: AuthListener? = null
    var mEmailCheckListener: EmailCheckListener? = null

    var isEmailCheckSucccesful = false

    var name: String? = null
    var email: String? = null
    var password: String? = null
    var passwordCheck: String? = null

    fun createUserWithEmailAndPassword(email: String?, password: String?, passwordCheck: String?, name : String?) {
        if(!LoginPatternCheckUtil.isValidName(name)){
            mRegisterListener?.onFailure("이름을 입력해주세요.")
        }
        else if(!LoginPatternCheckUtil.isValidEmail(email) || isEmailCheckSucccesful == false) {
            mRegisterListener?.onFailure("이메일 중복 확인 버튼을 눌러주세요.")
        }
        else if(!LoginPatternCheckUtil.isValidPassword(password)) {
            mRegisterListener?.onFailure("비밀번호는 6자 이상으로 입력해주세요.")
        }
        else if(!LoginPatternCheckUtil.checkPassword(password, passwordCheck)) {
            mRegisterListener?.onFailure("비밀번호가 다릅니다.")
        }
        else {
            mRegisterListener?.onStarted()
            addDisposable(repo.createUserWithEmailAndPassword(email!!, password!!, name!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(name).build()
                    repo.getCurrentUser()!!.updateProfile(profileUpdates)

                    mRegisterListener?.onSuccess()
                }, {
                    mRegisterListener?.onFailure(it.message!!)
                })
            )
        }
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

    fun onResisterButtonClick(view: View) {
        createUserWithEmailAndPassword(email, password, passwordCheck, name)
    }

    fun onEmailCheckButtonClick(view: View) {
        if (LoginPatternCheckUtil.isValidEmail(email))
            isAvailableEmail(email!!)
    }
}