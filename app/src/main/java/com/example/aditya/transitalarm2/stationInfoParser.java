/**
 * Created by Nimay and Kasi on 9/12/2015.
 */
package com.example.aditya.transitalarm2;

import java.util.HashMap;
import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import android.util.Xml;

import android.util.Log;


public class stationInfoParser {
    public HashMap<String, double[]> parseForCoords(InputStream in) throws XmlPullParserException{
        XmlPullParser parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(in, null);
        HashMap<String, double[]> stations = new HashMap<>(); // station -> [lat,long]
        String currentTag = null;
        String currStation = null; //Used as key of stations.

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                currentTag = parser.getName();
            }
            else if (eventType == XmlPullParser.TEXT) {
                if ("name".equals(currentTag)) {
                    currStation = currentTag;
                    Log.d("stationInfoParser", currStation);
                    stations.get(currStation);
                    double[] coords = new double[2];
                    stations.put(currStation, coords);
                }
                if ("gtfs_latitude".equals(currentTag)) {
                    stations.get(currStation)[0] = Double.valueOf(parser.getText());
                    // Can insert error checking for null.
                }
                if ("gtfs_longitude".equals(currentTag)) {
                    stations.get(currStation)[1] = Double.valueOf(parser.getText());
                    // Can insert error checking for null.
                }
            }
            else if (eventType == XmlPullParser.END_TAG) {
                continue;
            }
            try {
                eventType = parser.next();
            } catch(IOException i){
                stations.get(currStation)[0]=0;
                stations.get(currStation)[1]=0;
                return stations;
            }
        }
        return stations;
    }
}
