package com.project.monopad.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.project.monopad.network.repository.MovieRepoImpl
import com.project.monopad.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailViewModel(private val repo: MovieRepoImpl) : BaseViewModel() {

    private val _title = MutableLiveData<String>()
    val title = _title

    fun getDetailData(){
        addDisposable(repo.getMovieDetail(
            movie_id = 479718,
            apikey = "84301bd818cef2f63643e7dffa8998ab",
            language = "ko-KR",
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    _title.value = title
                }
            },{
                //
            })
        )
    }

}