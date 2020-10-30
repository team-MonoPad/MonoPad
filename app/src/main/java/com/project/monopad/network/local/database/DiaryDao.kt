package com.project.monopad.network.local.database

import androidx.room.*
import com.project.monopad.model.entity.Movie
import com.project.monopad.model.entity.Review
import io.reactivex.Completable
import io.reactivex.Single


@Dao
interface DiaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReviewAndMovie(review: Review) : Completable

    @Query("DELETE FROM Review")
    fun delete() : Completable

    @Query("DELETE FROM Review WHERE id = :id")
    fun deleteReviewByReviewId(id: Int) : Completable

    @Query("SELECT * FROM Review WHERE id = :id")
    fun getReviewByReviewID(id : Int) : Single<Review>

    @Query("SELECT * FROM Review")
    fun getAllReview() : Single<List<Review>>

    @Update
    fun updateReview(review: Review) : Completable


}