package com.example.contentproviderdemo.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.contentproviderdemo.database.DbOpenHelper;
import com.example.contentproviderdemo.utils.LogUtils;

public class BookProvider extends ContentProvider {

    public static final String AUTHORITY = "com.example.contentproviderdemo.provider";

    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" +AUTHORITY+"/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://"+ AUTHORITY+"/user");

    static {
        sUriMatcher.addURI(AUTHORITY,"book",BOOK_URI_CODE);
        sUriMatcher.addURI(AUTHORITY,"user",USER_URI_CODE);
    }

    private Context mContext;

    private SQLiteDatabase mDb;


    @Override
    public boolean onCreate() {
        //运行在main线程中
        LogUtils.i("onCreate, current Thread: "+Thread.currentThread().getName());
        mContext = getContext();
        //ContentProvider 创建时，初始化数据库
        mDb = new DbOpenHelper(mContext).getWritableDatabase();

        //注意，实际使用中不推荐在主线程中进行耗时的数据库操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                initProviderData();
            }
        }).start();
        return true;
    }

    private void initProviderData() {
        mDb.execSQL("delete from "+DbOpenHelper.BOOK_TABLE_NAME);
        mDb.execSQL("delete from "+DbOpenHelper.USER_TABLE_NAME);
        mDb.execSQL("insert into book values(3,'Android');");
        mDb.execSQL("insert into book values(4,'IOS');");
        mDb.execSQL("insert into book values(5,'HTML5');");
        mDb.execSQL("insert into user values(1,'Jake',1);");
        mDb.execSQL("insert into user values(2,'Jasmine',0);");

    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        //运行在子线程中
        LogUtils.i("query, current Thread: "+Thread.currentThread().getName());
        String table = getTableName(uri);
        if (table == null){
            throw new IllegalArgumentException("Unsupported URI: "+uri);
        }
        return mDb.query(table,projection,selection,selectionArgs,null,null,sortOrder,null);
    }

    /**
     * 返回一个Uri请求所对应的MIME类型
     * @param uri
     * @return
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        LogUtils.i("getType");
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        //运行在子线程中
        LogUtils.i("insert");
        String table = getTableName(uri);
        if (table == null){
            throw new IllegalArgumentException("Unsupported URI: "+uri);
        }
        mDb.insert(table,null,values);
        //通知外界当前ContentProvider中的数据已经改变
        mContext.getContentResolver().notifyChange(uri,null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        //运行在子线程中
        LogUtils.i("delete");
        String table = getTableName(uri);
        if (table == null){
            throw new IllegalArgumentException("Unsupported URI: "+uri);
        }
        int count = mDb.delete(table,selection,selectionArgs);
        if (count>0){
            mContext.getContentResolver().notifyChange(uri,null);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        //运行在子线程中
        LogUtils.i("update");
        String table = getTableName(uri);
        if (table == null){
            throw new IllegalArgumentException("Unsupported URI: "+uri);
        }
        int row = mDb.update(table,values,selection,selectionArgs);
        if (row>0){
            mContext.getContentResolver().notifyChange(uri,null);
        }
        return row;
    }


    private String getTableName(Uri uri){
        String tableName = null;
        switch (sUriMatcher.match(uri)){
            case BOOK_URI_CODE:
                tableName = DbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DbOpenHelper.USER_TABLE_NAME;
                break;
        }
        return tableName;
    }

}
