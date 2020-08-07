package com.example.ipcdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ipcdemo.entity.User;
import com.example.ipcdemo.utils.IPCByFile;
import com.example.ipcdemo.utils.MyConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        findViewById(R.id.btn_activity_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IPCByFile.persistToFile();
            }
        });

    }

}

