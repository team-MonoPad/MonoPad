package com.project.monopad.network.remote.api

import com.google.firebase.auth.FirebaseAuth

object UserApiClient {
    enum class LoginMode {
        EMAIL, GOOGLE
    }

    val firebaseClient = FirebaseAuth.getInstance()
}