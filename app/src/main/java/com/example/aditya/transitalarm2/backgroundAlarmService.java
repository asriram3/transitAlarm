package com.example.aditya.transitalarm2;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;


/**
 * Created by Kasi on 9/12/2015.
 */
public class backgroundAlarmService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        System.out.println("Timer is on now!");
        builder.setContentTitle("WAKE UP!");
        builder.setContentText("You are near your stop!");
        NotificationManager nf = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nf.notify(1, builder.build());
        return null;
    }
}