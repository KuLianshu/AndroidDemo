package com.example.chapter7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

public class LauncherAnimationDemoActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher_animation_demo);

        Button button = findViewById(R.id.start_activity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LauncherAnimationDemoActivity.this,ShowAnimationActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_enter_anim,R.anim.anim_exit_anim);

            }
        });

    }
}
