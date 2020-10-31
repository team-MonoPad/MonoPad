package com.project.monopad.network.repository

import com.project.monopad.model.entity.Review
import com.project.monopad.model.network.response.MovieDetailResponse
import com.project.monopad.model.network.response.MovieImageResponse
import io.reactivex.Completable
import io.reactivex.Single

interface ReviewRepo {
    fun insertReview(review: Review) : Completable
    fun deleteAllReview() : Completable
    fun deleteReviewById(review_id: Int): Completable
    fun getReviewById(review_id : Int) : Single<Review>
    fun getAllReview() : Single<List<Review>>
    fun updateReview(review: Review) : Completable
    fun getMovieDetail(movie_id: Int, apikey: String, language: String) : Single<MovieDetailResponse>
    fun getMovieImages(movie_id: Int, apikey: String): Single<MovieImageResponse>
}