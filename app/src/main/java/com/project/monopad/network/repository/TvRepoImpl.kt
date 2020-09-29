package com.project.monopad.network.repository

import com.project.monopad.model.network.MovieInfoResponse
import com.project.monopad.model.network.OtherMovieInfoResponse
import com.project.monopad.model.network.OtherTvInfoResponse
import com.project.monopad.model.network.TvInfoResponse
import com.project.monopad.network.remote.datasource.MovieRemoteDataSource
import com.project.monopad.network.remote.datasource.TvRemoteDataSource
import io.reactivex.Single

class TvRepoImpl (private val tvRemoteDataSource: TvRemoteDataSource) : TvRepo{
    override fun getOnTheAirTv(
        apikey: String,
        language: String,
        page: Int
    ): Single<TvInfoResponse> {
        return tvRemoteDataSource.getOnTheAirTv(apikey, language, page)
    }

    override fun getPopularTv(apikey: String, language: String, page: Int): Single<TvInfoResponse> {
        return tvRemoteDataSource.getPopularTv(apikey, language, page)
    }

    override fun getTopRatedTv(
        apikey: String,
        language: String,
        page: Int
    ): Single<TvInfoResponse> {
        return tvRemoteDataSource.getTopRatedTv(apikey, language, page)
    }

    override fun getSimilarTv(
        tv_id: Int,
        apikey: String,
        language: String,
        page: Int
    ): Single<OtherTvInfoResponse> {
        return tvRemoteDataSource.getSimilarTv(tv_id, apikey, language, page)
    }

    override fun getRecommendationsTv(
        tv_id: Int,
        apikey: String,
        language: String,
        page: Int
    ): Single<OtherTvInfoResponse> {
        return tvRemoteDataSource.getRecommendationsTv(tv_id, apikey, language, page)
    }


}