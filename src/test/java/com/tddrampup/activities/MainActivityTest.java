package com.tddrampup.activities;

import android.support.v4.app.Fragment;
import android.widget.Button;

import com.google.inject.Inject;
import com.tddrampup.R;
import com.tddrampup.RobolectricTestRunnerWithInjection;
import com.tddrampup.fragments.HomeFragment;
import com.tddrampup.singletons.ListingsInterface;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import static org.fest.assertions.api.ANDROID.assertThat;

@RunWith(RobolectricTestRunnerWithInjection.class)
public class MainActivityTest {
    private MainActivity mMainActivity;

    @Inject
    ListingsInterface mListings;

    @Before
    public void setUp() throws Exception {
        mListings.setListings(null);
        mMainActivity = Robolectric.buildActivity(MainActivity.class).create().start().visible().get();
    }

    @Test
    public void onCreate_shouldDisplayHomeFragment() throws Exception {
        HomeFragment homeFragment = (HomeFragment) mMainActivity.getSupportFragmentManager().findFragmentByTag("MY_HOME_FRAGMENT");
        assertThat(homeFragment).isVisible();
    }

    @Test
    public void onListButtonClicked_shouldOpenListFragment() throws Exception {
        Button listButton = (Button) mMainActivity.findViewById(R.id.list_button);
        listButton.performClick();
        Fragment listFragment = mMainActivity.getSupportFragmentManager().findFragmentByTag("MY_LIST_FRAGMENT");
        assertThat(listFragment).isVisible();
    }

    @Ignore
    @Test
    public void onMapButtonClicked_shouldOpenMapFragment() throws Exception {
        Button mapButton = (Button) mMainActivity.findViewById(R.id.map_button);
        mapButton.performClick();
        Fragment mapFragment = mMainActivity.getSupportFragmentManager().findFragmentByTag("MY_GOOGLE_MAP_FRAGMENT");
        assertThat(mapFragment).isVisible();
    }

    @Test
    public void itemCallbackCall_shouldOpenDetailFragment() throws Exception {
        mMainActivity.new Callback().itemCallbackCall(mListings.getListings().get(0));
        Fragment detailFragment = mMainActivity.getSupportFragmentManager().findFragmentByTag("MY_DETAIL_FRAGMENT");
        assertThat(detailFragment).isVisible();
    }
}
