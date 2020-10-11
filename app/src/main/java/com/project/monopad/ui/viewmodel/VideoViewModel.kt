package com.project.monopad.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.project.monopad.model.network.response.MovieVideoResponse
import com.project.monopad.model.network.response.MovieVideoResultResponse
import com.project.monopad.network.repository.MovieRepoImpl
import com.project.monopad.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class VideoViewModel (private val repo : MovieRepoImpl) : BaseViewModel(){

     private val TAG = "VideoViewModel"

    val liveVideo = MutableLiveData<MovieVideoResponse>()

    fun getVideoData(id: Int) {
        addDisposable(repo.getMovieVideo(
            apikey = "84301bd818cef2f63643e7dffa8998ab",
            language = "ko-KR",
            movie_id = id
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ it ->
                it.run {
                    liveVideo.value = it
                }
            },{
                Log.d(TAG, it.localizedMessage.toString())
            })
        )

    }

}