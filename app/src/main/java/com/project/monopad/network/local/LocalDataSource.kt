package com.project.monopad.network.local

import com.project.monopad.model.entity.Movie
import com.project.monopad.model.entity.Review
import com.project.monopad.network.local.database.DiaryDao
import io.reactivex.Completable
import io.reactivex.Single

class LocalDataSource (private val diaryDao: DiaryDao) {

    fun insertReview(review: Review) : Completable {
        return diaryDao.insertReviewAndMovie(review = review)
    }

    fun deleteReview() : Completable {
        return diaryDao.deleteAllReview()
    }

    fun deleteReviewByReviewID(review_id: Int): Completable{
        return diaryDao.deleteReviewByReviewId(id = review_id)
    }

    fun getReviewByReviewID(review_id : Int) : Single<Review> {
        return diaryDao.getReviewByReviewID(id = review_id)
    }

    fun getAllReview() : Single<List<Review>> {
        return diaryDao.getAllReview()
    }

    fun updateReview(review: Review) : Completable {
        return diaryDao.updateReview(review = review)
    }
}