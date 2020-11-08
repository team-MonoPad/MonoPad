package com.project.monopad.data.model.network.response

data class PersonDetailCreditsResponse(
    val cast: List<PersonDetailCreditsCastResponse>,
    val crew: List<Any>,
    val id: Int
)

