package com.example.jetpackpaging.adapter

import android.graphics.Color
import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.jetpackpaging.R
import com.squareup.picasso.Picasso

class ImageViewBindingAdapter {

    companion object{
        @JvmStatic
        @BindingAdapter("image")
        fun setImageVIew(imageView: ImageView, url: String){
            if (!TextUtils.isEmpty(url)){
                Picasso.get().load(url).placeholder(R.mipmap.ic_launcher).into(imageView)
            }else{
                imageView.setBackgroundColor(Color.GRAY)
            }
        }
    }


}