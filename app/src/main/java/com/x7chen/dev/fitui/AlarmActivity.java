package com.x7chen.dev.fitui;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class AlarmActivity extends AppCompatActivity {
    AlarmListAdapter mAlarmListAdapter;
    PacketParser mPacketParser;

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
        mPacketParser = applicationContextHelper.getPacketParser();
        applicationContextHelper.setAlarmActivity(this);
        ListView alarmlistview = (ListView) findViewById(R.id.alarm_listView);
        mAlarmListAdapter = new AlarmListAdapter(this);
        alarmlistview.setAdapter(mAlarmListAdapter);
        mPacketParser.getAlarms();
//        final PacketParser nPacktParse = mPacketParser;
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if (nPacktParse.getConnnetStatus()) {
//                    int i = 3;
//                    while (0 < i--) {
//                        if (nPacktParse.isIdle()) {
//                            nPacktParse.getAlarms();
//                            return;
//                        } else {
//                            try {
//                                Thread.sleep(50);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                } else {
//                    //Toast.makeText(AlarmActivity.this,"手环未连接",Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return super.onCreateOptionsMenu(menu);
    }

    class AlarmListAdapter extends BaseAdapter {

        private ArrayList<PacketParser.Alarm> mAlarms;
        private LayoutInflater mInflator;

        public AlarmListAdapter(Context context) {
            super();
            mAlarms = new ArrayList<>();
            mInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void addAlarm(PacketParser.Alarm alarm) {
            mAlarms.add(alarm);
        }

        @Override
        public int getCount() {
            return mAlarms.size();
        }

        @Override
        public Object getItem(int i) {
            return mAlarms.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            // General ListView optimization code.
            if (view == null) {
                view = mInflator.inflate(R.layout.listitem_alarm, null);
                viewHolder = new ViewHolder();
                viewHolder.alarmTime = (TextView) view.findViewById(R.id.alarm_time);
                viewHolder.repeat = (TextView) view.findViewById(R.id.alarm_repeat);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            PacketParser.Alarm alarm = mAlarms.get(i);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.format("%02d", alarm.Hour));
            stringBuilder.append(":");
            stringBuilder.append(String.format("%02d", alarm.Minute));
            viewHolder.alarmTime.setText(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            char[] week = {'一', '二', '三', '四', '五', '六', '日'};
            stringBuilder.append("重复:  ");
            for (int r = 0; r < 7; r++) {
                if ((alarm.Repeat & ((byte) (0x01 << i))) != 0) {
                    stringBuilder.append(week[r]);
                } else {
                    stringBuilder.append("  ");
                }
                stringBuilder.append("  ");
            }
            viewHolder.repeat.setText(stringBuilder.toString());
            return view;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }
    }

    static class ViewHolder {
        TextView alarmTime;
        TextView repeat;
    }
}
