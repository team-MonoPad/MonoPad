package com.project.monopad.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.project.monopad.model.entity.Review
import com.project.monopad.model.network.response.MovieDetailResponse
import com.project.monopad.network.repository.ReviewRepoImpl
import com.project.monopad.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DiaryViewModel(private val repo : ReviewRepoImpl): BaseViewModel(){

    private val TAG = "DiaryViewModel"

    private val _reviewData = MutableLiveData<List<Review>>()
    val reviewData = _reviewData

    fun insertReviewWithMovie(review : Review){
        addDisposable(
            repo.insert(review)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        getAllReview()
                    },
                    {
                        Log.d(TAG, it.localizedMessage)
                    }
                )
        )
    }

    fun deleteAllReview(){
        addDisposable(
            repo.delete()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        getAllReview()
                    },
                    {
                        Log.d(TAG, it.localizedMessage)
                    }
                )

        )
    }

    fun getAllReview(){
        addDisposable(repo.getAllReview()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    reviewData.value =it
                }
            },{
                Log.d(TAG, it.localizedMessage)
            })
        )
    }

    fun getReviewByReviewId(){
        addDisposable(
            repo.getReviewByReviewId(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.run {
                        Log.d(" REVIEW_TEST " , this.title)
                    }
                },{
                    Log.d(TAG, it.localizedMessage)
                })
        )
    }

}