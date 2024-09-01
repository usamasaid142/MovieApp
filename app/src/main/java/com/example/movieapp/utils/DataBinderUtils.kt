package com.example.movieapp.utils

import android.widget.ImageView
import android.widget.RatingBar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.movieapp.BuildConfig


object DataBinderUtils {
    @JvmStatic
    @BindingAdapter("imageApplyString")
    fun imageApplyString(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .asDrawable()
            .load("${BuildConfig.BASE_URL}${url}")
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("applyRating")
    fun applyRating(ratebar: RatingBar, rate: Double?) {
       ratebar.rating=rate?.toFloat()?: 1.0f
    }
}