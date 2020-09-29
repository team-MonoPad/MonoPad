package com.project.monopad.model.network

data class OtherTvInfoResponse(
    val page: Int,
    val results: ArrayList<MovieInfoResultResponse>,
    val total_pages: Int,
    val total_results: Int
)