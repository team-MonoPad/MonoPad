package com.project.monopad.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.project.monopad.data.model.entity.Review
import io.reactivex.Completable
import io.reactivex.Single


@Dao
interface DiaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReviewAndMovie(review: Review) : Completable

    @Query("DELETE FROM Review")
    fun deleteAllReview() : Completable

    @Query("DELETE FROM Review WHERE id = :id")
    fun deleteReviewByReviewId(id: Int) : Completable

    @Query("SELECT * FROM Review WHERE id = :id")
    fun getReviewByReviewID(id : Int) : Single<Review>

    @Query("SELECT * FROM Review")
    fun getAllReview() : Single<List<Review>>

    @Query("SELECT * FROM Review")
    fun getAllReviewInLiveData() : LiveData<List<Review>>

    @Update
    fun updateReview(review: Review) : Completable

}