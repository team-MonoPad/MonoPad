package com.project.monopad.ui.viewmodel

import android.app.Activity
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.project.monopad.R
import com.project.monopad.model.network.dto.Genre
import com.project.monopad.model.network.response.MovieDetailResponse
import com.project.monopad.network.repository.MovieRepoImpl
import com.project.monopad.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.glide.transformations.BlurTransformation

class DetailViewModel(private val repo: MovieRepoImpl) : BaseViewModel() {

    companion object{
        val IMAGE_URL : String = "https://image.tmdb.org/t/p/w500/"
    }

    private val _movieDetailData = MutableLiveData<MovieDetailResponse>()
    val movieDetailData = _movieDetailData

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
                    movieDetailData.value = it
                }
            },{
                //
            })
        )
    }

    fun releaseDateParsing(releaseDate: String) = releaseDate.replace("-","/")

    fun runtimeParsing(runtime: Int) = runtime.let { "${it/60}h ${it-(it/60)*60}m" }

    fun genreParsing(genreList: List<Genre>) : String{
        var genre = ""
        for(g in genreList){
            genre += "${g.name}, "
        }
        return genre.substring(0, genre.length-2)
    }

}

@BindingAdapter("bindPoster")
fun bindPoster(view: ImageView, imageUrl: String?){
    if(!imageUrl.isNullOrEmpty()){
        Glide.with(view.context)
            .load(DetailViewModel.IMAGE_URL+imageUrl)
            .fitCenter()
            .into(view)
    }
}

@BindingAdapter("bindBackPoster")
fun bindBackPoster(view: ImageView, imageUrl: String?){
    if(!imageUrl.isNullOrEmpty()){
        Glide.with(view.context)
            .load(DetailViewModel.IMAGE_URL+imageUrl)
            .fitCenter()
            .apply(RequestOptions.bitmapTransform(BlurTransformation(25,3)))
            .into(view)
    }
}
