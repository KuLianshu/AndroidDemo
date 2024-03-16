package com.example.getdatafromcontentproviderdemo;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class DBHelper {

    public static void queryData(Context context) {
        Uri uri=Uri.parse("content://com.example.contentproviderdemo.provider/language");
        Cursor cursor=context.getContentResolver().query(uri,null,null,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                do {
                    int languageIndex = cursor.getColumnIndex("language");
                    String language=cursor.getString(languageIndex);
                    Log.i("WLY","language1:"+language);
                }while (cursor.moveToNext());
                cursor.close();
            }
        }

    }

}
