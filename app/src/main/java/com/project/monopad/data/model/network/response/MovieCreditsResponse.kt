package com.project.monopad.data.model.network.response

data class MovieCreditsResponse(
    val cast: List<MovieCastResponse>,
    val crew: List<MovieCrewResponse>,
    val id: Int
)



