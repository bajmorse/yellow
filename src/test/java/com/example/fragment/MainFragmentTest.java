package com.example.fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import com.example.activity.MainActivity;
import com.example.fragments.MainFragment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.util.ActivityController;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Robolectric.buildActivity;

@RunWith(RobolectricTestRunner.class)
public class MainFragmentTest {
    private ActivityController<MainActivity> activityController;
    private MainFragment fragment;

    private void addFragment() {
        Activity activity = activityController.create().get();
        FragmentManager fragmentManager = activity.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = new MainFragment();
        fragmentTransaction.add(fragment, null);
        fragmentTransaction.commit();
    }

    @Before
    public void setUp() throws Exception {
        activityController = buildActivity(MainActivity.class);
        addFragment();
        activityController.start().resume().visible();
    }

    @Test
    public void fragmentShouldNotBeNull() throws Exception {
        assertTrue(fragment != null);
    }
}
