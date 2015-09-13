package com.example.aditya.transitalarm2;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    public static final String TAG = "MainActivity";

    public static final OkHttpClient CLIENT = new OkHttpClient();

    List<bartXMLParser.Entry> stationInfo = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView lv = (ListView) findViewById(R.id.stationList);

        List<String> myArrayList = new ArrayList<>();
        myArrayList.add("Some random places");
        myArrayList.add("Legit subway station name");
        myArrayList.add("I'm not making this up");
        myArrayList.add("The one next to it");
        myArrayList.add("this is the last one");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                R.layout.list_item_station,
                myArrayList
        );

        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = lv.getItemAtPosition(position);
                String str = o.toString();
                //Toast.makeText(getBaseContext(), str, Toast.LENGTH_SHORT).show();

                boolean[] coords = new boolean[2];
                //coords[0] = 0;
                //coords[1] = 0;
                Intent intent = new Intent(getBaseContext(), DistanceActivity.class);
                intent.putExtra("stationName", str);
                //intent.putExtra("stationCoords", []);
            }
        });

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
                Log.d(TAG, "Got response: " + response.body().string());
                bartXMLParser obj = new bartXMLParser();
                System.out.println("OK!");
//                HashMap<String, double[]> stationInfo = null;
                try {
                    stationInfo = obj.parse(response.body().byteStream());
                    System.out.println("OK");
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }

                bartXMLParser.Entry ent = null;
//                System.out.println(stationInfo.toString());
                for(int i =0; i< stationInfo.size(); i++){
                    ent = stationInfo.get(i);
                    System.out.println(ent.title);
                }
            }
        });
    }





}

