/**
 * Created by Kasi on 9/12/2015.
 */
package com.example.aditya.transitalarm2;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

public class stationInfoParser {
    XmlPullParser parser;
    public stationInfoParser(InputStream inputStream){
        parser = createParser(inputStream);
    }
    public double[] parseForCoords(String stationName) throws XmlPullParserException{
        double[] coords = new double[2];
        String currentTag = null;

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                currentTag = parser.getName();
            }
            else if (eventType == XmlPullParser.TEXT) {
                if ("gtfs_latitude".equals(currentTag)) {
                    coords[0] = Double.valueOf(parser.getText());
                }
                if ("gtfs_longitude".equals()) {
                    coords[1] = Double.valueOf(parser.getText());
                }
            }
            else if (eventType == XmlPullParser.END_TAG) {
            }
            try {
                eventType = parser.next();
            } catch(IOException i){
                coords[0]=0;
                coords[1]=0;
                return coords;
            }
        }
        return coords;
    }
}
