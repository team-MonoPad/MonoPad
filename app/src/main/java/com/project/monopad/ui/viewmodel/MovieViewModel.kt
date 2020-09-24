package com.project.monopad.ui.viewmodel

import android.util.Log
import com.project.monopad.network.repository.MovieRepoImpl
import com.project.monopad.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieViewModel (private val repo : MovieRepoImpl) : BaseViewModel(){

     private val TAG = "MovieViewModel"

    fun nowPlayMovieData() {
        addDisposable(repo.getNowPlayMovie(
            apikey = "84301bd818cef2f63643e7dffa8998ab",
            language = "ko-KR",
            page = 1
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ it ->
                it.run {
                    this.results.forEach {MRR ->
                        Log.d(TAG, MRR.title)
                    }
                }
            },{
                Log.d(TAG, it.localizedMessage.toString())
            })
        )

    }

}