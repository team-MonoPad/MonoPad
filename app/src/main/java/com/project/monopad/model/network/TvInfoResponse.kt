package com.project.monopad.model.network

data class TvInfoResponse(
    val page: Int,
    val results: List<TvInfoResultResponse>,
    val total_pages: Int,
    val total_results: Int
)

