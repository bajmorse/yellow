package com.tddrampup.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tddrampup.R;
import com.tddrampup.models.Listing;

import java.util.List;

/**
 * Created by WX009-PC on 2/19/14.
 */
public class GoogleMapFragment extends Fragment {

    private List<Listing> mListings;
    private GoogleMap map;

    public GoogleMapFragment(List<Listing> listings) {
        mListings = listings;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.google_map_fragment, container, false);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.google_map)).getMap();
        map.setMyLocationEnabled(true);
        addMarkers();

//        Location myLoc = map.getMyLocation();
//        if (myLoc != null){
//            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLoc.getLatitude(),myLoc.getLongitude()), 15));
//            map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
//        }

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Fragment fragment = getFragmentManager().findFragmentById(R.id.google_map);
        if (fragment != null)
            getFragmentManager().beginTransaction().remove(fragment).commit();
    }

    public void addMarkers() {
        for(Listing tempListing : mListings) {
            if (tempListing.getGeoCode() != null) {
                LatLng coordinates = new LatLng(Double.parseDouble(tempListing.getGeoCode().getLatitude()), Double.parseDouble(tempListing.getGeoCode().getLongitude()));
                map.addMarker(new MarkerOptions().position(coordinates).title(tempListing.getName()));
            }
        }
    }
}
