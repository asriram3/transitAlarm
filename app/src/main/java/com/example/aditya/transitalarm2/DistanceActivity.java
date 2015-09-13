package com.example.aditya.transitalarm2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class DistanceActivity extends Activity{

    String stationname;
    double[] stationCoords;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //collecting extra data from intent
        Bundle stationData = getIntent().getExtras();
        stationname = stationData.getString("stationName");
        stationCoords = stationData.getDoubleArray("stationCoords");
        setContentView(R.layout.activity_distance);
        //create text view
        TextView stationNameView = new TextView(this);
        stationNameView.setTextSize(40);
        stationNameView.setText(stationname);

        Button setAlarm = (Button) findViewById(R.id.setAlarmButton);
        //setAlarm.setText("Set Alarm");
        setAlarm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), intentService.class);
                i.putExtra("stationCoords", stationCoords);
                System.out.println(stationCoords[0]);
//                try {
                startService(i);

                System.out.println("Service started!");
//                }catch (Exception e){
//                    System.out.println(e);
//                }
            }
        });
        //insert button
    }


    public double getDistance(double[] stationCoordinates, double[] currentCoordinates){
        return Math.sqrt((Math.pow(stationCoordinates[0],2)-(Math.pow(currentCoordinates[0],2))) + (Math.pow(stationCoordinates[1],2)-(Math.pow(currentCoordinates[1],2))));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_distance, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
