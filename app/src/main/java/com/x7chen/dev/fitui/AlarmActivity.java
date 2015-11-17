package com.x7chen.dev.fitui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import java.io.IOException;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_setting);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_revert);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ApplicationContextHelper applicationContextHelper = (ApplicationContextHelper) getApplicationContext();
        PacketParser mPacketParser = applicationContextHelper.getPacketParser();
        mPacketParser.registerCallback(new PacketParser.CallBack() {
            @Override
            public void onSendSuccess() {

            }

            @Override
            public void onSendFailure() {

            }

            @Override
            public void onTimeOut() {

            }

            @Override
            public void onConnectStatusChanged(boolean status) {

            }

            @Override
            public void onDataReceived(byte category) {

            }

            @Override
            public void onCharacteristicNotFound() {

            }
        });
        try {
            mPacketParser.telNotify(this,"hello");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
