package com.project.monopad.network.remote.datasource

import com.project.monopad.model.network.MovieInfoResponse
import com.project.monopad.model.network.OtherMovieInfoResponse
import com.project.monopad.model.network.OtherTvInfoResponse
import com.project.monopad.model.network.TvInfoResponse
import com.project.monopad.network.remote.api.MovieRepoApi
import com.project.monopad.network.remote.api.TvRepoApi
import io.reactivex.Single

class TvRemoteDataSourceImpl (private val tvRepoApi: TvRepoApi) : TvRemoteDataSource {
    override fun getOnTheAirTv(
        apikey: String,
        language: String,
        page: Int
    ): Single<TvInfoResponse> {
        return tvRepoApi.getOnTheAirTv(api_key = apikey, language = language, page = page)
    }

    override fun getPopularTv(apikey: String, language: String, page: Int): Single<TvInfoResponse> {
        return tvRepoApi.getPopularTv(api_key = apikey, language = language, page = page)
    }

    override fun getTopRatedTv(
        apikey: String,
        language: String,
        page: Int
    ): Single<TvInfoResponse> {
        return tvRepoApi.getTopRatedTv(api_key = apikey, language = language, page = page)
    }

    override fun getSimilarTv(
        tv_id: Int,
        apikey: String,
        language: String,
        page: Int
    ): Single<OtherTvInfoResponse> {
        return tvRepoApi.getSimilarTv(tv_id = tv_id, api_key = apikey, language = language, page = page)
    }

    override fun getRecommendationsTv(
        tv_id: Int,
        apikey: String,
        language: String,
        page: Int
    ): Single<OtherTvInfoResponse> {
        return tvRepoApi.getRecommendationsTv(tv_id = tv_id, api_key = apikey, language = language, page = page)
    }


}