package com.x7chen.dev.fitui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

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
        final PacketParser mPacketParser = applicationContextHelper.getPacketParser();
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mPacketParser.getConnnetStatus()) {
                    int i = 3;
                    while (0 < i--) {
                        if (mPacketParser.isIdle()) {
                            mPacketParser.getAlarms();
                            return;
                        } else {
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    //Toast.makeText(AlarmActivity.this,"手环未连接",Toast.LENGTH_SHORT).show();
                }
            }

        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return super.onCreateOptionsMenu(menu);
    }

    class AlarmListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }

    }
}
