package com.example.aditya.transitalarm2;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    public static final OkHttpClient CLIENT = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        Request request = new Request.Builder()
                .get()
                .url("http://api.bart.gov/api/stn.aspx?cmd=stns&key=MW9S-E7SL-26DU-VV8V")
                .build();

    Call call = CLIENT.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e(TAG, "Request failed", e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.d(TAG, "Got response: " + response.body().byteStream());
                stationInfoParser obj = new stationInfoParser();
                HashMap<String, double[]> stationInfo = null;
                try {
                    stationInfo = obj.parseForCoords(response.body().byteStream());
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }

                Iterator it = stationInfo.entrySet().iterator();
                while (it.hasNext()) {
                    HashMap.Entry pair = (HashMap.Entry)it.next();
                    System.out.println(pair.getKey() + " = " + pair.getValue());
                    it.remove(); // avoids a ConcurrentModificationException
                }
            }
        });

}}

