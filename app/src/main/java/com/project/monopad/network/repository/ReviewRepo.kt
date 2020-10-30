package com.project.monopad.network.repository

import com.project.monopad.model.entity.Movie
import com.project.monopad.model.entity.Review
import io.reactivex.Completable
import io.reactivex.Single

interface ReviewRepo {
    fun insert(review: Review) : Completable
    fun delete() : Completable
    fun getReviewByReviewId(review_id : Int) : Single<Review>
    fun getAllReview() : Single<List<Review>>
    fun updateReview(review: Review) : Completable
}