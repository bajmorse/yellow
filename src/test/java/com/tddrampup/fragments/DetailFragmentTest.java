package com.tddrampup.fragments;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.google.inject.Inject;
import com.tddrampup.R;
import com.tddrampup.RobolectricTestRunnerWithInjection;
import com.tddrampup.models.Listing;
import com.tddrampup.singletons.ListingsInterface;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import roboguice.activity.RoboFragmentActivity;

import static org.fest.assertions.api.ANDROID.assertThat;

/**
* Created by WX009-PC on 2/21/14.
*/
@RunWith(RobolectricTestRunnerWithInjection.class)
public class DetailFragmentTest{
    private RoboFragmentActivity activity;
    private DetailFragment detailFragment;
    private Listing listing;
    private TextView nameTextView;
    private TextView locationTextView;
    private TextView websiteTextView;

    @Inject
    ListingsInterface listings;

    private void addFragment() {
        listing = listings.getListings().get(0);
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        detailFragment = new DetailFragment(listing);
        fragmentTransaction.add(detailFragment, null);
        fragmentTransaction.commit();
    }

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(RoboFragmentActivity.class).create().start().visible().get();
        addFragment();
        nameTextView = (TextView) detailFragment.getView().findViewById(R.id.name_detail_fragment);
        locationTextView = (TextView) detailFragment.getView().findViewById(R.id.location_detail_fragment);
        websiteTextView = (TextView) detailFragment.getView().findViewById(R.id.website_detail_fragment);
    }

    @Test
    public void getName_shouldMatchListing() throws Exception {
        assertThat(nameTextView).hasText("One");
    }

    @Test
    public void getLocation_shouldMatchListing() throws Exception {
        assertThat(locationTextView).hasText(listing.getAddress().getStreet() + ", " + listing.getAddress().getCity() + ", " + listing.getAddress().getProv() + ", " + listing.getAddress().getPcode());
    }

    @Test
    public void getWebsite_shouldMatchListing() throws Exception {
        assertThat(websiteTextView).hasText("www.herpaderp.com");
    }
}
