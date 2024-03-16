package com.example.contentproviderdemo.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.contentproviderdemo.provider.DatabaseProvider;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_LANGUAGE_STORE = "create table "+ DatabaseProvider.TABLE_NAME+"(" +
            "id integer primary key autoincrement," +
            "language text)";

    public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LANGUAGE_STORE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
