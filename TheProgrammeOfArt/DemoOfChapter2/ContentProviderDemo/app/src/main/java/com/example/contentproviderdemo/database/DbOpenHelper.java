package com.example.contentproviderdemo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "book_provider.db";
    public static final String BOOK_TABLE_NAME = "book";
    public static final String USER_TABLE_NAME = "user";
    public static final int DB_VERSION = 1;

    //图书和用户信息表
    private static final String CREATE_BOOK_TABLE = "CREATE TABLE book(_id INTEGER PRIMARY KEY, name TEXT)";

    private static final String CREATE_USER_TABLE = "CREATE TABLE user(_id INTEGER PRIMARY KEY, name TEXT, sex INT)";


    public DbOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK_TABLE);
        db.execSQL(CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
