package com.tddrampup.fragments;

import android.location.Location;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.inject.Inject;
import com.tddrampup.RobolectricTestRunnerWithInjection;
import com.tddrampup.factories.FakeCameraUpdateFactoryWrapper;
import com.tddrampup.singletons.ListingsInterface;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import java.util.ArrayList;

import roboguice.activity.RoboFragmentActivity;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by WX009-PC on 2/21/14.
 */
@RunWith(RobolectricTestRunnerWithInjection.class)
public class GoogleMapFragmentTest {
    private RoboFragmentActivity mActivity;
    private GoogleMapFragment mGoogleMapFragment;
    private ArrayList<Marker> mMarkers;

    static final String HAMBURG = "Hamburg", HAMBURG_LAT = "53.558", HAMBURG_LONG = "9.927";
//    static final String KIEL = "Kiel", KIEL_LAT = "53.551", KIEL_LONG = "9.993";

    @Inject
    ListingsInterface mListings;

    private void addFragment() {
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mGoogleMapFragment = new GoogleMapFragment();
        fragmentTransaction.add(mGoogleMapFragment, null);
        fragmentTransaction.commit();
    }

    @Before
    public void setUp() throws Exception {
        mActivity = Robolectric.buildActivity(RoboFragmentActivity.class).create().start().visible().get();
        addFragment();

        mGoogleMapFragment.map = mock(GoogleMap.class);
        Marker hamburgMarker = mock(Marker.class);
        when(hamburgMarker.getPosition()).thenReturn(new LatLng(Double.parseDouble(HAMBURG_LAT), Double.parseDouble(HAMBURG_LONG)));
//        Marker kielMarker = mock(Marker.class);
//        when(kielMarker.getPosition()).thenReturn(new LatLng(Double.parseDouble(KIEL_LAT), Double.parseDouble(KIEL_LONG)));
        mMarkers = new ArrayList<Marker>();
        mMarkers.add(hamburgMarker);
//        mMarkers.add(kielMarker);

        when(mGoogleMapFragment.map.addMarker(any(MarkerOptions.class))).thenReturn(hamburgMarker);
    }

    @Test
    public void setupMap_shouldZoomToCurrentLocation() {
        mGoogleMapFragment.setupMap();
        LatLng fakeLatLng = new LatLng(Double.parseDouble(HAMBURG_LAT), Double.parseDouble(HAMBURG_LONG));
        Location fakeLocation = new Location("fakeLocation");
        fakeLocation.setLatitude(Double.parseDouble(HAMBURG_LAT));
        fakeLocation.setLongitude(Double.parseDouble(HAMBURG_LONG));
        mGoogleMapFragment.mListener.onMyLocationChange(fakeLocation);
        assertThat(((FakeCameraUpdateFactoryWrapper) mGoogleMapFragment.cameraUpdateFactory).mLatLng).isEqualTo(fakeLatLng);
    }
}