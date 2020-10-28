package com.project.monopad.ui.viewmodel

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.project.monopad.model.entity.Review
import com.project.monopad.network.repository.ReviewRepoImpl
import com.project.monopad.ui.base.BaseViewModel
import com.project.monopad.util.DownloadUtil.saveImage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File


class DiaryViewModel(
    private val repo : ReviewRepoImpl,
    private val context : Context
): BaseViewModel(){

    private val TAG = "DiaryViewModel"

    private val _reviewData = MutableLiveData<List<Review>>()
    val reviewData = _reviewData

    private val _imagePathData = MutableLiveData<String>()
    val imagePathData = _imagePathData

    fun insertReviewWithMovie(review : Review){
        addDisposable(
            repo.insert(review)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        getAllReview()
                    },
                    {
                        Log.d(TAG, it.localizedMessage)
                    }
                )
        )
    }

    fun deleteAllReview(){
        addDisposable(
            repo.delete()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        getAllReview()
                    },
                    {
                        Log.d(TAG, it.localizedMessage)
                    }
                )

        )
    }

    fun getAllReview(){
        addDisposable(repo.getAllReview()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    reviewData.value =it
                }
            },{
                Log.d(TAG, it.localizedMessage)
            })
        )
    }

    fun getReviewByReviewId(id : Int){
        addDisposable(
            repo.getReviewByReviewId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.run {
                        Log.d(" REVIEW_TEST " , this.title)
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
                    getAllReview()
                },{
                    Log.d(TAG, it.localizedMessage)
                })
        )
    }

    fun downloadImage (imageURL : String, title:String) {
        val teamName = "MONOPAD"
        val dirPath = "${context.getExternalFilesDir(teamName)?.absolutePath}/${title}/"
        val dir = File(dirPath)
        val fileName = title+"_download_poster"

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

}