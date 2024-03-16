package com.example.contentproviderdemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contentproviderdemo.entity.LanguageData;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    
    private Button add_data;

    private Button query_data;

    private Button update_data;

    private Button delete_data;

    private String newId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
        initEvent();

    }

    private void initEvent() {
        add_data.setOnClickListener(this);
        query_data.setOnClickListener(this);
        update_data.setOnClickListener(this);
        delete_data.setOnClickListener(this);
        findViewById(R.id.update_language).setOnClickListener(this);
    }

    private void initView() {
        add_data=(Button)findViewById(R.id.add_data);

        query_data=(Button)findViewById(R.id.query_data);

        update_data=(Button)findViewById(R.id.update_data);

        delete_data=(Button)findViewById(R.id.delete_data);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.add_data:
//                Uri uri= Uri.parse("content://com.example.contentproviderdemo.provider/language");
//                ContentValues values=new ContentValues();
//                values.put("language","en");
//                Uri newUri=getContentResolver().insert(uri,values);
//                newId=newUri.getPathSegments().get(1);
//                values.clear();
//                break;

            case R.id.query_data:
                Uri uri=Uri.parse("content://com.example.contentproviderdemo.provider/language");
                Cursor cursor=getContentResolver().query(uri,null,null,null,null);
                if(cursor!=null){
                    if(cursor.moveToFirst()){
                        do {
                            int languageIndex = cursor.getColumnIndex("language");
                            String language=cursor.getString(languageIndex);
                            Log.i("WLY","name:"+language);
                        }while (cursor.moveToNext());
                        cursor.close();
                    }
                }
                break;

//            case R.id.update_data:
//                uri=Uri.parse("content://com.example.contentproviderdemo.provider/language/"+newId);
//                values=new ContentValues();
//                values.put("language","zh");
//                int updateId=getContentResolver().update(uri,values,null,null);
//                Log.i("WLY","updateId:"+updateId);
//                break;
//
//            case R.id.delete_data:
//                uri=Uri.parse("content://com.example.contentproviderdemo.provider/language/"+"1");
//                int deleteRow=getContentResolver().delete(uri,null,null);
//                Log.i("WLY","deleteRow："+deleteRow);
//                break;

            case R.id.update_language:
                uri=Uri.parse("content://com.example.contentproviderdemo.provider/language");
                Cursor c=getContentResolver().query(uri,null,null,null,null);
                List<LanguageData> lanList = new ArrayList<>();
                if(c!=null){
                    if(c.moveToFirst()){
                        do {
                            int idIndex = c.getColumnIndex("id");
                            int id = c.getInt(idIndex);
                            int languageIndex = c.getColumnIndex("language");
                            String language=c.getString(languageIndex);
                            Log.i("WLY","language:"+language+", id: "+id);
                            lanList.add(new LanguageData(id,language));
                        }while (c.moveToNext());
                        c.close();
                    }
                }
                if (lanList.size()==1){
                    uri=Uri.parse("content://com.example.contentproviderdemo.provider/language/"+lanList.get(0).getId());
                    ContentValues values=new ContentValues();
                    values.put("language","zh");
                    int updateId1=getContentResolver().update(uri,values,null,null);
                    Log.i("WLY","updateId:"+updateId1);
                }else {
                    Uri uri1= Uri.parse("content://com.example.contentproviderdemo.provider/language");
                    ContentValues values1=new ContentValues();
                    values1.put("language","en");
                    Uri newUri1=getContentResolver().insert(uri1,values1);
                    newId=newUri1.getPathSegments().get(1);
                    values1.clear();
                }

                break;
        }
    }



}
