package com.tddrampup.fragments;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import roboguice.activity.RoboFragmentActivity;

/**
 * Created by WX009-PC on 2/21/14.
 */
@Ignore
@RunWith(RobolectricTestRunner.class)
public class GoogleMapFragmentTest {
    private RoboFragmentActivity activity;
    private GoogleMapFragment googleMapFragment;

    private void addFragment() {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        googleMapFragment = new GoogleMapFragment(null);
        fragmentTransaction.add(googleMapFragment, null);
        fragmentTransaction.commit();
    }

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(RoboFragmentActivity.class).create().start().visible().get();
        addFragment();
    }
}