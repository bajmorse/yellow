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
public class ListFragmentTest {
    private Activity activity;
    private ListFragment listFragment;

    private void addFragment() {
        FragmentManager fragmentManager = activity.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        listFragment = new ListFragment();
        fragmentTransaction.add(listFragment, null);
        fragmentTransaction.commit();
    }

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(Activity.class).create().visible().get();
        addFragment();
    }

    @Test
    public void fragmentShouldNotBeNull() throws Exception {
        assertTrue(listFragment != null);
    }

    // TODO: populate list test
    // TODO: attach test
    // TODO: detach test
    // TODO: recycling views test
}