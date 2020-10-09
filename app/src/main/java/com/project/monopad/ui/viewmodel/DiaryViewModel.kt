package com.project.monopad.ui.viewmodel

import android.util.Log
import com.project.monopad.model.entity.Movie
import com.project.monopad.model.entity.Review
import com.project.monopad.model.network.dto.Genre
import com.project.monopad.network.repository.ReviewRepoImpl
import com.project.monopad.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DiaryViewModel(private val repo : ReviewRepoImpl): BaseViewModel(){

    fun insertReviewWithMovie(){
        //add test data
        val genre = Genre(1,"action")
        val genre1 = Genre(2,"fantasy")
        val moview = Movie(1231, "post-paath", "title","overview","2020/08/01", listOf(genre, genre1))
        addDisposable(
            repo.insert(
                review = Review(review_poster = "poster_path",title = "title",date = "2020/08/01",comment = "good!! nice!!", rating = 1.1, movie = moview)
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        //successEvent.call()
                        Log.d("INSERT ACTION", "INSERT SUCCESS")
                    },
                    { error -> // error event
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
                        Log.d("DELETE ACTION", "DELETE SUCCESS")
                    },
                    { error -> // error event
                        Log.d("DELETE ACTION ", error.localizedMessage)
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
                    this.forEach {rv ->
                        Log.d(" ALL REVIEW_TEST " , rv.toString())
                    }
                }
            },{
                Log.d(" ALL REVIEW_TEST " , it.localizedMessage)
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
                Log.d(" REVIEW_TEST " , it.localizedMessage)
            })
        )
    }

}