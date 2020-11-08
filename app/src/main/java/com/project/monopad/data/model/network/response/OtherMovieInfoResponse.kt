package com.project.monopad.data.model.network.response

data class OtherMovieInfoResponse(
    val page: Int,
    val results: ArrayList<MovieInfoResultResponse>,
    val total_pages: Int,
    val total_results: Int
)