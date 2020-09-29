package com.project.monopad.haeseong.model.api

import com.example.movieappsample.base.NetworkManager.API_KEY
import com.project.monopad.haeseong.model.response.LatestMovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface LatestMovieApi {

    @GET("/movie/latest/{language}")
    @Headers("Accept: application/json")
    fun getLatestMovies(
        @Path("language")
        language: String = "ko-KR" ,
        @Query("api_key")
        apiKey: String = API_KEY
    )
    : Single<List<LatestMovieResponse>>



//GET API Path Variable Example
//    @GET("test/{testNo}")
//    Observable<TestDetailResponse> getTestData(@Path("testNo") int testNo);
}