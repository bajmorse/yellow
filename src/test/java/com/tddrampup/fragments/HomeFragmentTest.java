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
    private RoboFragmentActivity mActivity;
    private HomeFragment mHomeFragment;
    private Button listButton;
    private Button mapButton;

    private void addFragment() {
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mHomeFragment = new HomeFragment();
        fragmentTransaction.add(mHomeFragment, null);
        fragmentTransaction.commit();
    }

    @Before
    public void setUp() throws Exception {
        mActivity = Robolectric.buildActivity(RoboFragmentActivity.class).create().start().visible().get();
        addFragment();
        mHomeFragment.mListener = mock(HomeFragment.onItemClickedListener.class);
        listButton = (Button) mHomeFragment.getView().findViewById(R.id.list_button);
        mapButton = (Button) mHomeFragment.getView().findViewById(R.id.map_button);
    }

    @Test
    public void ListButtonClick_shouldCallListClickListener() {
        listButton.performClick();
        verify(mHomeFragment.mListener).onListButtonClicked();
    }

    @Test
    public void MapButtonClick_shouldCallMapClickListener() {
        mapButton.performClick();
        verify(mHomeFragment.mListener).onMapButtonClicked();
    }

    // TODO: on attach test
    // TODO: on detach test
}
