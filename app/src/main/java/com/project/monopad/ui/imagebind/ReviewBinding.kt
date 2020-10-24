package com.project.monopad.ui.imagebind

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.project.monopad.R
import com.project.monopad.util.DateUtil
import java.util.*

object ReviewBinding {
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
        val format = "yyyy년 MM월 dd일"
        view.text = DateUtil.convertDateToString(date, format)
    }
}