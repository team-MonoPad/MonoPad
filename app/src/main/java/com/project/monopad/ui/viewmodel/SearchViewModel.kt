package com.project.monopad.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.monopad.data.model.network.response.MovieInfoResultResponse
import com.project.monopad.data.repository.MovieRepoImpl
import com.project.monopad.ui.base.BaseViewModel
import com.project.monopad.util.BaseUtil
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
            apikey = BaseUtil.API_KEY,
            language = BaseUtil.KR_LANGUAGE,
            query = query,
            page = 1
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    _searchMovieData.postValue(it.results)
                }
            },{
                Log.d(TAG, it.localizedMessage)
            })
        )
    }

}