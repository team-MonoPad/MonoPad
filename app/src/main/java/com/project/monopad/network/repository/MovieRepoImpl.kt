package com.project.monopad.network.repository

import com.project.monopad.model.MovieInfoResponse
import com.project.monopad.network.remote.api.MovieRepoApi
import io.reactivex.Single

class MovieRepoImpl (private val movieRepoApi : MovieRepoApi) : MovieRepo{

    override fun getNowPlayMovie(
        apikey: String,
        language: String,
        page: Int
    ): Single<MovieInfoResponse> {
        return movieRepoApi.getNowPlayMovie(apikey,language,page)
    }

    override fun getUpComingMovie(
        apikey: String,
        language: String,
        page: Int
    ): Single<MovieInfoResponse> {
        return movieRepoApi.getUpComingMovie(apikey,language,page)
    }

    override fun getPopularMovie(
        apikey: String,
        language: String,
        page: Int,
        region: String
    ): Single<MovieInfoResponse> {
        return movieRepoApi.getPopularMovie(apikey,language,page,region)
    }

    override fun getTopRatedMovie(
        apikey: String,
        language: String,
        page: Int,
        region: String
    ): Single<MovieInfoResponse> {
        return movieRepoApi.getTopRatedMovie(apikey,language,page,region)
    }

}