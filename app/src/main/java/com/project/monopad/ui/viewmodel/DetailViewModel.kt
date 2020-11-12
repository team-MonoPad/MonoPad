package com.project.monopad.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.monopad.data.model.entity.Movie
import com.project.monopad.data.model.entity.Review
import com.project.monopad.data.model.network.response.*
import com.project.monopad.data.repository.MovieRepoImpl
import com.project.monopad.data.repository.ReviewRepoImpl
import com.project.monopad.ui.base.BaseViewModel
import com.project.monopad.util.AppUtil.API_KEY
import com.project.monopad.util.AppUtil.KR_LANGUAGE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailViewModel(private val movieRepo: MovieRepoImpl,
                      private val reviewRepo: ReviewRepoImpl ) : BaseViewModel() {

    private val TAG = "DETAIL VIEWMODEL"

    private val _movieDetailData = MutableLiveData<MovieDetailResponse>()
    val movieDetailData: LiveData<MovieDetailResponse>
        get() = _movieDetailData

    private val _movieCastData = MutableLiveData<List<MovieCastResponse>>()
    val movieCastData: LiveData<List<MovieCastResponse>>
        get() = _movieCastData

    private val _movieCrewData = MutableLiveData<List<MovieCrewResponse>>()
    val movieCrewData: LiveData<List<MovieCrewResponse>>
        get() = _movieCrewData

    private val _similarMovieData = MutableLiveData<List<MovieInfoResultResponse>>()
    val similarMovieData: LiveData<List<MovieInfoResultResponse>>
        get() = _similarMovieData

    private val _recommendMovieData = MutableLiveData<List<MovieInfoResultResponse>>()
    val recommendMovieData: LiveData<List<MovieInfoResultResponse>>
        get() = _recommendMovieData

    private val _movieTrailerData = MutableLiveData<List<MovieVideoResultResponse>>()
    val movieTrailerData: LiveData<List<MovieVideoResultResponse>>
        get() = _movieTrailerData

    private val _reviewData = MutableLiveData<List<Movie>>()
    val reviewData: LiveData<List<Movie>>
        get() = _reviewData

    fun getDetailData(movieId : Int){
        /* 영화 상세 정보 데이터 가져오기 */
        addDisposable(movieRepo.getMovieDetail(
            movie_id = movieId,
            apikey = API_KEY,
            language = KR_LANGUAGE,
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    _movieDetailData.postValue(it)
                }
            },{
                Log.d(TAG, it.localizedMessage)
            })
        )

        /* 영화 출연진, 스태프 정보 가져오기 */
        addDisposable(movieRepo.getMovieCredits(
            movie_id = movieId,
            apikey = API_KEY,
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    _movieCastData.postValue(it.cast)
                    _movieCrewData.postValue(it.crew)
                }
            },{
                Log.d(TAG, it.localizedMessage)
            })
        )

        /* 비슷한 영화 정보 가져오기 */
        addDisposable(movieRepo.getSimilarMovie(
            movie_id = movieId,
            apikey = API_KEY,
            language = KR_LANGUAGE,
            page = 1
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    _similarMovieData.postValue(it.results)
                }
            },{
                Log.d(TAG, it.localizedMessage)
            })
        )

        /* 추천 영화 정보 가져오기 */
        addDisposable(movieRepo.getRecommendationsMovie(
            movie_id = movieId,
            apikey = API_KEY,
            language = KR_LANGUAGE,
            page = 1
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    _recommendMovieData.postValue(it.results)
                }
            },{
                Log.d(TAG, it.localizedMessage)
            })
        )

        /* 영화 트레일러 가져오기 */
        addDisposable(movieRepo.getMovieVideo(
            movie_id = movieId,
            apikey = API_KEY,
            language = KR_LANGUAGE,
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                   _movieTrailerData.postValue(it.results)
                }
            },{
                Log.d(TAG, it.localizedMessage)
            })
        )
    }

    fun getReviewData(){
        addDisposable(reviewRepo.getAllReview()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    val reviewList = it as ArrayList<Review>
                    val movieList =  ArrayList<Movie>()
                    for(i in reviewList.indices){
                        movieList.add(reviewList[i].movie)
                    }
                    _reviewData.postValue(movieList)
                }
            },{
                Log.d(TAG, it.localizedMessage)
            })
        )
    }

}


