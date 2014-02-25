package com.tddrampup.fragments;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.google.android.gms.maps.GoogleMap;
import com.tddrampup.RobolectricTestRunnerWithInjection;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import roboguice.activity.RoboFragmentActivity;

import static org.mockito.Mockito.mock;

/**
 * Created by WX009-PC on 2/21/14.
 */
@Ignore
@RunWith(RobolectricTestRunnerWithInjection.class)
public class GoogleMapFragmentTest {
    private RoboFragmentActivity mActivity;
    private GoogleMapFragment mGoogleMapFragment;

    private void addFragment() {
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mGoogleMapFragment = new GoogleMapFragment(null);
        fragmentTransaction.add(mGoogleMapFragment, null);
        fragmentTransaction.commit();
    }

    @Before
    public void setUp() throws Exception {
        mActivity = Robolectric.buildActivity(RoboFragmentActivity.class).create().start().visible().get();
        addFragment();
        mGoogleMapFragment.map = mock(GoogleMap.class);
    }
}