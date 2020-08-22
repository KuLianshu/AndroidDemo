package com.example.contentproviderdemo.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.contentproviderdemo.database.MyDatabaseHelper;
import com.example.contentproviderdemo.utils.LogUtils;

public class DatabaseProvider extends ContentProvider {
    
    
    public static final int BOOK_DIR = 0;
    public static final int BOOK_ITEM = 1;
    public static final int CATEGORY_DIR = 2;
    public static final int CATEGORY_ITEM = 3;

    public static final int BOOK_1= 4;
    
    private static UriMatcher uriMatcher;
    private static final String AUTHOR = "com.example.database.provider";

    private static final String TYPE = "vnd.android.cursor";

    private MyDatabaseHelper dbHelper;


    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHOR,"book",BOOK_DIR);
        /*
                *：表示匹配任意长度的任意字符，比如列名
                #：表示匹配任意长度的数字
         */
        uriMatcher.addURI(AUTHOR,"book/#",BOOK_ITEM);
        uriMatcher.addURI(AUTHOR,"category",CATEGORY_DIR);
        uriMatcher.addURI(AUTHOR,"category/#",CATEGORY_ITEM);
        uriMatcher.addURI(AUTHOR,"book/*",BOOK_1);

    }
    
    
    @Override
    public boolean onCreate() {
        dbHelper = new MyDatabaseHelper(getContext());

        return true;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                cursor = db.query("Book",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                cursor = db.query("Book",projection,"id = ?",new String[]{bookId},null,null,sortOrder);
                break;
            case CATEGORY_DIR:
                cursor = db.query("Category",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                cursor = db.query("Category",projection,"id = ?",new String[]{categoryId},null,null,sortOrder);
                break;

        }


        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
            case BOOK_ITEM:
                long newBookId = db.insert("Book",null,values);
                uriReturn = Uri.parse("content://"+AUTHOR+"/book/"+newBookId);
                break;
            case CATEGORY_DIR:
            case CATEGORY_ITEM:
                long newCategory = db.insert("Category",null,values);
                uriReturn = Uri.parse("content://"+AUTHOR+"/category/"+newCategory);
                break;

        }

        return uriReturn;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updatedRows = 0;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                updatedRows = db.update("Book",values,selection,selectionArgs);
                LogUtils.i("update ...");
                break;
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                updatedRows = db.update("Book",values,"id = ?",new String[]{bookId});
                LogUtils.i("update ... id = "+bookId);

                break;
            case CATEGORY_DIR:
                updatedRows = db.update("Category",values,selection,selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                updatedRows = db.update("Category",values,"id = ?",new String[]{categoryId});
                break;
            case BOOK_1:
                String column = uri.getPathSegments().get(1);
                LogUtils.i("update ... , column = "+column);

                break;
        }

        return updatedRows;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deletedRows = 0;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                deletedRows = db.delete("Book",selection,selectionArgs);
                LogUtils.i("delete ...");
                break;
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                String s = uri.getPathSegments().get(0);
                deletedRows = db.delete("Book","id = ?",new String[]{bookId});
                LogUtils.i("delete ... id = "+bookId+", s = "+s);
                break;
            case CATEGORY_DIR:
                deletedRows = db.delete("Category",selection,selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                deletedRows = db.delete("Category","id = ?",new String[]{categoryId});
                break;
        }

        return deletedRows;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
//        switch (uriMatcher.match(uri)){
//            case BOOK_DIR:
//                return TYPE+".dir/vnd."+AUTHOR+".book";
//            case BOOK_ITEM:
//                return TYPE+".item/vnd."+AUTHOR+".book";
//            case CATEGORY_DIR:
//                return TYPE+".dir/vnd."+AUTHOR+".category";
//            case CATEGORY_ITEM:
//                return TYPE+".item/vnd."+AUTHOR+".category";
//
//        }

        return null;
    }

}
