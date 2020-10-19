package com.project.monopad.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.project.monopad.model.network.response.MovieImagePosterResponse
import com.project.monopad.network.repository.MovieRepoImpl
import com.project.monopad.ui.base.BaseViewModel
import com.project.monopad.util.BaseUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ImageSelectViewModel(private val repo: MovieRepoImpl) : BaseViewModel() {

    private val _movieImageData = MutableLiveData<List<MovieImagePosterResponse>>()
    val movieImageData = _movieImageData


    fun getMovieData(movieId: Int){
        addDisposable(repo.getMovieImages(
            movie_id = movieId,
            apikey = BaseUtil.API_KEY,
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    _movieImageData.value = it.posters
                }
            },{
                //
            })
        )
    }

}