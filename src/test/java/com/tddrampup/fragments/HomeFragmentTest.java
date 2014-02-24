package com.tddrampup.fragments;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;

import com.tddrampup.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import roboguice.activity.RoboFragmentActivity;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class HomeFragmentTest {
    private RoboFragmentActivity activity;
    private HomeFragment homeFragment;
    private Button listButton;
    private Button mapButton;

    private void addFragment() {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        homeFragment = new HomeFragment();
        fragmentTransaction.add(homeFragment, null);
        fragmentTransaction.commit();
    }

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(RoboFragmentActivity.class).create().start().visible().get();
        addFragment();
        homeFragment.mListener = mock(HomeFragment.onItemClickedListener.class);
        listButton = (Button) homeFragment.getView().findViewById(R.id.list_button);
        mapButton = (Button) homeFragment.getView().findViewById(R.id.map_button);
    }

    @Test
    public void ListButtonClick_shouldCallListClickListener() {
        listButton.performClick();
        verify(homeFragment.mListener).onListButtonClicked();
    }

    @Test
    public void MapButtonClick_shouldCallMapClickListener() {
        mapButton.performClick();
        verify(homeFragment.mListener).onMapButtonClicked();
    }

    // TODO: on attach test
    // TODO: on detach test
}
