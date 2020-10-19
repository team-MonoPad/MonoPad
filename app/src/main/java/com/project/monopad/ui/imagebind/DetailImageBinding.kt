package com.project.monopad.ui.imagebind

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.project.monopad.R
import com.project.monopad.util.BaseUtil
import jp.wasabeef.glide.transformations.BlurTransformation

object DetailImageBinding {

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
    @BindingAdapter("bindFilmoPoster")
    fun bindFilmoPoster(view: ImageView, imageUrl: String?) {
        Glide.with(view.context)
            .load(BaseUtil.IMAGE_URL + imageUrl)
            .error(R.drawable.ic_baseline_error_outline_24)
            .fitCenter()
            .into(view)
    }
}
