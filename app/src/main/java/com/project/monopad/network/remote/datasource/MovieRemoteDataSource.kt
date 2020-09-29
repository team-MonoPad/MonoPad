package com.project.monopad.network.remote.datasource

import com.project.monopad.model.network.MovieInfoResponse
import com.project.monopad.model.network.OtherMovieInfoResponse
import io.reactivex.Single

interface MovieRemoteDataSource {
    fun getNowPlayMovie(apikey : String, language : String, page: Int) : Single<MovieInfoResponse>
    fun getUpComingMovie(apikey : String, language : String, page: Int) : Single<MovieInfoResponse>
    fun getPopularMovie(apikey : String, language : String, page: Int, region : String) : Single<MovieInfoResponse>
    fun getTopRatedMovie(apikey : String, language : String, page: Int, region : String) : Single<MovieInfoResponse>
    fun getLatestMovie(apikey : String, language : String) : Single<MovieInfoResponse>

    fun getSimilarMovie(movie_id : Int, apikey: String, language: String, page: Int) : Single<OtherMovieInfoResponse>
    fun getRecommendationsMovie(movie_id : Int, apikey: String, language: String, page: Int) : Single<OtherMovieInfoResponse>

    fun getSearch(apikey : String, language : String, query : String, page: Int) : Single<OtherMovieInfoResponse>
}