package com.project.monopad.model.network

data class MovieInfoResponse(
    val dates: Dates,
    val page: Int,
    val results: ArrayList<MovieInfoResultResponse>,
    val total_pages: Int,
    val total_results: Int
)





