package com.example.aditya.transitalarm2;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
//put service into manifest
/**
 * Created by Kasi on 9/12/2015.
 */
public class intentService extends IntentService {



    public intentService() {
        super("intentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //temp for notifications
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("WAKE UP!");
        builder.setContentText("You are near your stop!");
        NotificationManager nf = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nf.notify(1, builder.build());
        //end temp

        System.out.println("Timer is on now!");
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Bundle stationData = intent.getExtras();
        double[] stationCoords = stationData.getDoubleArray("stationCoords");
        try {
            PendingIntent pendingIntent = PendingIntent.getService(this, 1, new Intent(this, backgroundAlarmService.class), 0);
            System.out.println("Waiting for you to reach your stop");
            locationManager.addProximityAlert(stationCoords[0], stationCoords[1], 3220, -1, pendingIntent);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
