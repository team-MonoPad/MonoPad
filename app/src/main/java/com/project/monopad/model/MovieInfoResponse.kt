package com.project.monopad.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class MovieInfoResponse(
    val dates: Dates,
    val page: Int,
    val results: ArrayList<MovieInfoResultResponse>,
    val total_pages: Int,
    val total_results: Int
)



