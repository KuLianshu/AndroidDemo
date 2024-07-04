package com.wly.mvvm.adapter

import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.wly.mvvm.R


object CustomsBindingAdapter {
    @BindingAdapter(value = ["image", "defaultImage"], requireAll = false)
    @JvmStatic
    fun setImage(imageView: ImageView, imageUrl: String, defaultImageResource: Int) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(imageView)
        } else {
            imageView.setImageResource(defaultImageResource)
        }
    }
}
