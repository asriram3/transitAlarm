/**
 * Created by Nimay and Kasi on 9/12/2015.
 */
package com.example.aditya.transitalarm2;

import java.io.StringReader;
import java.util.HashMap;
import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlPullParserException;
import android.util.Xml;

import android.util.Log;


public class stationInfoParser {
    public HashMap<String, double[]> parseForCoords(String in) throws XmlPullParserException{
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
//        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(new StringReader(in));
        HashMap<String, double[]> stations = new HashMap<>(); // station -> [lat,long]
        String currentTag = null;
        String currStation = null; //Used as key of stations.

        int eventType = parser.getEventType();
        Log.d("fuck", in);
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                currentTag = parser.getName();
            }
            else if (eventType == XmlPullParser.TEXT) {
                if (("name").equals(currentTag)) {
                    currStation = currentTag;
                    Log.d("stationInfoParser", currStation);
                    stations.get(currStation);
                    double[] coords = new double[2];
                    stations.put(currStation, coords);
                }
                if (("gtfs_latitude").equals(currentTag)) {
                    stations.get(currStation)[0] = Double.valueOf(parser.getText());
                    // Can insert error checking for null.
                }
                if (("gtfs_longitude").equals(currentTag)) {
                    stations.get(currStation)[1] = Double.valueOf(parser.getText());
                    // Can insert error checking for null.
                }
            }
            else if (eventType == XmlPullParser.END_TAG) {
                continue;
            }
            try {
                eventType = parser.nextToken();
//                System.out.println("START_DOCUMENT" + XmlPullParser.START_DOCUMENT);
//                System.out.println("TEXT" + XmlPullParser.TEXT);
//                System.out.println("END_TAG" + XmlPullParser.END_TAG);
//                System.out.println("START_TAG" + XmlPullParser.START_TAG);
//                System.out.println("END_DOCUMENT" + XmlPullParser.END_DOCUMENT);

            } catch(IOException i){
                stations.get(currStation)[0]=0;
                stations.get(currStation)[1]=0;
                return stations;
            }
        }
        return stations;
    }
}
