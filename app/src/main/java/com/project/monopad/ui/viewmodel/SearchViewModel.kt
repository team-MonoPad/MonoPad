package com.project.monopad.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.monopad.data.model.network.response.MovieInfoResultResponse
import com.project.monopad.data.repository.MovieRepoImpl
import com.project.monopad.ui.base.BaseViewModel
import com.project.monopad.util.AppUtil.API_KEY
import com.project.monopad.util.AppUtil.KR_LANGUAGE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchViewModel(private val repo: MovieRepoImpl) : BaseViewModel() {

    private val TAG = "SEARCH VIEWMODEL"

    private val _searchMovieData = MutableLiveData<List<MovieInfoResultResponse>>()
    val searchMovieData : LiveData<List<MovieInfoResultResponse>>
        get() = _searchMovieData

    fun getSearchData(query: String){
        /* 비슷한 영화 정보 가져오기 */
        addDisposable(repo.getMovieSearch(
            apikey = API_KEY,
            language = KR_LANGUAGE,
            query = query,
            page = 1
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    val results = it.results
                    val movieList = ArrayList<MovieInfoResultResponse>()
                    for(i in results.indices){
                        val movie = results[i]
                        if(movie.poster_path != null){
                            movieList.add(movie)
                        }
                    }
                    _searchMovieData.postValue(movieList)

                }
            },{
                Log.d(TAG, it.localizedMessage)
            })
        )
    }

}