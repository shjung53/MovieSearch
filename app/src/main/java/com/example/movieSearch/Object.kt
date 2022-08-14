package com.example.movieSearch

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object ImgBinding{
    @BindingAdapter("movieImg")
    @JvmStatic
    fun glideImg(v: ImageView, image: String){
        Glide.with(v).load(image).into(v)
    }
}
