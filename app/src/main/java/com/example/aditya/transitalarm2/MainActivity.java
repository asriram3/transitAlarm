package com.example.aditya.transitalarm2;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    }
}
//    class RequestTask extends AsyncTask<String, String, String>{
//
//        @Override
//        protected String doInBackground(String... args) {
//            URL url = new URL(urlString);
//
//            HttpClient httpclient = new DefaultHttpClient();
//            HttpResponse response;
//            String responseString = null;
//            try {
//                response = httpclient.execute(new HttpGet(args[0]));
//                StatusLine statusLine = response.getStatusLine();
//                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
//                    ByteArrayOutputStream out = new ByteArrayOutputStream();
//                    response.getEntity().writeTo(out);
//                    responseString = out.toString();
//                    out.close();
//                } else{
//                    //Closes the connection.
//                    response.getEntity().getContent().close();
//                    throw new IOException(statusLine.getReasonPhrase());
//                }
//            } catch (ClientProtocolException e) {
//                System.out.println(e);
//            } catch (IOException e) {
//                System.out.println(e);
//            }
//            return responseString;
//        }
//
//        protected void onProgressUpdate(Integer... progress) {
//            setProgressPercent(progress[0]);
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            //Do anything with response..
//        }
//    }
//
//    String url = "http://api.bart.gov/api/stn.aspx?cmd=stns&key=MW9S-E7SL-26DU-VV8V";
//    RequestTask request = new RequestTask().execute(url);
//}

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView =  inflater.inflate(R.layout.fragment_main, container, false);
//
//        String[] stationNames = {"Fremont", "Fruitvale", "Powell", "Daly City"};
//
//        List<String> Names = new ArrayList<String>(Arrays.asList(stationNames));
//
//        ArrayAdapter<String> adapter= new ArrayAdapter<String>(
//                this,
//                R.layout.list_item_station,
//                R.id.list_item_station_textview,
//                Names);
//
//        ListView stations = (ListView) rootView.findViewById(R.id.stationList);
//        stations.setAdapter(adapter);
//
//        return rootView;
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            Intent intent = new Intent(this, DistanceActivity.class);
//            startActivity(intent);
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//}
