package com.example.chapter12;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DiskLruCacheDemoActivity extends Activity {

    //50MB
    private static final long DISK_CACHE_SEIZE = 1024*1024*50;

    private DiskLruCache mDiskLruCache;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disk_lru_cache_demo);

        File diskCacheDir = getDiskCacheDir(this,"bitmap");
        if (!diskCacheDir.exists()){
            diskCacheDir.mkdirs();
        }

        try {
            mDiskLruCache = DiskLruCache.open(diskCacheDir,1,1,DISK_CACHE_SEIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private File getDiskCacheDir(Context context, String bitmap) {
        return null;

    }

    /*
    这里将图片的URL转成KEY
     */
    private String hashKeyFormUrl(String url){
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }



}
