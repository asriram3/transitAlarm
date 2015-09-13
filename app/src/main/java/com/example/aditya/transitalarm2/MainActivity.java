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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class MainActivity extends Activity {
    public static final String TAG = "MainActivity";

    public static final OkHttpClient CLIENT = new OkHttpClient();

    String data = "Bay Fair,37.69718,-122.126871:Pleasant Hill/Contra Costa Centre,37.92840,-122.056013:San Leandro,37.7226192,-122.1613112:Glen Park,37.73292,-122.434092:North Concord/Martinez,38.00327,-122.024597:Richmond,37.93688,-122.353165:16th St. Mission,37.76506,-122.419694:19th St. Oakland,37.8078,-122.269029:West Dublin/Pleasanton,37.69975,-121.928099:Hayward,37.67039,-122.087967:12th St. Oakland City Center,37.80366,-122.271604:Ashby,37.85302,-122.26978:MacArthur,37.82841,-122.267227:Castro Valley,37.69075,-122.075567:Fremont,37.55735,-121.9764:Embarcadero,37.79297,-122.396742:Walnut Creek,37.90562,-122.067423:West Oakland,37.8046747,-122.2945822:Balboa Park,37.7219808,-122.4474142:San Bruno,37.63775,-122.416038:Coliseum/Oakland Airport,37.75400,-122.197273:Millbrae,37.59978,-122.38666:Colma,37.68463,-122.466233:Downtown Berkeley,37.86986,-122.268045:South San Francisco,37.66417,-122.444116:El Cerrito del Norte,37.92565,-122.317269:Dublin/Pleasanton,37.70169,-121.900367:Lafayette,37.89339,-122.123801:Rockridge,37.84460,-122.251793:Oakland Int'l Airport,37.7129717,-122.21244024:Fruitvale,37.77496,-122.224274:South Hayward,37.6347995,-122.0575506:El Cerrito Plaza,37.903058,-122.2992715:Daly City,37.7061205,-122.4690807:24th St. Mission,37.75225,-122.418466:Concord,37.97373,-122.029095:Montgomery St.,37.78925,-122.401407:Union City,37.59120,-122.017867:Civic Center/UN Plaza,37.77952,-122.413756:San Francisco Int'l Airport,37.61603,-122.392612:Orinda,37.8783608,-122.1837911:Lake Merritt,37.79748,-122.265609:Powell St.,37.78499,-122.406857:North Berkeley,37.8740,-122.283451:Pittsburg/Bay Point,38.01891,-121.945154:";

    String[][] data = new String[45][3];


    //    HashMap<String, double[]> stationInfo = null;
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

                double[] coords = new double[2];
                coords[0] = 37.000;
                coords[1] = -122.123;
                Intent intent = new Intent(getBaseContext(), DistanceActivity.class);
                intent.putExtra("stationName", str);
                intent.putExtra("stationCoords", coords);
                startActivity(intent);
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
                //Log.d(TAG, "Got response: " + response.body().string());
                stationInfoParser obj = new stationInfoParser();
                HashMap<String, double[]> stationInfo = null;
                try {
                    stationInfo = obj.parseForCoords(response.body().string());
                    System.out.println(stationInfo.keySet());
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }


            }
        });
    }





}

