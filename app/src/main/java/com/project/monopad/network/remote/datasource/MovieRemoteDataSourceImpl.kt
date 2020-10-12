package com.project.monopad.network.remote.datasource

import com.project.monopad.model.network.response.*
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

    override fun getMovieVideo(
        movie_id: Int,
        apikey: String,
        language: String
    ): Single<MovieVideoResponse> {
        return movieRepoApi.getMovieVideo(movie_id = movie_id, api_key = apikey, language = language)
    }

    override fun getMovieCredits(movie_id: Int, apikey: String): Single<MovieCreditsResponse> {
        return movieRepoApi.getMovieCredits(movie_id = movie_id, api_key = apikey)
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

    override fun getPersonDetail(
        person_id: Int,
        apikey: String,
        language: String
    ): Single<PersonDetailResponse> {
        return movieRepoApi.getPeopleDetail(person_id = person_id, api_key = apikey, language = language)
    }

}