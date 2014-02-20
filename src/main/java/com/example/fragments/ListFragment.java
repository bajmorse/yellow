package com.example.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.R;
import com.example.adapters.ListingAdapter;
import com.example.models.Listing;
import com.example.serviceLayers.VolleyServiceLayer;
import com.example.serviceLayers.VolleyServiceLayerCallback;
import com.example.singletons.Listings;

import java.util.List;

/**
 * Created by WX009-PC on 2/19/14.
 */
public class ListFragment extends Fragment  {

    private static final String mUrl = "http://api.sandbox.yellowapi.com/FindBusiness/?what=Restaurants&where=Toronto&pgLen=40&pg=1&dist=1&fmt=JSON&lang=en&UID=jkhlh&apikey=4nd67ycv3yeqtg97dku7m845";

    private ListView mListView;
    private ListingAdapter mListingAdapter;
    private LayoutInflater mLayoutInflater;
    private VolleyServiceLayer volleyServiceLayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mLayoutInflater = inflater;
        View rootView = mLayoutInflater.inflate(R.layout.list_fragment, container, false);
        mListView = (ListView) rootView.findViewById(R.id.list_view);

        volleyServiceLayer = new VolleyServiceLayer(rootView.getContext());
        volleyServiceLayer.volleyServiceLayerCallback = new Callback();
        volleyServiceLayer.GetListings(mUrl);

        return rootView;
    }

    class Callback implements VolleyServiceLayerCallback {
        public void callbackCall(List<Listing> listings) {
            Listings.getInstance().setListings(listings);
            mListingAdapter = new ListingAdapter(mLayoutInflater, Listings.getInstance().getListings());
            mListView.setAdapter(mListingAdapter);
            mListingAdapter.notifyDataSetChanged();
        }
    }
}
