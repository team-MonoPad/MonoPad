package com.project.monopad.network.remote.api

import com.project.monopad.model.network.MovieInfoResponse
import com.project.monopad.model.network.OtherMovieInfoResponse
import com.project.monopad.model.network.OtherTvInfoResponse
import com.project.monopad.model.network.TvInfoResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvRepoApi {

    //upcoming/ latest 제한적

    @GET("movie/now_playing")
    fun getOnTheAirTv(
        @Query("api_key") api_key:String,
        @Query("language") language:String,
        @Query("page") page:Int
    ): Single<TvInfoResponse>

    @GET("tv/popular")
    fun getPopularTv(
        @Query("api_key") api_key:String,
        @Query("language") language:String,
        @Query("page") page:Int
    ): Single<TvInfoResponse>

    @GET("tv/top_rated")
    fun getTopRatedTv(
        @Query("api_key") api_key:String,
        @Query("language") language:String,
        @Query("page") page:Int
    ): Single<TvInfoResponse>

    @GET("tv/{tv_id}/similar")
    fun getSimilarTv(
        @Path("tv_id") tv_id : Int,
        @Query("api_key") api_key:String,
        @Query("language") language:String,
        @Query("page") page:Int
    ) : Single<OtherTvInfoResponse>

    @GET("tv/{tv_id}/recommendations")
    fun getRecommendationsTv(
        @Path("tv_id") tv_id : Int,
        @Query("api_key") api_key:String,
        @Query("language") language:String,
        @Query("page") page:Int
    ) : Single<OtherTvInfoResponse>
}