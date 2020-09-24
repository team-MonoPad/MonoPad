package com.project.monopad.network.remote.datasource

import com.project.monopad.model.MovieInfoResponse
import io.reactivex.Single

interface MovieRemoteDataSource {
    fun getNowPlayMovie(apikey : String, language : String, page: Int) : Single<MovieInfoResponse>
    fun getUpComingMovie(apikey : String, language : String, page: Int) : Single<MovieInfoResponse>
    fun getPopularMovie(apikey : String, language : String, page: Int, region : String) : Single<MovieInfoResponse>
    fun getTopRatedMovie(apikey : String, language : String, page: Int, region : String) : Single<MovieInfoResponse>
}