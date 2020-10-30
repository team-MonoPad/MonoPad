package com.project.monopad.network.local.datasource

import com.project.monopad.model.entity.Movie
import com.project.monopad.model.entity.Review
import com.project.monopad.network.local.LocalDataSource
import io.reactivex.Completable
import io.reactivex.Single

class ReviewLocalDataSourceImpl(private val localDataSource: LocalDataSource): ReviewLocalDataSource{

    override fun insert(review: Review) : Completable {
        return localDataSource.insert(review)
    }

    override fun delete() : Completable{
        return localDataSource.delete()
    }

    override fun getReviewByReviewID(REVIEW_ID: Int): Single<Review> {
        return localDataSource.getReviewByReviewID(REVIEW_ID)
    }

    override fun getAllReview(): Single<List<Review>> {
        return localDataSource.getAllReview()
    }

    override fun updateReview(review: Review): Completable {
        return localDataSource.updateReview(review)
    }

}