package com.example.contentproviderdemo.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;
import com.example.contentproviderdemo.helper.MyDatabaseHelper;

public class DatabaseProvider extends ContentProvider {

    public static final int LANGUAGE_DIR=0;
    public static final String TABLE_NAME = "LanguageDB";
    // uri=Uri.parse("content://com.example.contentproviderdemo.provider/language/");
    public static final String AUTHORITY="com.example.contentproviderdemo.provider";
    public static UriMatcher uriMatcher;
    private static MyDatabaseHelper dbHelper;

    static {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"language",LANGUAGE_DIR);
    }


    @Override
    public boolean onCreate() {
        dbHelper=new MyDatabaseHelper(getContext(),"LanguageStore.db",null,1);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=null;
        if (uriMatcher.match(uri) == LANGUAGE_DIR) {
            cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        }
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Uri uriReturn=null;
        if (uriMatcher.match(uri) == LANGUAGE_DIR) {
            long newLanguageId = db.insert(TABLE_NAME, null, values);
            uriReturn = Uri.parse("content://" + AUTHORITY + "/language/" + newLanguageId);
        }
        return uriReturn;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String bookId=uri.getPathSegments().get(1);
        int updatedRows =db.update(TABLE_NAME,values,"id=?",new String[]{bookId});
        Log.i("WLY","updatedRows : "+updatedRows);
        return updatedRows;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String bookId=uri.getPathSegments().get(1);
        int deleteRows =db.delete(TABLE_NAME,"id=?",new String[]{bookId});
        Log.i("WLY","updatedRows : "+deleteRows);
        return deleteRows;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }
}
