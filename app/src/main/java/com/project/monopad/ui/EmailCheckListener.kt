package com.project.monopad.ui

interface EmailCheckListener {
    fun onSuccess(isEmailCheckSucccesful: Boolean)
    fun onFailure(message: String)
}