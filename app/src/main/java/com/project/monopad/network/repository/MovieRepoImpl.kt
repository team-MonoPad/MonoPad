package com.project.monopad.network.repository

import com.project.monopad.model.network.response.MovieDetailResponse
import com.project.monopad.model.network.response.MovieInfoResponse
import com.project.monopad.model.network.response.OtherMovieInfoResponse
import com.project.monopad.network.remote.datasource.MovieRemoteDataSource
import io.reactivex.Single

class MovieRepoImpl (private val movieRemoteDataSource : MovieRemoteDataSource) : MovieRepo{

    override fun getNowPlayMovie(
        apikey: String,
        language: String,
        page: Int
    ): Single<MovieInfoResponse> {
        return movieRemoteDataSource.getNowPlayMovie(apikey,language,page)
    }

    override fun getUpComingMovie(
        apikey: String,
        language: String,
        page: Int
    ): Single<MovieInfoResponse> {
        return movieRemoteDataSource.getUpComingMovie(apikey,language,page)
    }

    override fun getPopularMovie(
        apikey: String,
        language: String,
        page: Int,
        region: String
    ): Single<MovieInfoResponse> {
        return movieRemoteDataSource.getPopularMovie(apikey,language,page,region)
    }

    override fun getTopRatedMovie(
        apikey: String,
        language: String,
        page: Int,
        region: String
    ): Single<MovieInfoResponse> {
        return movieRemoteDataSource.getTopRatedMovie(apikey,language,page,region)
    }

    override fun getLatestMovie(apikey: String, language: String): Single<MovieInfoResponse> {
        return movieRemoteDataSource.getLatestMovie(apikey,language)
    }

    override fun getMovieDetail(
        movie_id: Int,
        apikey: String,
        language: String
    ): Single<MovieDetailResponse> {
        return movieRemoteDataSource.getMovieDetail(movie_id, apikey, language)
    }

    override fun getSimilarMovie(
        movie_id: Int,
        apikey: String,
        language: String,
        page: Int
    ): Single<OtherMovieInfoResponse> {
        return movieRemoteDataSource.getSimilarMovie(movie_id,apikey,language, page)
    }

    override fun getRecommendationsMovie(
        movie_id: Int,
        apikey: String,
        language: String,
        page: Int
    ): Single<OtherMovieInfoResponse> {
        return movieRemoteDataSource.getRecommendationsMovie(movie_id,apikey,language,page)
    }

    override fun getSearch(
        apikey: String,
        language: String,
        query: String,
        page: Int
    ): Single<OtherMovieInfoResponse> {
        return movieRemoteDataSource.getSearch(apikey,language,query,page)
    }

}