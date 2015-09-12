package com.example.aditya.transitalarm2;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class DistanceActivity extends ActionBarActivity {

    double[] coords;
    String stationname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get intent
        Intent intent = getIntent();
        //get station data from intent
        stationname = intent.getStringExtra();
        //create text view
        TextView stationNameView = new TextView(this);
        stationNameView.setTextSize(40);
        stationNameView.setText(stationname);
        //create content view
        setContentView(stationNameView);
    }

    public double[] getCoords(){
        stationInfoParser x = new stationInfoParser();
        return coords;
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
