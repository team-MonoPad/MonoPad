package com.project.monopad.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.project.monopad.model.network.response.MovieInfoResultResponse
import com.project.monopad.model.network.response.MovieVideoResultResponse
import com.project.monopad.network.repository.MovieRepoImpl
import com.project.monopad.ui.base.BaseViewModel
import com.project.monopad.util.BaseUtil
import com.project.monopad.util.DateComparator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class MovieViewModel(private val repo: MovieRepoImpl) : BaseViewModel(){

    private val TAG = "MovieViewModel"

    private val _popularMovieData = MutableLiveData<List<MovieInfoResultResponse>>()
    val popularMovieData = _popularMovieData

    private val _nowPlayingMovieData = MutableLiveData<List<MovieInfoResultResponse>>()
    val nowPlayingMovieData = _nowPlayingMovieData

    private val _upcomingMovieData = MutableLiveData<List<MovieInfoResultResponse>>()
    val upcomingMovieData = _upcomingMovieData

    private val _topRatedMovieData = MutableLiveData<List<MovieInfoResultResponse>>()
    val topRatedMovieData = _topRatedMovieData

    private val _popularMovieVideoData = MutableLiveData<List<MovieVideoResultResponse>>()
    val popularMovieVideoData = _popularMovieVideoData


    fun popularMovieData() {
        addDisposable(
            repo.getPopularMovie(
                apikey = BaseUtil.API_KEY,
                language = BaseUtil.KR_LANGUAGE,
                page = 1,
                region = BaseUtil.KR_REGION
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    it.run {
                        popularMovieData.value = it.results
                    }
                }, {
                    Log.d(TAG, it.message.toString())
                })
        )
    }

    fun nowPlayMovieData() {
        addDisposable(
            repo.getNowPlayMovie(
                apikey = BaseUtil.API_KEY,
                language = BaseUtil.KR_LANGUAGE,
                page = 1
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    it.run {
                        nowPlayingMovieData.value = it.results
                    }
                }, {
                    Log.d(TAG, it.message.toString())
                })
        )

    }

    fun upcomingMovieData() {
        addDisposable(
            repo.getUpComingMovie(
                apikey = BaseUtil.API_KEY,
                language = BaseUtil.KR_LANGUAGE,
                page = 1,
                region = BaseUtil.KR_REGION
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    it.run {
                        //개봉일 순으로 정렬
                        Collections.sort(it.results, DateComparator())
                        upcomingMovieData.value = it.results
                    }
                }, {
                    Log.d(TAG, it.message.toString())
                })
        )
    }

    fun topRatedMovieData() {
        addDisposable(
            repo.getTopRatedMovie(
                apikey = BaseUtil.API_KEY,
                language = BaseUtil.KR_LANGUAGE,
                page = 1,
                region = BaseUtil.KR_REGION
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    it.run {
                        it.results.sort() //vote_count 순 으로 정렬
                        topRatedMovieData.value = it.results
                    }
                }, {
                    Log.d(TAG, it.message.toString())
                })
        )
    }

    fun popularMovieVideoData(movieId: Int){
        addDisposable(
            repo.getMovieVideo(
                movie_id = movieId,
                apikey = BaseUtil.API_KEY,
                language = BaseUtil.KR_LANGUAGE,
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.run {
                        _popularMovieVideoData.value = it.results
                    }
                }, {
                    Log.d(TAG, it.localizedMessage)
                })
        )
    }
}