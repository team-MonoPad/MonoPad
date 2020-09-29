package com.project.monopad.ui.viewmodel

import android.util.Log
import com.project.monopad.network.repository.MovieRepoImpl
import com.project.monopad.network.repository.TvRepoImpl
import com.project.monopad.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TvViewModel (private val repo : TvRepoImpl) : BaseViewModel(){

     private val TAG = "TvViewModel"

    fun onTheAirTV() {
        addDisposable(repo.getPopularTv(
            apikey = "84301bd818cef2f63643e7dffa8998ab",
            language = "ko-KR",
            page = 1
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ it ->
                it.run {
                    this.results.forEach {MRR ->
                        Log.d(TAG, MRR.name)
                    }
                }
            },{
                Log.d(TAG, it.localizedMessage.toString())
            })
        )

    }

}