package com.x7chen.dev.fitui;

import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class NotificationCollectorService extends NotificationListenerService {
    public NotificationCollectorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        Log.i("zpf", "open"+"-----"+sbn.toString());
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("zpf", "shut" + "-----" + sbn.toString());

    }
}
