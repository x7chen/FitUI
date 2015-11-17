package com.x7chen.dev.fitui;

import android.app.Application;

/**
 * Created by Sean on 2015/11/17.
 */
public class ApplicationContextHelper extends Application {
    PacketParser packetParser;

    public PacketParser getPacketParser() {
        return packetParser;
    }

    public void setPacketParser(PacketParser packetParser) {
        this.packetParser = packetParser;
    }
}
