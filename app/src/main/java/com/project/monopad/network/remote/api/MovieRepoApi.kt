package com.project.monopad.network.remote.api

import com.project.monopad.model.network.MovieInfoResponse
import com.project.monopad.model.network.OtherMovieInfoResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieRepoApi {

    @GET("movie/now_playing")
    fun getNowPlayMovie(
        @Query("api_key") api_key:String,
        @Query("language") language:String,
        @Query("page") page:Int
    ): Single<MovieInfoResponse>

    @GET("movie/upcoming")
    fun getUpComingMovie(
        @Query("api_key") api_key:String,
        @Query("language") language:String,
        @Query("page") page:Int
    ): Single<MovieInfoResponse>

    @GET("movie/popular")
    fun getPopularMovie(
        @Query("api_key") api_key:String,
        @Query("language") language:String,
        @Query("page") page:Int,
        @Query("region") region:String
    ): Single<MovieInfoResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovie(
        @Query("api_key") api_key:String,
        @Query("language") language:String,
        @Query("page") page:Int,
        @Query("region") region:String
    ): Single<MovieInfoResponse>

    @GET("movie/latest")
    fun getLatestMovie(
        @Query("api_key") api_key:String,
        @Query("language") language:String
    ) : Single<MovieInfoResponse>

    @GET("movie/{movie_id}/similar")
    fun getSimilarMovie(
        @Path("movie_id") movie_id : Int,
        @Query("api_key") api_key:String,
        @Query("language") language:String,
        @Query("page") page:Int
    ) : Single<OtherMovieInfoResponse>

    @GET("movie/{movie_id}/recommendations")
    fun getRecommendationsMovie(
        @Path("movie_id") movie_id : Int,
        @Query("api_key") api_key:String,
        @Query("language") language:String,
        @Query("page") page:Int
    ) : Single<OtherMovieInfoResponse>

    @GET("search/multi")
    fun getSearch(
        @Query("api_key") api_key:String,
        @Query("language") language:String,
        @Query("query") query:String,
        @Query("page") page:Int
    ) : Single<OtherMovieInfoResponse>

}