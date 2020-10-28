package com.project.monopad.network.local.datasource

import com.project.monopad.model.entity.Movie
import com.project.monopad.model.entity.Review
import io.reactivex.Completable
import io.reactivex.Single

interface ReviewLocalDataSource{

    fun insert(review:Review) : Completable

    fun delete() : Completable

    fun getReviewByReviewID(REVIEW_ID : Int) : Single<Review>

    fun getAllReview() : Single<List<Review>>

    fun updateReview(review:Review) : Completable

}