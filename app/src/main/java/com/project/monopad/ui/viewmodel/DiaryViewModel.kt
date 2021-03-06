package com.project.monopad.ui.viewmodel

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.project.monopad.data.model.entity.Review
import com.project.monopad.data.model.network.response.MovieDetailResponse
import com.project.monopad.data.repository.ReviewRepoImpl
import com.project.monopad.ui.base.BaseViewModel
import com.project.monopad.util.AppUtil.API_KEY
import com.project.monopad.util.AppUtil.KR_LANGUAGE
import com.project.monopad.util.AppUtil.saveImage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File


class DiaryViewModel(
    private val repo : ReviewRepoImpl,
    private val context : Context
): BaseViewModel(){

    private val _reviewData : LiveData<List<Review>> = repo.getAllReviewInLiveData()
    val reviewData : LiveData<List<Review>>
        get() = _reviewData

    private val _singleReviewData = MutableLiveData<Review>()
    val singleReviewData : LiveData<Review>
        get() = _singleReviewData

    private val _imagePathData = MutableLiveData<String>()
    val imagePathData : LiveData<String>
        get() = _imagePathData

    private val _isCompleted = MutableLiveData<Boolean>()
    val isCompleted: LiveData<Boolean>
        get() = _isCompleted
  
    private val _movieDetailInfo = MutableLiveData<MovieDetailResponse>()
    val movieDetailInfo: LiveData<MovieDetailResponse>
            get() = _movieDetailInfo

    fun insertReviewWithMovie(review : Review){
        addDisposable(
            repo.insertReview(review)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _isCompleted.postValue(true)
                    },
                    {
                        Log.d(TAG, it.localizedMessage)
                    }
                )
        )
    }

    fun deleteReviewByReviewId(review_id: Int){
        addDisposable(
            repo.deleteReviewById(review_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _isCompleted.postValue(false)
                    },
                    {
                        Log.d(TAG, it.localizedMessage)
                    }
                )

        )
    }

    fun deleteAllReview(){
        addDisposable(
            repo.deleteAllReview()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {

                    },
                    {
                        Log.d(TAG, it.localizedMessage)
                    }
                )

        )
    }

    fun getReviewByReviewId(id : Int){
        addDisposable(
            repo.getReviewById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.run {
                        Log.d("getReviewByReviewId" , this.toString())
                        _singleReviewData.postValue(it)
                    }
                },{
                    Log.d(TAG, it.localizedMessage)
                })
        )
    }

    fun getMovieDetailInfo(movieId: Int){
        addDisposable(repo.getMovieDetail(
            movie_id = movieId,
            apikey = API_KEY,
            language = KR_LANGUAGE,
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    _movieDetailInfo.value = it
                }
            },{
                Log.d(TAG, it.localizedMessage)
            })
        )
    }

    fun updateReview(review: Review){
        addDisposable(
            repo.updateReview(review)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _isCompleted.postValue(true)
                },{
                    Log.d(TAG, it.localizedMessage)
                })
        )
    }

    fun downloadImage (imageURL: String, title: String, saveTime: String) {
        val teamName = "MONOPAD"
        val dirPath = "${context.getExternalFilesDir(teamName)?.absolutePath}/${title+saveTime}/"
        val dir = File(dirPath)
        val fileName = title+"_download_poster_"+saveTime

        Log.d("다운로드!", dirPath)
        Glide.with(context)
            .load(imageURL)
            .into(object : CustomTarget<Drawable>() {
            override fun onLoadCleared(placeholder: Drawable?) {}
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                val bitmap = (resource as BitmapDrawable).bitmap
                val direct = saveImage(bitmap, dir, fileName)
                if (!direct.isBlank()) {
                    if (direct.contentEquals("already_exist")) {
                        _imagePathData.postValue(dirPath+fileName)
                    } else {
                        _imagePathData.postValue(direct)
                    }
                }
            }
        })
    }

    fun deleteImageInFilesDir(imgPath : String){
        val file : File = File(imgPath)
        if (file.exists()){
            file.delete()
            Log.d("파일 존재 :","파일 삭제 완료!")
        }else{
            Log.d("파일 미존재 :","파일 삭제 안함!")
        }
    }

    companion object{
        private const val TAG = "DiaryViewModel"
    }
}