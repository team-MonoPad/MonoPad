package com.project.monopad.data.repository

import com.project.monopad.data.model.network.response.*
import com.project.monopad.data.remote.datasource.MovieRemoteDataSource
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
        page: Int,
        region: String
    ): Single<MovieInfoResponse> {
        return movieRemoteDataSource.getUpComingMovie(apikey,language,page,region)
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

    override fun getMovieVideo(
        movie_id: Int,
        apikey: String,
        language: String
    ): Single<MovieVideoResponse> {
        return movieRemoteDataSource.getMovieVideo(movie_id, apikey, language)
    }

    override fun getMovieCredits(movie_id: Int, apikey: String): Single<MovieCreditsResponse> {
        return movieRemoteDataSource.getMovieCredits(movie_id, apikey)
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

    override fun getMovieSearch(
        apikey: String,
        language: String,
        query: String,
        page: Int
    ): Single<OtherMovieInfoResponse> {
        return movieRemoteDataSource.getMovieSearch(apikey,language,query,page)
    }

    override fun getPeopleDetail(
        people_id: Int,
        apikey: String,
        language: String
    ): Single<PersonDetailResponse> {
        return movieRemoteDataSource.getPersonDetail(people_id,apikey, language)
    }

    override fun getPersonDetailCredits(
        people_id: Int,
        apikey: String,
        language: String
    ): Single<PersonDetailCreditsResponse> {
        return movieRemoteDataSource.getPersonDetailCredits(people_id, apikey, language)
    }

    override fun getMovieImages(movie_id: Int, apikey: String): Single<MovieImageResponse> {
        return movieRemoteDataSource.getMovieImages(movie_id, apikey)
    }


}