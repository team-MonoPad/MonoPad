package com.project.monopad.data.model.network.response

import com.project.monopad.data.model.dto.Dates

data class MovieInfoResponse(
    val dates: Dates,
    val page: Int,
    val results: ArrayList<MovieInfoResultResponse>,
    val total_pages: Int,
    val total_results: Int
)





