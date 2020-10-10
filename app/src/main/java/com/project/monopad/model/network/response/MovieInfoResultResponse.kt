package com.project.monopad.model.network.response



data class MovieInfoResultResponse(
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String?,
    val genre_ids: List<Int>,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
) : Comparable<MovieInfoResultResponse> {

    //https://siyoon210.tistory.com/22?category=839846
    //특정 필드 값을 기준으로 리스트를 정렬하기 위한 메서드

    override fun compareTo(other: MovieInfoResultResponse): Int {
        return other.vote_count - vote_count
    }

}