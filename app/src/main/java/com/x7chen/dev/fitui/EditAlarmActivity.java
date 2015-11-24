package com.x7chen.dev.fitui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TimePicker;

public class EditAlarmActivity extends AppCompatActivity {

    TimePicker mTimePicker;
    CheckBox checkBoxMon;
    CheckBox checkBoxTue;
    CheckBox checkBoxWed;
    CheckBox checkBoxThur;
    CheckBox checkBoxFri;
    CheckBox checkBoxSat;
    CheckBox checkBoxSun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_setting);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_revert);
        setSupportActionBar(toolbar);
        mTimePicker = (TimePicker) findViewById(R.id.timePicker);
        checkBoxMon = (CheckBox) findViewById(R.id.checkBox);
        checkBoxTue = (CheckBox) findViewById(R.id.checkBox2);
        checkBoxWed = (CheckBox) findViewById(R.id.checkBox3);
        checkBoxThur = (CheckBox) findViewById(R.id.checkBox4);
        checkBoxFri = (CheckBox) findViewById(R.id.checkBox5);
        checkBoxSat = (CheckBox) findViewById(R.id.checkBox6);
        checkBoxSun = (CheckBox) findViewById(R.id.checkBox7);
        Intent intent = getIntent();
        int minute = intent.getIntExtra("minute", 0);
        int hour = intent.getIntExtra("hour", 0);
        int repeat = intent.getIntExtra("repeat", 0);
        mTimePicker.setCurrentHour(hour);
        mTimePicker.setCurrentMinute(minute);

        if ((repeat & ((byte) (0x01 << 0))) != 0) {
            checkBoxMon.setChecked(true);
        }
        if ((repeat & ((byte) (0x01 << 1))) != 0) {
            checkBoxTue.setChecked(true);
        }
        if ((repeat & ((byte) (0x01 << 2))) != 0) {
            checkBoxWed.setChecked(true);
        }
        if ((repeat & ((byte) (0x01 << 3))) != 0) {
            checkBoxThur.setChecked(true);
        }
        if ((repeat & ((byte) (0x01 << 4))) != 0) {
            checkBoxFri.setChecked(true);
        }
        if ((repeat & ((byte) (0x01 << 5))) != 0) {
            checkBoxSat.setChecked(true);
        }
        if ((repeat & ((byte) (0x01 << 6))) != 0) {
            checkBoxSun.setChecked(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_complete) {
            int repeat = 0;
            if (checkBoxMon.isChecked()) {
                repeat |= (1);
            }
            if (checkBoxTue.isChecked()) {
                repeat |= (1 << 1);
            }
            if (checkBoxWed.isChecked()) {
                repeat |= (1 << 2);
            }
            if (checkBoxThur.isChecked()) {
                repeat |= (1 << 3);
            }
            if (checkBoxFri.isChecked()) {
                repeat |= (1 << 4);
            }
            if (checkBoxSat.isChecked()) {
                repeat |= (1 << 5);
            }
            if (checkBoxSun.isChecked()) {
                repeat |= (1 << 6);
            }
            Log.i(NusManager.TAG, "repeat:" + repeat);
            int hour = mTimePicker.getCurrentHour();
            int minute = mTimePicker.getCurrentMinute();
            Intent intent = new Intent();
            intent.putExtra("repeat", repeat);
            intent.putExtra("minute", minute);
            intent.putExtra("hour", hour);
            setResult(0x09, intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
