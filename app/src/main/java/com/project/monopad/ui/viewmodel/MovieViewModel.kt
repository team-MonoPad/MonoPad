package com.project.monopad.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.project.monopad.model.network.response.MovieInfoResponse
import com.project.monopad.network.repository.MovieRepoImpl
import com.project.monopad.ui.base.BaseViewModel
import com.project.monopad.util.DateUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class MovieViewModel(private val repo: MovieRepoImpl) : BaseViewModel(){

    private val TAG = "MovieViewModel"

    val loading: MutableLiveData<Boolean> = MutableLiveData(false)

    val livePopularMovie: MutableLiveData<MovieInfoResponse> by lazy {
        MutableLiveData<MovieInfoResponse>()
    }

    val liveNowPlayingMovie: MutableLiveData<MovieInfoResponse> by lazy {
        MutableLiveData<MovieInfoResponse>()
    }

    val liveUpcomingMovie: MutableLiveData<MovieInfoResponse> by lazy {
        MutableLiveData<MovieInfoResponse>()
    }

    val liveTopRatedMovie: MutableLiveData<MovieInfoResponse> by lazy {
        MutableLiveData<MovieInfoResponse>()
    }

    fun popularMovieData() {
        addDisposable(
            repo.getPopularMovie(
                apikey = "84301bd818cef2f63643e7dffa8998ab",
                language = "ko-KR",
                page = 1,
                region = "kr"
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loading.postValue(true) }
                .subscribe({ it ->
                    it.run {
                        livePopularMovie.value = it
                    }
                }, {
                    Log.d(TAG, it.message.toString())
                })
        )
    }

    fun nowPlayMovieData() {
        addDisposable(repo.getNowPlayMovie(
            apikey = "84301bd818cef2f63643e7dffa8998ab",
            language = "ko-KR",
            page = 1
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loading.postValue(true) }
            .subscribe({ it ->
                it.run {
                    liveNowPlayingMovie.value = it
                }
            }, {
                Log.d(TAG, it.message.toString())
            })
        )

    }

    fun upcomingMovieData() {
        addDisposable(
            repo.getUpComingMovie(
                apikey = "84301bd818cef2f63643e7dffa8998ab",
                language = "ko-KR",
                page = 1
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loading.postValue(true) }
                .subscribe({ it ->
                    it.run {
                        //개봉일 순으로 정렬
                        it.results.sortWith(Comparator { a, b -> DateUtil.getDayDifference(a.release_date).toInt() - DateUtil.getDayDifference(b.release_date).toInt()})
                        liveUpcomingMovie.value = it
                    }
                }, {
                    Log.d(TAG, it.message.toString())
                })
        )
    }

    fun topRatedMovieData() {
        addDisposable(
            repo.getTopRatedMovie(
                apikey = "84301bd818cef2f63643e7dffa8998ab",
                language = "ko-KR",
                page = 1,
                region = "kr"
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loading.postValue(true) }
                .subscribe({ it ->
                    it.run {
                        it.results.sort()
                        liveTopRatedMovie.value = it
                    }
                }, {
                    Log.d(TAG, it.message.toString())
                })
        )
    }
}