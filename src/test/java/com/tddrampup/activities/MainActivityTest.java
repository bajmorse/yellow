package com.tddrampup.activities;

import android.app.Fragment;
import android.widget.Button;

import com.tddrampup.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.fest.assertions.api.ANDROID.assertThat;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
    private MainActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(MainActivity.class).create().visible().get();
    }

    @Test
    public void onCreate_shouldDisplayHomeFragment() throws Exception {
        Fragment homeFragment = activity.getFragmentManager().findFragmentByTag("MY_HOME_FRAGMENT");
        assertThat(homeFragment).isVisible();
    }

    @Test
    public void onListButtonClicked_shouldOpenListFragment() throws Exception {
        Button listButton = (Button) activity.findViewById(R.id.list_button);
        listButton.performClick();
        Fragment listFragment = activity.getFragmentManager().findFragmentByTag("MY_LIST_FRAGMENT");
        assertThat(listFragment).isVisible();
    }

    @Test
    public void onMapButtonClicked_shouldOpenMapFragment() throws Exception {
        Button mapButton = (Button) activity.findViewById(R.id.map_button);
        mapButton.performClick();
        Fragment mapFragment = activity.getFragmentManager().findFragmentByTag("MY_MAP_FRAGMENT");
        assertThat(mapFragment).isVisible();
    }
}
