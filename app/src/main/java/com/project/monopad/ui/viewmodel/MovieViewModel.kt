package com.project.monopad.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.monopad.model.entity.Review
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
    val popularMovieData : LiveData<List<MovieInfoResultResponse>>
        get() = _popularMovieData

    private val _nowPlayingMovieData = MutableLiveData<List<MovieInfoResultResponse>>()
    val nowPlayingMovieData : LiveData<List<MovieInfoResultResponse>>
        get() = _nowPlayingMovieData

    private val _upcomingMovieData = MutableLiveData<List<MovieInfoResultResponse>>()
    val upcomingMovieData: LiveData<List<MovieInfoResultResponse>>
        get() = _upcomingMovieData

    private val _topRatedMovieData = MutableLiveData<List<MovieInfoResultResponse>>()
    val topRatedMovieData :  LiveData<List<MovieInfoResultResponse>>
        get() = _topRatedMovieData

    private val _videoData = MutableLiveData<List<MovieVideoResultResponse>>()
    val videoData :  LiveData<List<MovieVideoResultResponse>>
        get() = _videoData

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
                        _popularMovieData.postValue(it.results)
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
                        _nowPlayingMovieData.postValue(it.results)
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
                        _upcomingMovieData.postValue(it.results)
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
                        _topRatedMovieData.postValue(it.results)
                    }
                }, {
                    Log.d(TAG, it.message.toString())
                })
        )
    }

    fun videoData(movieId: Int){
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
                        _videoData.postValue(it.results)
                    }
                }, {
                    Log.d(TAG, it.localizedMessage)
                })
        )
    }
}