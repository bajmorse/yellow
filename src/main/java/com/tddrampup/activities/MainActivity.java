package com.tddrampup.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.tddrampup.R;
import com.tddrampup.fragments.DetailFragment;
import com.tddrampup.fragments.GoogleMapFragment;
import com.tddrampup.fragments.HomeFragment;
import com.tddrampup.fragments.ListFragment;
import com.tddrampup.models.Listing;
import com.tddrampup.serviceLayers.VolleyServiceLayer;
import com.tddrampup.serviceLayers.VolleyServiceLayerCallback;
import com.tddrampup.singletons.Listings;

import java.util.List;

public class MainActivity extends Activity implements HomeFragment.onItemClickedListener, ListFragment.onListViewItemClickedListener {

    private VolleyServiceLayer volleyServiceLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        volleyServiceLayer = new VolleyServiceLayer(getApplicationContext());

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.main_activity, new HomeFragment(), "MY_HOME_FRAGMENT").commit();
        }
    }

    @Override
    public void onListButtonClicked(){
        ListFragment listFragment = new ListFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_activity, listFragment, "MY_LIST_FRAGMENT");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onMapButtonClicked(){
        GoogleMapFragment googleMapFragment = new GoogleMapFragment(Listings.getInstance().getListings());
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_activity, googleMapFragment, "MY_GOOGLE_MAP_FRAGMENT");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onListViewItemClicked(int position) {
        Listing listing = Listings.getInstance().getListings().get(position);
        volleyServiceLayer.volleyServiceLayerCallback = new Callback();
        volleyServiceLayer.GetListing(listing.getId());
    }

    class Callback implements VolleyServiceLayerCallback {
        public void listCallbackCall(List<Listing> listings) {
            // do nothing
        }

        @Override
        public void itemCallbackCall(Listing listing) {
            DetailFragment detailFragment = new DetailFragment(listing);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.main_activity, detailFragment, "MY_DETAIL_FRAGMENT");
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
