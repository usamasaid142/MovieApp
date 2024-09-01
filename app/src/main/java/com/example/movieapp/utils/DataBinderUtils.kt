package com.example.movieapp.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.movieapp.BuildConfig


object DataBinderUtils {
    @JvmStatic
    @BindingAdapter("imageApplyString")
    fun imageApplyString(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .asDrawable()
            .load("vpnVM9B6NMmQpWeZvzLvDESb2QY.jpg")
            .into(imageView)
    }
}