package com.project.monopad.network.repository

import com.project.monopad.model.entity.Review
import com.project.monopad.model.network.response.MovieDetailResponse
import com.project.monopad.model.network.response.MovieImageResponse
import com.project.monopad.network.local.datasource.ReviewLocalDataSource
import com.project.monopad.network.remote.datasource.MovieRemoteDataSource
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

    override fun insertReview(review: Review) : Completable{
        return local.insert(review)
    }

    override fun deleteAllReview() : Completable {
        return local.delete()
    }

    override fun getReviewByReviewId(review_id: Int) : Single<Review> {
        return local.getReviewByReviewID(review_id)
    }

    override fun getAllReview(): Single<List<Review>> {
        return local.getAllReview()
    }

    override fun updateReview(review: Review): Completable {
        return local.updateReview(review)
    }

}