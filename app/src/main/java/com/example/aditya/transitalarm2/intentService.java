package com.example.aditya.transitalarm2;

import android.app.IntentService;
import android.content.Intent;
//put service into manifest
/**
 * Created by Kasi on 9/12/2015.
 */
public class intentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public intentService() {
        super("intentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
