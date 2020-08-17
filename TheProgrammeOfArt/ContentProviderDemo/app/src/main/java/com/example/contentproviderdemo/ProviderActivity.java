package com.example.contentproviderdemo;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.example.contentproviderdemo.entity.Book;
import com.example.contentproviderdemo.entity.User;
import com.example.contentproviderdemo.utils.LogUtils;

public class ProviderActivity extends Activity {

    private static final String  TAG = "ProviderActivity";




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

        //uriString:唯一标识，对应 android:authorities
        Uri bookUri = Uri.parse("content://com.example.contentproviderdemo.provider/book");
        ContentValues values = new ContentValues();
        values.put("_id",6);;
        values.put("name","刺杀骑士团长");
        getContentResolver().insert(bookUri,values);
        Cursor bookCursor = getContentResolver().query(bookUri,new String[]{"_id","name"},null,null,null);
        if (bookCursor!=null){
            while (bookCursor.moveToNext()){
                Book book = new Book();
                book.setId(bookCursor.getInt(0));
                book.setName(bookCursor.getString(1));
                LogUtils.i(TAG,"query book:"+book.toString());
            }
            bookCursor.close();
        }

        Uri userUri = Uri.parse("content://com.example.contentproviderdemo.provider/user");
        Cursor userCursor = getContentResolver().query(userUri,new String[]{"_id","name","sex"},null,null,null);
        if (userCursor!=null){
            while (userCursor.moveToNext()){
                User user = new User();
                user.setId(userCursor.getInt(0));
                user.setName(userCursor.getString(1));
                user.setSex(userCursor.getInt(2));
                LogUtils.i(TAG,"query user:"+user.toString());
            }
            userCursor.close();
        }

    }


}
