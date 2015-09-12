package com.example.aditya.transitalarm2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_main, container, false);

        String[] stationNames = {"Fremont", "Fruitvale", "Powell", "Daly City"};

        List<String> Names = new ArrayList<String>(Arrays.asList(stationNames));

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item_station,
                R.id.list_item_station_textview,
                Names);

        ListView stations = (ListView) rootView.findViewById(R.id.stationList);
        stations.setAdapter(adapter);

        return rootView;
    }
}
