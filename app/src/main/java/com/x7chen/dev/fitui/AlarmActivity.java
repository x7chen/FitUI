package com.x7chen.dev.fitui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

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
        Log.i(NusManager.TAG, "AlarmActivity onCreate");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPacketParser.setAlarmList(mAlarmListAdapter.getAlarmList());
                finish();
            }
        });
        ApplicationContextHelper applicationContextHelper = (ApplicationContextHelper) getApplicationContext();
        mPacketParser = applicationContextHelper.getPacketParser();
        applicationContextHelper.setAlarmActivity(this);
        ListView alarmlistview = (ListView) findViewById(R.id.alarm_listView);
        mAlarmListAdapter = new AlarmListAdapter(this);
        alarmlistview.setAdapter(mAlarmListAdapter);
        alarmlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PacketParser.Alarm alarm = (PacketParser.Alarm)mAlarmListAdapter.getItem(position);
                Intent intent = new Intent(AlarmActivity.this, EditAlarmActivity.class);
                intent.putExtra("hour",alarm.Hour);
                intent.putExtra("minute",alarm.Minute);
                intent.putExtra("repeat",alarm.Repeat);
                startActivityForResult(intent, position);
            }
        });
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
//                    Toast.makeText(AlarmActivity.this, "手环未连接", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.memu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(NusManager.TAG, "AlarmActivity onActivityResult" + requestCode + "#" + resultCode);
        if(resultCode == 0xFF){
            return;
        }
        if (requestCode == 0x09) {
            PacketParser.Alarm alarm = new PacketParser.Alarm();
            alarm.Minute = data.getIntExtra("minute", 0);
            alarm.Hour = data.getIntExtra("hour", 0);
            alarm.Repeat = data.getIntExtra("repeat", 0);
            alarm.ID = mAlarmListAdapter.getCount();
            Calendar calendar = Calendar.getInstance();
            alarm.Year = calendar.get(Calendar.YEAR) - 2000;
            alarm.Month = calendar.get(Calendar.MONTH);
            alarm.Day = calendar.get(Calendar.DAY_OF_MONTH);
            mAlarmListAdapter.addAlarm(alarm);
            mAlarmListAdapter.notifyDataSetChanged();
        }
        if (requestCode < 0x09) {
            PacketParser.Alarm alarm = new PacketParser.Alarm();
            alarm.Minute = data.getIntExtra("minute", 0);
            alarm.Hour = data.getIntExtra("hour", 0);
            alarm.Repeat = data.getIntExtra("repeat", 0);
            alarm.ID = mAlarmListAdapter.getCount();
            Calendar calendar = Calendar.getInstance();
            alarm.Year = calendar.get(Calendar.YEAR) - 2000;
            alarm.Month = calendar.get(Calendar.MONTH);
            alarm.Day = calendar.get(Calendar.DAY_OF_MONTH);
            mAlarmListAdapter.setAlarm(requestCode, alarm);
            mAlarmListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_add) {
            Intent intent = new Intent(AlarmActivity.this, EditAlarmActivity.class);
            startActivityForResult(intent, 0x09);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class AlarmListAdapter extends BaseAdapter {

        private ArrayList<PacketParser.Alarm> mAlarms;
        private LayoutInflater mInflator;

        public AlarmListAdapter(Context context) {
            super();
            mAlarms = new ArrayList<>();
            mInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public ArrayList<PacketParser.Alarm> getAlarmList() {
            return mAlarms;
        }
        public void setAlarm(int i,PacketParser.Alarm alarm){
            mAlarms.set(i,alarm);
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
                if ((alarm.Repeat & ((byte) (0x01 << r))) != 0) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(NusManager.TAG, "AlarmActivity onDestroy");
    }

    static class ViewHolder {
        TextView alarmTime;
        TextView repeat;
    }
}
