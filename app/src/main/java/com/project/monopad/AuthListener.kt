package com.project.monopad

interface AuthListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}