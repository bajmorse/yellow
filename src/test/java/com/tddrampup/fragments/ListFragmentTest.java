package com.tddrampup.fragments;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ListView;

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

import static org.robolectric.Robolectric.shadowOf;

/**
* Created by WX009-PC on 2/21/14.
*/
@RunWith(RobolectricTestRunnerWithInjection.class)
public class ListFragmentTest {
    private ListFragment mListFragment;

    @Inject
    ListingsInterface mListings;

    private void addFragment(MainActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mListFragment = new ListFragment();
        fragmentTransaction.add(mListFragment, null);
        fragmentTransaction.commit();
    }

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void onListViewItemClickedListener_shouldCallOnListViewItemClicked() throws Exception {
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().start().visible().get();
        addFragment(mainActivity);

        ListView listView = (ListView) mListFragment.getView().findViewById(R.id.list_view);
        ListingAdapter listingAdapter = new ListingAdapter(mainActivity.getLayoutInflater(), mListings.getListings());
        listView.setAdapter(listingAdapter);

        shadowOf(listView).performItemClick(0);
        //verify(mainActivity).onListViewItemClicked(0);
        //TODO: add function call assert
    }


    // TODO: populate list test
    // TODO: attach test
    // TODO: detach test
    // TODO: recycling views test
}