package com.project.monopad.network.repository

import com.project.monopad.model.entity.Movie
import com.project.monopad.model.entity.Review
import com.project.monopad.network.local.datasource.ReviewLocalDataSource
import io.reactivex.Completable
import io.reactivex.Single

class ReviewRepoImpl (private val local: ReviewLocalDataSource): ReviewRepo{
    override fun insert(review: Review) : Completable{
        return local.insert(review)
    }

    override fun delete() : Completable {
        return local.delete()
    }

    override fun getReviewByReviewId(review_id: Int) : Single<Review> {
        return local.getReviewByReviewID(review_id)
    }

    override fun getAllReview(): Single<List<Review>> {
        return local.getAllReview()
    }

}