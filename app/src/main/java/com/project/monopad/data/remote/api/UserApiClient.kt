package com.project.monopad.data.remote.api

import com.google.firebase.auth.FirebaseAuth

object UserApiClient {
    val firebaseClient = FirebaseAuth.getInstance()
}