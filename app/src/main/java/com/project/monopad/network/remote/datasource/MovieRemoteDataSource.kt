package com.project.monopad.network.remote.datasource

import com.project.monopad.model.network.response.*
import io.reactivex.Single

interface MovieRemoteDataSource {
    fun getNowPlayMovie(apikey : String, language : String, page: Int) : Single<MovieInfoResponse>
    fun getUpComingMovie(apikey : String, language : String, page: Int) : Single<MovieInfoResponse>
    fun getPopularMovie(apikey : String, language : String, page: Int, region : String) : Single<MovieInfoResponse>
    fun getTopRatedMovie(apikey : String, language : String, page: Int, region : String) : Single<MovieInfoResponse>
    fun getLatestMovie(apikey : String, language : String) : Single<MovieInfoResponse>
    fun getMovieDetail(movie_id : Int, apikey: String, language: String) : Single<MovieDetailResponse>
    fun getMovieVideo(movie_id : Int, apikey: String, language: String) : Single<MovieVideoResponse>
    fun getMovieCredits(movie_id : Int, apikey: String) : Single<MovieCreditsResponse>
    fun getSimilarMovie(movie_id : Int, apikey: String, language: String, page: Int) : Single<OtherMovieInfoResponse>
    fun getRecommendationsMovie(movie_id : Int, apikey: String, language: String, page: Int) : Single<OtherMovieInfoResponse>
    fun getSearch(apikey : String, language : String, query : String, page: Int) : Single<OtherMovieInfoResponse>
    fun getPersonDetail(person_id : Int, apikey: String, language: String) : Single<PersonDetailResponse>
    fun getPersonDetailCredits(person_id : Int, apikey: String, language: String) : Single<PersonDetailCreditsResponse>
    fun getMovieImages(movie_id : Int, apikey: String) : Single<MovieImageResponse>
}