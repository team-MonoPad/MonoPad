package com.project.monopad.network.remote.datasource

import com.project.monopad.model.network.MovieInfoResponse
import com.project.monopad.model.network.OtherMovieInfoResponse
import com.project.monopad.model.network.OtherTvInfoResponse
import com.project.monopad.model.network.TvInfoResponse
import io.reactivex.Single

interface TvRemoteDataSource {
    fun getOnTheAirTv(apikey : String, language : String, page: Int) : Single<TvInfoResponse>
    fun getPopularTv(apikey : String, language : String, page: Int) : Single<TvInfoResponse>
    fun getTopRatedTv(apikey : String, language : String, page: Int) : Single<TvInfoResponse>

    fun getSimilarTv(tv_id : Int, apikey: String, language: String, page: Int) : Single<OtherTvInfoResponse>
    fun getRecommendationsTv(tv_id : Int, apikey: String, language: String, page: Int) : Single<OtherTvInfoResponse>
}