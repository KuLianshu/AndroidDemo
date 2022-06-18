package com.example.chapter12;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.chapter12.utils.BitmapUtils;

public class BitmapDemoActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_bitmap_demo);

        ImageView imageView = findViewById(R.id.image);
        Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromResource(getResources(),R.mipmap.flower,500,500);
        imageView.setImageBitmap(bitmap);

    }
}
