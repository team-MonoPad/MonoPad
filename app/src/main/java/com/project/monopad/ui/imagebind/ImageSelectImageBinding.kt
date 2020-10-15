package com.project.monopad.ui.imagebind

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.project.monopad.util.BaseUtil

object ImageSelectImageBinding {

    @JvmStatic
    @BindingAdapter("bindPosterImage")
    fun bindPosterImage(view: ImageView, imageUrl: String?) {
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(view.context)
                .load(BaseUtil.IMAGE_URL + imageUrl)
                .fitCenter()
                .into(view)
        }
    }
}