package com.project.monopad.ui.view.login

interface AuthListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}