package com.tddrampup.fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertTrue;

/**
 * Created by WX009-PC on 2/21/14.
 */
@RunWith(RobolectricTestRunner.class)
public class MapFragmentTest {
    private Activity activity;
    private MapFragment mapFragment;

    private void addFragment() {
        FragmentManager fragmentManager = activity.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mapFragment = new MapFragment();
        fragmentTransaction.add(mapFragment, null);
        fragmentTransaction.commit();
    }

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(Activity.class).create().visible().get();
        addFragment();
    }

    @Test
    public void fragmentShouldNotBeNull() throws Exception {
        assertTrue(mapFragment != null);
    }
}