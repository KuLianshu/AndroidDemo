package com.example.chapter12;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.chapter12.utils.BitmapUtils;

public class LruCacheDemoActivity extends Activity{

    private static final String key = "";
    private LruCache<String,Bitmap> mMemoryCache;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lru_cache_demo);

        //获取当前进程的可用内存
        int maxMemory = (int) (Runtime.getRuntime().maxMemory()/1024);
        //这里设置缓存的总量大小为可用内存的1/8，单位为KB
        int cacheSize = maxMemory/8;
        mMemoryCache =new LruCache<String, Bitmap>(cacheSize){

            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes()*bitmap.getHeight()/1024;
            }
        };

        ImageView imageView = findViewById(R.id.image);
        Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromResource(getResources(),R.mipmap.flower,500,500);

        //添加缓存对象
        mMemoryCache.put(key,bitmap);
        //获取缓存对象
        Bitmap bitmap1 = mMemoryCache.get(key);

        imageView.setImageBitmap(bitmap1);


    }
}
