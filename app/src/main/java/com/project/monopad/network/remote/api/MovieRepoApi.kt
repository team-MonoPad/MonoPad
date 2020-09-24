package com.project.monopad.network.remote.api

import com.project.monopad.model.MovieInfoResponse
import io.reactivex.Single
import retrofit2.http.GET
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

}