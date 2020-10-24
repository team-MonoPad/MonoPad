package com.project.monopad.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.project.monopad.model.network.response.MovieInfoResultResponse
import com.project.monopad.network.repository.MovieRepoImpl
import com.project.monopad.ui.base.BaseViewModel
import com.project.monopad.util.BaseUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchViewModel(private val repo: MovieRepoImpl) : BaseViewModel() {

    private val TAG = "SEARCH VIEWMODEL"

    private val _searchMovieData = MutableLiveData<List<MovieInfoResultResponse>>()
    val searchMovieData = _searchMovieData

    fun getSearchData(){
        /* 비슷한 영화 정보 가져오기 */
        addDisposable(repo.getMovieSearch(
            apikey = BaseUtil.API_KEY,
            language = BaseUtil.KR_LANGUAGE,
            query = "범죄",
            page = 1
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    _searchMovieData.value = it.results
                }
            },{
                Log.d(TAG, it.localizedMessage)
            })
        )
    }

}