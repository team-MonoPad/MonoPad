package com.project.monopad.model.network.response

data class MovieImageResponse(
    val backdrops: List<MovieImageBackdropResponse>,
    val id: Int,
    val posters: List<MovieImagePosterResponse>
)



