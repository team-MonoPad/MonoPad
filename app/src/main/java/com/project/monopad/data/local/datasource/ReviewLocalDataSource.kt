package com.project.monopad.data.local.datasource

import com.project.monopad.data.model.entity.Review
import io.reactivex.Completable
import io.reactivex.Single

interface ReviewLocalDataSource{

    fun insert(review:Review) : Completable

    fun delete() : Completable

    fun deleteReviewById(review_id: Int): Completable

    fun getReviewById(REVIEW_ID : Int) : Single<Review>

    fun getAllReview() : Single<List<Review>>

    fun updateReview(review:Review) : Completable

}