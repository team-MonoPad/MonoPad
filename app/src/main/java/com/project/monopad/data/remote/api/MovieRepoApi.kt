package com.project.monopad.data.remote.api

import com.project.monopad.data.model.network.response.*
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
        @Query("page") page:Int,
        @Query("region") region:String
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

    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movie_id : Int,
        @Query("api_key") api_key:String,
        @Query("language") language:String
    ) : Single<MovieDetailResponse>

    @GET("movie/{movie_id}/videos")
    fun getMovieVideo(
        @Path("movie_id") movie_id : Int,
        @Query("api_key") api_key:String,
        @Query("language") language:String
    ) : Single<MovieVideoResponse>

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

    @GET("search/movie")
    fun getSearch(
        @Query("api_key") api_key:String,
        @Query("language") language:String,
        @Query("query") query:String,
        @Query("page") page:Int
    ) : Single<OtherMovieInfoResponse>

    @GET("movie/{movie_id}/credits")
    fun getMovieCredits(
        @Path("movie_id") movie_id : Int,
        @Query("api_key") api_key: String
    ) : Single<MovieCreditsResponse>

    @GET("person/{person_id}")
    fun getPeopleDetail(
        @Path("person_id") person_id : Int,
        @Query("api_key") api_key: String,
        @Query("language") language:String
    ) : Single<PersonDetailResponse>

    @GET("person/{person_id}/movie_credits")
    fun getPeopleDetailCredits(
        @Path("person_id") person_id : Int,
        @Query("api_key") api_key: String,
        @Query("language") language:String
    ) : Single<PersonDetailCreditsResponse>

    @GET("movie/{movie_id}/images")
    fun getMovieImages(
        @Path("movie_id") movie_id : Int,
        @Query("api_key") api_key: String
    ) : Single<MovieImageResponse>
}