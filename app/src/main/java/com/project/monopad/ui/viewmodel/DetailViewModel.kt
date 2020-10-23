package com.project.monopad.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.project.monopad.model.network.response.*
import com.project.monopad.network.repository.MovieRepoImpl
import com.project.monopad.ui.base.BaseViewModel
import com.project.monopad.util.BaseUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailViewModel(private val repo: MovieRepoImpl) : BaseViewModel() {

    private val TAG = "DETAIL VIEWMODEL"

    private val _movieDetailData = MutableLiveData<MovieDetailResponse>()
    val movieDetailData = _movieDetailData

    private val _movieCastData = MutableLiveData<List<MovieCastResponse>>()
    val movieCastData = _movieCastData

    private val _movieCrewData = MutableLiveData<List<MovieCrewResponse>>()
    val movieCrewData = _movieCrewData

    private val _similarMovieData = MutableLiveData<List<MovieInfoResultResponse>>()
    val similarMovieData = _similarMovieData

    private val _recommendMovieData = MutableLiveData<List<MovieInfoResultResponse>>()
    val recommendMovieData = _recommendMovieData

    private val _movieTrailerData = MutableLiveData<List<MovieVideoResultResponse>>()
    val movieTrailerData = _movieTrailerData

    fun getDetailData(movieId : Int){
        /* 영화 상세 정보 데이터 가져오기 */
        addDisposable(repo.getMovieDetail(
            movie_id = movieId,
            apikey = BaseUtil.API_KEY,
            language = BaseUtil.KR_LANGUAGE,
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    _movieDetailData.value = it
                }
            },{
                Log.d(TAG, it.localizedMessage)
            })
        )

        /* 영화 출연진, 스태프 정보 가져오기 */
        addDisposable(repo.getMovieCredits(
            movie_id = movieId,
            apikey = BaseUtil.API_KEY,
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    _movieCastData.value = it.cast
                    _movieCrewData.value = it.crew
                }
            },{
                Log.d(TAG, it.localizedMessage)
            })
        )

        /* 비슷한 영화 정보 가져오기 */
        addDisposable(repo.getSimilarMovie(
            movie_id = movieId,
            apikey = BaseUtil.API_KEY,
            language = BaseUtil.KR_LANGUAGE,
            page = 1
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    _similarMovieData.value = it.results
                }
            },{
                Log.d(TAG, it.localizedMessage)
            })
        )

        /* 추천 영화 정보 가져오기 */
        addDisposable(repo.getRecommendationsMovie(
            movie_id = movieId,
            apikey = BaseUtil.API_KEY,
            language = BaseUtil.KR_LANGUAGE,
            page = 1
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    _recommendMovieData.value = it.results
                }
            },{
                Log.d(TAG, it.localizedMessage)
            })
        )

        /* 영화 트레일러 가져오기 */
        addDisposable(repo.getMovieVideo(
            movie_id = movieId,
            apikey = BaseUtil.API_KEY,
            language = BaseUtil.KR_LANGUAGE,
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                   _movieTrailerData.value = it.results
                }
            },{
                Log.d(TAG, it.localizedMessage)
            })
        )
    }

}


