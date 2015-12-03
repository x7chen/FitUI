package com.x7chen.dev.fitui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;

import com.x7chen.dev.fitui.MyView.WeekPicker;

public class EditAlarmActivity extends AppCompatActivity {

    TimePicker mTimePicker;
    WeekPicker mWeekPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_setting);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);
        mTimePicker = (TimePicker) findViewById(R.id.timePicker);
        mWeekPicker = (WeekPicker) findViewById(R.id.weekPicker);
        Intent intent = getIntent();
        int minute = intent.getIntExtra("minute", 0);
        int hour = intent.getIntExtra("hour", 0);
        int repeat = intent.getIntExtra("repeat", 0);
        mTimePicker.setCurrentHour(hour);
        mTimePicker.setCurrentMinute(minute);
        boolean[] weeks = new boolean[7];
        for (int i = 0; i < 7; i++) {
            if ((repeat & ((byte) (0x01 << i))) != 0) {
                weeks[i] = true;
            }
        }
        mWeekPicker.setWeeks(weeks);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditAlarmActivity.this.setResult(0xFF, EditAlarmActivity.this.getIntent());
                EditAlarmActivity.this.finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_complete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_complete) {
            int repeat = 0;
            boolean[] weeks = mWeekPicker.getWeeks();
            for (int i = 0; i < 7; i++) {
                if (weeks[i]) {
                    repeat |= (1 << i);
                }
            }
            Log.i(NusManager.TAG, "repeat:" + repeat);
            int hour = mTimePicker.getCurrentHour();
            int minute = mTimePicker.getCurrentMinute();
            Intent intent = new Intent();
            intent.putExtra("repeat", repeat);
            intent.putExtra("minute", minute);
            intent.putExtra("hour", hour);
            setResult(0x00, intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
