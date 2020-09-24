package com.project.monopad.network.remote.datasource

import com.project.monopad.model.MovieInfoResponse
import com.project.monopad.network.remote.api.MovieRepoApi
import io.reactivex.Single

class MovieRemoteDataSourceImpl (private val movieRepoApi: MovieRepoApi) : MovieRemoteDataSource {

    override fun getNowPlayMovie(apikey: String, language: String, page: Int
    ): Single<MovieInfoResponse> {
        return movieRepoApi.getNowPlayMovie(api_key = apikey, language = language, page = page)
    }

    override fun getUpComingMovie(apikey: String, language: String, page: Int
    ): Single<MovieInfoResponse> {
        return movieRepoApi.getUpComingMovie(api_key = apikey, language = language, page = page)
    }

    override fun getPopularMovie(apikey: String, language: String, page: Int, region: String
    ): Single<MovieInfoResponse> {
        return movieRepoApi.getPopularMovie(api_key = apikey, language = language, page = page, region = region)
    }

    override fun getTopRatedMovie(apikey: String, language: String, page: Int, region: String
    ): Single<MovieInfoResponse> {
        return movieRepoApi.getTopRatedMovie(api_key = apikey, language = language, page = page, region = region)
    }

}