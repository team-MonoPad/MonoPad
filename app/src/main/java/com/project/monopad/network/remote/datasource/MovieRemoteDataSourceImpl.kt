package com.project.monopad.network.remote.datasource

import com.project.monopad.model.network.response.MovieDetailResponse
import com.project.monopad.model.network.response.MovieInfoResponse
import com.project.monopad.model.network.response.OtherMovieInfoResponse
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

    override fun getLatestMovie(apikey: String, language: String): Single<MovieInfoResponse> {
        return movieRepoApi.getLatestMovie(api_key = apikey, language = language)
    }

    override fun getMovieDetail(
        movie_id: Int,
        apikey: String,
        language: String
    ): Single<MovieDetailResponse> {
        return movieRepoApi.getMovieDetail(movie_id = movie_id, api_key = apikey, language = language)
    }

    override fun getSimilarMovie(movie_id: Int, apikey: String, language: String, page: Int) : Single<OtherMovieInfoResponse> {
        return movieRepoApi.getSimilarMovie(movie_id = movie_id, api_key = apikey, language = language, page = page)
    }

    override fun getRecommendationsMovie(
        movie_id: Int,
        apikey: String,
        language: String,
        page: Int
    ): Single<OtherMovieInfoResponse> {
        return movieRepoApi.getRecommendationsMovie(movie_id = movie_id, api_key = apikey, language = language, page = page)
    }

    override fun getSearch(
        apikey: String,
        language: String,
        query: String,
        page: Int
    ): Single<OtherMovieInfoResponse> {
        return movieRepoApi.getSearch(api_key = apikey, language = language, query = query, page = page)
    }

}