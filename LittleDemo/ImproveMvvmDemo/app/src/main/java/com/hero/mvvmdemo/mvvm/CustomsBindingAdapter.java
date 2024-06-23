package com.hero.mvvmdemo.mvvm;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.hero.mvvmdemo.R;
import com.squareup.picasso.Picasso;

/**
 * <pre>
 * 自定义BindingAdapter
 * https://www.jianshu.com/p/b93d5ab4494b
 * </pre>
 */
public class CustomsBindingAdapter {
    //定义了之后 app属性就多了 image, defaultImage
    @BindingAdapter(value = {"image", "defaultImage"}, requireAll = false)
    public static void setImage(ImageView imageView, String imageUrl, int defaultImageResource) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(imageView);
        } else {
            imageView.setImageResource(defaultImageResource);
        }
    }
}