package com.example.aditya.transitalarm2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;


/**
 * Created by Kasi on 9/12/2015.
 */
public class backgroundAlarmService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
