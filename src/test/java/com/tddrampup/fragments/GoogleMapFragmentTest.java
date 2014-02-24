package com.tddrampup.fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

/**
 * Created by WX009-PC on 2/21/14.
 */
@Ignore
@RunWith(RobolectricTestRunner.class)
public class GoogleMapFragmentTest {
    private Activity activity;
    private GoogleMapFragment googleMapFragment;

    private void addFragment() {
        FragmentManager fragmentManager = activity.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        googleMapFragment = new GoogleMapFragment();
        fragmentTransaction.add(googleMapFragment, null);
        fragmentTransaction.commit();
    }

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(Activity.class).create().visible().get();
        addFragment();
    }

    @Test
    public void fragmentShouldNotBeNull() throws Exception {
//        assertTrue(googleMapFragment != null);
    }
}