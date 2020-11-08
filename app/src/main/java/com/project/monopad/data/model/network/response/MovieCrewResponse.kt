package com.project.monopad.data.model.network.response

data class MovieCrewResponse(
    val credit_id: String,
    val department: String,
    val gender: Int,
    val id: Int,
    val job: String,
    val name: String,
    val profile_path: String
)