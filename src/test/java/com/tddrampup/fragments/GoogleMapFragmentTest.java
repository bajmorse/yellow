package com.tddrampup.fragments;

import android.location.Location;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.tddrampup.RobolectricTestRunnerWithInjection;
import com.tddrampup.factories.FakeCameraUpdateFactoryWrapper;
import com.tddrampup.factories.FakeMarkerOptionsFactoryWrapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import roboguice.activity.RoboFragmentActivity;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by WX009-PC on 2/21/14.
 */
@RunWith(RobolectricTestRunnerWithInjection.class)
public class GoogleMapFragmentTest {
    private RoboFragmentActivity mActivity;
    private GoogleMapFragment mGoogleMapFragment;

    static final String HAMBURG = "Hamburg", HAMBURG_LAT = "53.558", HAMBURG_LONG = "9.927";

    private void addFragment() {
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mGoogleMapFragment = new GoogleMapFragment();
        fragmentTransaction.add(mGoogleMapFragment, null);
        fragmentTransaction.commit();
    }

    private void removeFragment() {
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(mGoogleMapFragment);
        fragmentTransaction.commit();
    }

    @Before
    public void setUp() throws Exception {
        mActivity = Robolectric.buildActivity(RoboFragmentActivity.class).create().start().visible().get();
        addFragment();
        mGoogleMapFragment.map = mock(GoogleMap.class);
        mGoogleMapFragment.setupMap();
    }

    @Test
    public void onResume_shouldInstantiateMap() throws Exception {
        assertThat(mGoogleMapFragment.map).isNotNull();
    }

    @Test
    public void setupMap_shouldZoomToCurrentLocation() throws Exception {
        LatLng fakeLatLng = new LatLng(Double.parseDouble(HAMBURG_LAT), Double.parseDouble(HAMBURG_LONG));
        Location fakeLocation = new Location("fakeLocation");
        fakeLocation.setLatitude(Double.parseDouble(HAMBURG_LAT));
        fakeLocation.setLongitude(Double.parseDouble(HAMBURG_LONG));
        mGoogleMapFragment.mListener.onMyLocationChange(fakeLocation);
        assertThat(((FakeCameraUpdateFactoryWrapper) mGoogleMapFragment.cameraUpdateFactory).mLatLng).isEqualTo(fakeLatLng);
    }

    @Test
    public void addMarkers_shouldPopulateMapWithMarkers() throws Exception {
        assertThat(((FakeMarkerOptionsFactoryWrapper) mGoogleMapFragment.markerOptionsFactory).mLatLng).isEqualTo(new LatLng(Double.parseDouble(HAMBURG_LAT), Double.parseDouble(HAMBURG_LONG)));
        assertThat(((FakeMarkerOptionsFactoryWrapper) mGoogleMapFragment.markerOptionsFactory).mName).isEqualTo("One");
    }

    @Test
    public void destroyView_shouldDestroyView() throws Exception {
        removeFragment();
        assertThat(mGoogleMapFragment.isAdded()).isFalse();
    }
}