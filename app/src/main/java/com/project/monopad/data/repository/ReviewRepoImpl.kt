package com.project.monopad.data.repository

import androidx.lifecycle.LiveData
import com.project.monopad.data.model.entity.Review
import com.project.monopad.data.model.network.response.MovieDetailResponse
import com.project.monopad.data.model.network.response.MovieImageResponse
import com.project.monopad.data.local.datasource.ReviewLocalDataSource
import com.project.monopad.data.remote.datasource.MovieRemoteDataSource
import io.reactivex.Completable
import io.reactivex.Single

class ReviewRepoImpl (
    private val local: ReviewLocalDataSource,
    private val remote: MovieRemoteDataSource
): ReviewRepo{

    override fun getMovieDetail(
        movie_id: Int,
        apikey: String,
        language: String
    ): Single<MovieDetailResponse> {
        return remote.getMovieDetail(movie_id, apikey, language)
    }

    override fun getMovieImages(movie_id: Int, apikey: String): Single<MovieImageResponse> {
        return remote.getMovieImages(movie_id, apikey)
    }

    override fun insertReview(review: Review): Completable {
        return local.insert(review)
    }

    override fun deleteAllReview(): Completable {
        return local.delete()
    }

    override fun deleteReviewById(review_id: Int): Completable {
        return local.deleteReviewById(review_id)
    }

    override fun getReviewById(review_id: Int) : Single<Review> {
        return local.getReviewById(review_id)
    }

    override fun getAllReview(): Single<List<Review>> {
        return local.getAllReview()
    }

    override fun updateReview(review: Review): Completable {
        return local.updateReview(review)
    }
    override fun getAllReviewInLiveData() : LiveData<List<Review>> {
        return local.getAllReviewInLiveData()
    }

}