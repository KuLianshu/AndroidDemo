package com.example.module_girls;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GirlsActivity extends AppCompatActivity {

    @BindView(R2.id.tv_girls_activity_main)
    TextView tvGirlsActivityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.girls_activity_main);
        ButterKnife.bind(this);

    }


}
