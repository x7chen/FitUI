package com.x7chen.dev.fitui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    int TargetSteps;
    ApplicationContextHelper applicationContextHelper;
    NavigationView navigationView;
    private PacketParser mPacketParser;
    PacketParser.CallBack mPacketParserCallBack = new PacketParser.CallBack() {
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
            StringBuilder stringBuilder;
            switch (category) {
                case PacketParser.RECEIVED_DAILY_DATA:
                    if (mPacketParser != null) {
                        final PacketParser.DailyData dailyData = mPacketParser.getDailyDataList();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                UpdateSportProgress(dailyData.Steps);
                            }
                        });
                    }
                    break;
                case PacketParser.RECEIVED_SPORT_DATA:
                    List<PacketParser.SportData> sportDataList;
                    List<PacketParser.SleepData> sleepDataList;

                    if (mPacketParser != null) {
                        sportDataList = mPacketParser.getSportDataList();
                        int[] steps = new int[96];
                        for (PacketParser.SportData sportData : sportDataList) {
                            int index = sportData.Hour * 4 + sportData.Minute / 15;
                            steps[index] = sportData.Steps;
                        }
                        final int[] data = steps;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                UpdateChart(data);
                            }
                        });

                        stringBuilder = new StringBuilder();
                        if (sportDataList.size() != 0) {
                            stringBuilder.append("[SportData]\n");
                            for (PacketParser.SportData sportData : sportDataList) {
                                stringBuilder.append(sportData.Year + "." + sportData.Month + "." + sportData.Day + "  ");
                                stringBuilder.append(sportData.Hour + ":" + sportData.Minute + "\n");
                                stringBuilder.append("Steps:" + sportData.Steps + "  Distance:" + sportData.Distance + "  Calories:" + sportData.Calory + "\n");
                            }
                            sportDataList.clear();
                            stringBuilder.append("\n\n");
                            PacketParser.writeLog(stringBuilder.toString());
                        }
                        sleepDataList = mPacketParser.getSleepDataList();
                        stringBuilder = new StringBuilder();
                        if (sleepDataList.size() != 0) {
                            stringBuilder.append("[SleepData]\n");
                            for (PacketParser.SleepData sleepData : sleepDataList) {
                                stringBuilder.append(sleepData.Year + "." + sleepData.Month + "." + sleepData.Day + "  ");
                                stringBuilder.append(sleepData.Hour + ":" + sleepData.Minute + "  ");
                                stringBuilder.append("Mode:" + sleepData.Mode + "\n");
                            }
                            sleepDataList.clear();
                            stringBuilder.append("\n\n");
                            PacketParser.writeLog(stringBuilder.toString());
                        }
                    }
                    break;
                case PacketParser.RECEIVED_ALARM:
                    final AlarmActivity alarmActivity = applicationContextHelper.getAlarmActivity();
                    if (alarmActivity == null) {
                        break;
                    }
                    final ArrayList<PacketParser.Alarm> alarms = mPacketParser.getAlarmsList();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (PacketParser.Alarm alarm : alarms) {
                                alarmActivity.mAlarmListAdapter.addAlarm(alarm);
                            }
                            alarmActivity.mAlarmListAdapter.notifyDataSetChanged();
                        }
                    });
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onCharacteristicNotFound() {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPacketParser = new PacketParser(getApplicationContext());
        mPacketParser.registerCallback(mPacketParserCallBack);
        applicationContextHelper = (ApplicationContextHelper) getApplicationContext();
        applicationContextHelper.setPacketParser(mPacketParser);
        applicationContextHelper.setMainActivity(this);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        startService(new Intent(this, NotificationCollectorService.class));

        VerticalProgressBar progressBar = (VerticalProgressBar) findViewById(R.id.progressBar);
        progressBar.setProgress(66);
        progressBar.setIndeterminate(false);
        int[] dat = new int[96];
        UpdateChart(dat);

        BroadcastReceiverMgr mBroadcastReceiver = new BroadcastReceiverMgr();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BroadcastReceiverMgr.B_PHONE_STATE);
        intentFilter.setPriority(Integer.MAX_VALUE);
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    private void UpdateChart(int[] data) {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.ChartLayout);
        View mBarChart = new BarChart(this).UpdateBarChar(data);
        layout.removeAllViews();
        layout.addView(mBarChart);
    }

    private void UpdateSportProgress(int steps) {
        TextView textView = (TextView) findViewById(R.id.daily_steps);
        textView.setText(steps + "步");
        TextView textView_persentage = (TextView) findViewById(R.id.progress_percentage);
        textView_persentage.setText((steps / 10) + "%");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_refresh) {
            mPacketParser.getDailyData();
//            mPacketParser.mock();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    TargetEditDialog.CallBacks targetCallBacks = new TargetEditDialog.CallBacks() {
        @Override
        public void updateTarget(int target) {
            TargetSteps = target;
            MenuItem item = navigationView.getMenu().findItem(R.id.nav_target);
            item.setTitle(item.getTitle().subSequence(0, 2) + "          " + TargetSteps);
            navigationView.invalidate();
            mPacketParser.setTarget(target);
        }
    };

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_user_profile) {
            Intent intent = new Intent(this, UserProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_bond) {
            Intent intent = new Intent(this, BondActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_alarm) {
            Intent intent = new Intent(this, AlarmActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_sit_long) {
            Intent intent = new Intent(this, LongSitActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_target) {
            showTargetEdit();
        } else if (id == R.id.nav_hour_format) {
            item.setChecked(!(item.isChecked()));
            if (item.isChecked()) {
                mPacketParser.setHourFormat(1);
            } else {
                mPacketParser.setHourFormat(0);
            }
            return true;
        } else if (id == R.id.nav_hand) {
        } else if (id == R.id.nav_phone_notify) {
            try {
                mPacketParser.telNotify("s");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } else if (id == R.id.nav_message_notify) {
            try {
                mPacketParser.infoNotify("i");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } else if (id == R.id.nav_upgrade) {
            Intent intent = new Intent(this, UpdateActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            showAbout();
        }

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void showAbout() {
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(this);
        aBuilder.setTitle("About");
        aBuilder.setIcon(R.drawable.ic_all_inclusive_black_36dp);
        aBuilder.setMessage("Version:1.0");
        aBuilder.create().show();
    }

    void showTargetEdit() {
        TargetEditDialog showTargetEdit = new TargetEditDialog(this);
        showTargetEdit.setCallBacks(targetCallBacks);
        showTargetEdit.setTargetSteps(TargetSteps);
        showTargetEdit.show();
    }

    class BroadcastReceiverMgr extends BroadcastReceiver {
        public final static String B_PHONE_STATE = TelephonyManager.ACTION_PHONE_STATE_CHANGED;

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            //呼入电话
            if (action.equals(B_PHONE_STATE)) {
                doReceivePhone(context, intent);
            }
        }

        /**
         * 处理电话广播.
         *
         * @param context
         * @param intent
         */
        public void doReceivePhone(Context context, Intent intent) {
            String phoneNumber = intent.getStringExtra(
                    TelephonyManager.EXTRA_INCOMING_NUMBER);
            TelephonyManager telephony =
                    (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            int state = telephony.getCallState();
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    try {
                        mPacketParser.telNotify("s");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
            }
        }
    }
}
