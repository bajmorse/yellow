package com.example.activities;

import android.app.Fragment;
import android.widget.Button;

import com.example.R;

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

//    @Test
//    public void ListListenerClick_shouldOpenListFragment() {
////        activity.onListClick();
////        assertThat()
//    }

    @Test
    public void listButtonClickShouldOpenListFragment() throws Exception {
        Button listButton = (Button) activity.findViewById(R.id.list_button);
        listButton.performClick();
        Fragment listFragment = activity.getFragmentManager().findFragmentByTag("MY_LIST_FRAGMENT");
        assertThat(listFragment).isVisible();
    }

    @Test
    public void mapButtonClickShouldOpenMapFragment() throws Exception {
        Button mapButton = (Button) activity.findViewById(R.id.map_button);
        mapButton.performClick();
        Fragment mapFragment = activity.getFragmentManager().findFragmentByTag("MY_MAP_FRAGMENT");
        assertThat(mapFragment).isVisible();
    }
}
