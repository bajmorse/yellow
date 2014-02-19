package com.example.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import com.example.R;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.ActivityController;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Robolectric.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class MainFragmentTest {
    private ActivityController<MainActivity> activityController;
    private MainActivity.MainFragment fragment;

    private void addFragment() {
//        FragmentActivity activity = activityController.create().get();
//        FragmentManager fragmentManager = activity.getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragment = new RegistrationStep1Fragment();
//        fragmentTransaction.add(fragment, null);
//        fragmentTransaction.commit();
    }

    @Before
    public void setUp() throws Exception {
//        activity = Robolectric.buildActivity(MainActivity.class).create().visible().get();
//        activity.onCreate(null);
//        fragment = activity.getFragmentManager();
        //activity.getFragmentManager().beginTransaction().add(R.id.main_activity, FRAGMENT_TAG).commit();
    }

    @Test
    public void activityShouldNotBeNull() throws Exception {
//        Activity activity = Robolectric.buildActivity(MainActivity.class).create().get();
//        assertTrue(activity != null);
    }

    @Test
    public void fragmentShouldNotBeNull() throws Exception {


//        FragmentManager fragmentManager = activity.getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        assertTrue(fragmentTransaction.isEmpty());
    }
}
