package com.project.monopad.ui.imagebind

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.project.monopad.R
import com.project.monopad.util.BaseUtil
import com.project.monopad.util.DateUtil
import jp.wasabeef.glide.transformations.BlurTransformation
import java.util.*

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("bindBackPoster")
    fun bindBackPoster(view: ImageView, imageUrl: String?) {
        Glide.with(view.context)
            .load(BaseUtil.IMAGE_URL + imageUrl)
            .fitCenter()
            .apply(RequestOptions.bitmapTransform(BlurTransformation(13, 3)))
            .error(R.drawable.ic_baseline_error_outline_24)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("bindPoster")
    fun bindPoster(view: ImageView, imageUrl: String?) {
        Glide.with(view.context)
            .load(BaseUtil.IMAGE_URL + imageUrl)
            .fitCenter()
            .error(R.drawable.ic_baseline_error_outline_24)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("bindThumbNail")
    fun bindThumbNail(view: ImageView, imageUrl: String?) {
        Glide.with(view.context)
            .load(BaseUtil.THUMBNAIL_URL(imageUrl))
            .fitCenter()
            .error(R.drawable.ic_baseline_error_outline_24)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("homeBindPoster")
    fun homeBindPoster(view: ImageView, imageUrl: String?) {
        Glide.with(view.context)
            .load(BaseUtil.IMAGE_URL + imageUrl)
            .centerCrop()
            .error(R.drawable.ic_baseline_error_outline_24)
            .into(view)
    }

    /* review */
    @JvmStatic
    @BindingAdapter("bindReviewImage")
    fun bindReviewImage(view: ImageView, imagePath: String?) {
        Glide.with(view.context)
            .load(imagePath)
            .fitCenter()
            .error(R.drawable.ic_baseline_error_outline_24)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("bindReviewDate")
    fun bindReviewDate(view: TextView, date : Date) {
        view.text = DateUtil.convertDateToString(date)
    }

    @JvmStatic
    @BindingAdapter("bindEditReviewBackPoster")
    fun bindEditReviewBackPoster(view: ImageView, imageUrl: String?) {
        Glide.with(view.context)
            .load(imageUrl)
            .centerCrop()
            .apply(RequestOptions.bitmapTransform(BlurTransformation(13, 1)))
            .into(view)
    }
}
