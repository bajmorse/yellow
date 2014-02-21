package com.tddrampup.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.tddrampup.R;

/**
 * Created by WX009-PC on 2/19/14.
 */
public class GoogleMapFragment extends Fragment {

    private GoogleMap mapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.google_map_fragment, container, false);

        mapView = ((MapFragment) getFragmentManager().findFragmentById(R.id.google_map)).getMap();
        mapView.setMyLocationEnabled(true);

        return rootView;
    }
}
