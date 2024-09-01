package com.example.movieapp.utils

import android.widget.ImageView
import android.widget.RatingBar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


object DataBinderUtils {
    @JvmStatic
    @BindingAdapter("imageApplyString")
    fun imageApplyString(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .asDrawable()
            .load("https://image.tmdb.org/t/p/w500${url}")
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("applyRating")
    fun applyRating(ratebar: RatingBar, rate: Double?) {
       ratebar.rating=rate?.toFloat()?: 1.0f
    }
}