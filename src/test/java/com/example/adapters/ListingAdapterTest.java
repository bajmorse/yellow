package com.example.adapters;

import android.view.View;
import android.widget.TextView;

import com.example.R;
import com.example.activities.MainActivity;
import com.example.models.Address;
import com.example.models.Listing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

import static org.fest.assertions.api.ANDROID.assertThat;
import static org.fest.assertions.api.Assertions.assertThat;

/**
* Created by WX009-PC on 2/19/14.
*/
@RunWith(RobolectricTestRunner.class)
public class ListingAdapterTest {
    private ListingAdapter adapter;
    private MainActivity activity;
    private  ArrayList<Listing> listings;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(MainActivity.class).create().visible().get();

        Listing one = new Listing();
        Address addOne = new Address();
        addOne.setCity("Toronto");
        addOne.setStreet("Street");
        one.setName("One");
        one.setAddress(addOne);
        listings = new ArrayList<Listing>();
        listings.add(one);

        adapter = new ListingAdapter(activity.getLayoutInflater(), listings);
    }

    @Test
    public void getCount_shouldReturnListSize() throws Exception {
        assertThat(adapter).hasCount(1);
    }

    @Test
    public void getItem_shouldReturnInfo() throws Exception {
        View view = adapter.getView(0, null, null);
        assertThat((TextView)view.findViewById(R.id.listing_title)).hasText("One");
    }

    @Test
    public void testGetItemId() throws Exception {
        assertThat(adapter.getItemId(0)).isEqualTo((long)0);
    }
}
