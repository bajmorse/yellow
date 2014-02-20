package com.example.adapters;

import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.R;
import com.example.activity.MainActivity;
import com.example.models.Address;
import com.example.models.Listing;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import java.util.ArrayList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
* Created by WX009-PC on 2/19/14.
*/
@RunWith(RobolectricTestRunner.class)
public class ListingAdapterTest {
    private ListingAdapter adapter;
    private MainActivity activity;
    private  ArrayList<Listing> listings;
   // private Listing one;

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
        assertThat(adapter.getCount(), equalTo(listings.size()));
    }

    @Test
    public void getItem_shouldReturnInfo() throws Exception {
        assertThat(((Listing) adapter.getItem(0)).getName(), equalTo(listings.get(0).getName()));
    }

    @Test
    public void testGetItemId() throws Exception {
        assertThat(adapter.getItemId(0), equalTo(0L));
    }

    @Test
    public void testGetView() throws Exception {
        TextView nameView =  (TextView)adapter.getView(0, null, new LinearLayout(activity)).findViewById(R.id.listing_title);
        assertThat(nameView.getText().toString(), equalTo(listings.get(0).getName()));
    }
}
