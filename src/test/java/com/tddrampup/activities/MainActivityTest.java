package com.tddrampup.activities;

import android.support.v4.app.Fragment;
import android.widget.Button;

import com.tddrampup.R;
import com.tddrampup.fragments.HomeFragment;

import org.junit.Before;
import org.junit.Ignore;
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
        activity = Robolectric.buildActivity(MainActivity.class).create().start().visible().get();
    }

    @Test
    public void onCreate_shouldDisplayHomeFragment() throws Exception {
        HomeFragment homeFragment = (HomeFragment) activity.getSupportFragmentManager().findFragmentByTag("MY_HOME_FRAGMENT");
        assertThat(homeFragment).isVisible();
    }

    @Test
    public void onListButtonClicked_shouldOpenListFragment() throws Exception {
        Button listButton = (Button) activity.findViewById(R.id.list_button);
        listButton.performClick();
        Fragment listFragment = activity.getSupportFragmentManager().findFragmentByTag("MY_LIST_FRAGMENT");
        assertThat(listFragment).isVisible();
    }

    @Ignore
    @Test
    public void onMapButtonClicked_shouldOpenMapFragment() throws Exception {
        Button mapButton = (Button) activity.findViewById(R.id.map_button);
        mapButton.performClick();
        Fragment mapFragment = activity.getSupportFragmentManager().findFragmentByTag("MY_GOOGLE_MAP_FRAGMENT");
        assertThat(mapFragment).isVisible();
    }

//    @Test
//    public void onListViewItemClicked_shouldOpenDetailFragment() throws Exception {
//        ListFragment listFragment = new ListFragment();
//        ListView listView = (ListView) listFragment.getView().findViewById(R.id.list_view);
//        ListingAdapter listingAdapter = new ListingAdapter(activity.getLayoutInflater(), mListings);
//        listView.setAdapter(listingAdapter);
//        shadowOf(listView).populateItems();
//        shadowOf(listView).performItemClick(0);
//        Fragment detailFragment = activity.getFragmentManager().findFragmentByTag("MY_DETAIL_FRAGMENT");
//        assertThat(detailFragment).isVisible();
//    }
}
