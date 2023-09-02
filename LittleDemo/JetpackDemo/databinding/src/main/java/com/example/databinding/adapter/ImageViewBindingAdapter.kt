package com.example.databinding.adapter

import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.databinding.R


class ImageViewBindingAdapter {

    companion object{
        @JvmStatic
        @BindingAdapter("image")
        fun setImage(imageView: ImageView, url: String){
            Log.i("WLY","--------------------1")
            Glide.with(imageView)
                .load(url)
                .placeholder(R.mipmap.ic_launcher_round)
                .into(imageView)
        }

        @JvmStatic
        @BindingAdapter("image")
        fun setImage(imageView: ImageView, srcId: Int){
            Log.i("WLY","--------------------2")
            imageView.setImageResource(srcId)
        }


        @JvmStatic
        @BindingAdapter(value = ["image","defaultImageResource"], requireAll = false)
        fun setImage(imageView: ImageView, url: String ?, srcId: Int){
            Log.i("WLY","--------------------3")
            if (!TextUtils.isEmpty(url)){
                Glide.with(imageView)
                    .load(url)
                    .placeholder(R.drawable.loading)
                    .into(imageView)
            }else{
                imageView.setImageResource(srcId)
            }
        }



        @JvmStatic
        @BindingAdapter("itemImage")
        fun setItemImage(imageView: ImageView, url: String){
            Glide.with(imageView)
                .load(url)
                .placeholder(R.drawable.loading)
                .into(imageView)
        }

    }

}

