package com.project.monopad.ui.imagebind

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.project.monopad.util.BaseUtil
import jp.wasabeef.glide.transformations.BlurTransformation

object DetailImageBinding{

    @JvmStatic
    @BindingAdapter("bindFrontPoster")
    fun bindFrontPoster(view: ImageView, imageUrl: String?){
        if(!imageUrl.isNullOrEmpty()){
            Glide.with(view.context)
                .load(BaseUtil.IMAGE_URL +imageUrl)
                .fitCenter()
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("bindBackPoster")
    fun bindBackPoster(view: ImageView, imageUrl: String?){
        if(!imageUrl.isNullOrEmpty()){
            Glide.with(view.context)
                .load(BaseUtil.IMAGE_URL +imageUrl)
                .fitCenter()
                .apply(RequestOptions.bitmapTransform(BlurTransformation(13,3)))
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("bindCasterPoster")
    fun bindCasterPoster(view: ImageView, imageUrl: String?){
        if(!imageUrl.isNullOrEmpty()){
            Glide.with(view.context)
                .load(BaseUtil.IMAGE_URL +imageUrl)
                .fitCenter()
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("bindSimilarPoster")
    fun bindSimilarPoster(view: ImageView, imageUrl: String?){
        if(!imageUrl.isNullOrEmpty()){
            Glide.with(view.context)
                .load(BaseUtil.IMAGE_URL +imageUrl)
                .fitCenter()
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("bindRecommendPoster")
    fun bindRecommendPoster(view: ImageView, imageUrl: String?){
        if(!imageUrl.isNullOrEmpty()){
        Glide.with(view.context)
            .load(BaseUtil.IMAGE_URL +imageUrl)
            .fitCenter()
            .into(view)
    }
}
}