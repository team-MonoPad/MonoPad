package com.project.monopad.ui.view.login

interface EmailCheckListener {
    fun onEmailCheckSuccess(isEmailCheckSucccesful: Boolean)
    fun onEmailCheckFailure(message: String)
}