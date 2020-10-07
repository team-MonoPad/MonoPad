package com.project.monopad.network.remote.api

import com.google.firebase.auth.FirebaseAuth

object UserApiClient {
    val firebaseClient = FirebaseAuth.getInstance()
}