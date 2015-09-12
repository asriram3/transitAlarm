package com.example.aditya.transitalarm2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ArrayAdapter<String> adapter;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            FetchStationTask fetchStationTask = new FetchStationTask();
            fetchStationTask.execute("");
            return true;
        }
            return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_main, container, false);

        String[] stationNames = {"Fremont", "Fruitvale", "Powell", "Daly City"};

        List<String> Names = new ArrayList<String>(Arrays.asList(stationNames));

        adapter= new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item_station,
                R.id.list_item_station_textview,
                Names);

        ListView stations = (ListView) rootView.findViewById(R.id.stationList);
        stations.setAdapter(adapter);

        return rootView;
    }

    public class FetchStationTask  extends AsyncTask<String,String, List<String>> {


        private static final String API_KEY = "MW9S-E7SL-26DU-VV8V";
        private static final String BASE_API_URL = "http://api.bart.gov/api";
        private static final String STATIONS_URL = BASE_API_URL + "/stn.aspx?cmd=stns";

        @Override
        protected List<String> doInBackground(String... params){

            //HttpClient httpClient = new DefaultHttpClient();
            //HttpGet request = new HttpGet(url + "&key=" + API_KEY);
            //HttpResponse response = httpClient.execute(request);
            //InputStream inputStream = response.getEntity().getContent();

            String myurl = STATIONS_URL + "&key=" + API_KEY;
            HttpURLConnection conn = null;
            BufferedReader br = null;
            InputStream is = null;
            List<String> res=null;
            try{
                URL url = new URL(myurl);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                is = conn.getInputStream();
                res= parse(is);

            }catch (IOException e){
                Log.e("FetchStationTask", e.getMessage());
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }

            return res;
        }


        public final String ns = null;

        public List<String> parse(InputStream in) throws XmlPullParserException, IOException {
            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);
                parser.nextTag();
                return readFeed(parser);
            } finally {
                in.close();
            }
        }

        private List<String> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
            List<String> entries = new ArrayList<String>();

            parser.require(XmlPullParser.START_TAG, ns, "feed");
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                // Starts by looking for the entry tag
                if (name.equals("entry")) {
                    entries.add(readEntry(parser));
                } else {
                    skip(parser);
                }
            }
            return entries;
        }


        private String readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
            parser.require(XmlPullParser.START_TAG, ns, "station");
            String title = null;
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                if (name.equals("name")) {
                    title = readTitle(parser);
                } else {
                    skip(parser);
                }
            }
            return title;
        }

        private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "title");
            String title = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, "title");
            return title;
        }
        private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
            String result = "";
            if (parser.next() == XmlPullParser.TEXT) {
                result = parser.getText();
                parser.nextTag();
            }
            Log.v("FetchStationTask", result);
            return result;
        }




        private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                throw new IllegalStateException();
            }
            int depth = 1;
            while (depth != 0) {
                switch (parser.next()) {
                    case XmlPullParser.END_TAG:
                        depth--;
                        break;
                    case XmlPullParser.START_TAG:
                        depth++;
                        break;
                }
            }
        }


        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            if(strings != null){
                adapter.clear();
                for(String stn : strings){
                    adapter.add(stn);
                }
            }
        }
    }
}
