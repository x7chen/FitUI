package com.x7chen.dev.fitui;

import android.app.Application;

/**
 * Created by Sean on 2015/11/17.
 */
public class ApplicationContextHelper extends Application {

    MainActivity mainActivity;
    AlarmActivity alarmActivity;
    BondActivity bondActivity;
    PacketParser packetParser;


    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public AlarmActivity getAlarmActivity() {
        return alarmActivity;
    }

    public void setAlarmActivity(AlarmActivity alarmActivity) {
        this.alarmActivity = alarmActivity;
    }

    public BondActivity getBondActivity() {
        return bondActivity;
    }

    public void setBondActivity(BondActivity bondActivity) {
        this.bondActivity = bondActivity;
    }

    public PacketParser getPacketParser() {
        return packetParser;
    }

    public void setPacketParser(PacketParser packetParser) {
        this.packetParser = packetParser;
    }
}
