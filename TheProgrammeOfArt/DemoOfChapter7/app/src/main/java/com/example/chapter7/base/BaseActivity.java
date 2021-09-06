package com.example.chapter7.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.chapter7.R;

public class BaseActivity extends Activity {

    public void startActivity(Class clazz, Bundle bundle){
        Intent intent = new Intent();
        intent.setClass(this,clazz);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }


    public void startActivity(Class clazz){
        Intent intent = new Intent();
        intent.setClass(this,clazz);
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }


    public void startActivityForResult(Class clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this,clazz);
        intent.putExtras(bundle);
        startActivityForResult(intent,requestCode);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }
}
