package com.tddrampup.fragments;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ListView;
import android.widget.TextView;

import com.google.inject.Inject;
import com.tddrampup.R;
import com.tddrampup.RobolectricTestRunnerWithInjection;
import com.tddrampup.activities.MainActivity;
import com.tddrampup.adapters.ListingAdapter;
import com.tddrampup.singletons.ListingsInterface;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import static org.fest.assertions.api.ANDROID.assertThat;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.robolectric.Robolectric.shadowOf;

/**
* Created by WX009-PC on 2/21/14.
*/
@RunWith(RobolectricTestRunnerWithInjection.class)
public class ListFragmentTest {
    private MainActivity mMainActivity;
    private ListFragment mListFragment;
    private ListView mListView;

    @Inject
    ListingsInterface mListings;

    private void addFragment() {
        mListFragment = new ListFragment();
        FragmentManager fragmentManager = mMainActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(mListFragment, null);
        fragmentTransaction.commit();
    }

    @Before
    public void setUp() throws Exception {
        mMainActivity = Robolectric.buildActivity(MainActivity.class).create().start().visible().get();
        addFragment();
        mListings.setListings(null); // Must be after addFragment() for loading to show
        mListView = (ListView) mListFragment.getView().findViewById(R.id.list_view);
        ListingAdapter listingAdapter = new ListingAdapter(mMainActivity.getLayoutInflater(), mListings.getListings());
        mListView.setAdapter(listingAdapter);
    }

    @Test
    public void onListViewItemClickedListener_shouldCallOnListViewItemClicked() throws Exception {
        shadowOf(mListView).performItemClick(0);
        //TODO: add function call assert
    }

    @Test
    public void setupAdapter_shouldPopulateListViewWithCorrectData() throws Exception {
        TextView titleTextView = (TextView) mListView.getAdapter().getView(0, null, mListView).findViewById(R.id.listing_title);
        TextView addressTextView = (TextView) mListView.getAdapter().getView(0, null, mListView).findViewById(R.id.listing_address);
        TextView cityTextView = (TextView) mListView.getAdapter().getView(0, null, mListView).findViewById(R.id.listing_city);

        assertThat(titleTextView).hasText("One");
        assertThat(addressTextView).hasText("Street");
        assertThat(cityTextView).hasText("Toronto");
    }

    @Test
    public void loadingLifecycle_shouldShowAndHideLoadingProgressDialog() {
        assertThat(mListFragment.isProgressDialogShowing()).isTrue();
        mListFragment.new Callback().listCallbackCall(mListFragment.mListings.getListings());
        assertThat(mListFragment.isProgressDialogShowing()).isFalse();
    }

    @Test
    public void onAttach_shouldAttachOnItemClickedListener() {
        assertThat(mListFragment.mListener).isNotNull();
    }

    // TODO: recycling views test
}