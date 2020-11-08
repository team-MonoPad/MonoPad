package com.project.monopad.data.local.datasource

import com.project.monopad.data.model.entity.Review
import com.project.monopad.data.local.LocalDataSource
import io.reactivex.Completable
import io.reactivex.Single

class ReviewLocalDataSourceImpl(private val localDataSource: LocalDataSource): ReviewLocalDataSource{

    override fun insert(review: Review) : Completable {
        return localDataSource.insertReview(review)
    }

    override fun delete() : Completable{
        return localDataSource.deleteReview()
    }

    override fun deleteReviewById(review_id: Int): Completable {
        return localDataSource.deleteReviewByReviewID(review_id)
    }

    override fun getReviewById(REVIEW_ID: Int): Single<Review> {
        return localDataSource.getReviewByReviewID(REVIEW_ID)
    }

    override fun getAllReview(): Single<List<Review>> {
        return localDataSource.getAllReview()
    }

    override fun updateReview(review: Review): Completable {
        return localDataSource.updateReview(review)
    }

}