package com.x7chen.dev.fitui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_setting);
        toolbar.setTitle("闹钟");
        setSupportActionBar(toolbar);

    }
}
