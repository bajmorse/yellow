package com.tddrampup.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tddrampup.R;
import com.tddrampup.contentProviders.YellowContentProvider;
import com.tddrampup.databases.ListingsTable;

import roboguice.fragment.RoboFragment;

/**
 * Created by WX009-PC on 2/21/14.
 */
public class DetailFragment extends RoboFragment {

    private String mListingId;
    private TextView nameTextView;
    private TextView locationTextView;
    private TextView websiteTextView;

    public DetailFragment(String listingId) {
        mListingId = listingId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);
        nameTextView = (TextView) rootView.findViewById(R.id.name_detail_fragment);
        locationTextView = (TextView) rootView.findViewById(R.id.location_detail_fragment);
        websiteTextView = (TextView) rootView.findViewById(R.id.website_detail_fragment);
        populateTextViews();
        return rootView;
    }

    public void populateTextViews() {
        Cursor cursor = getActivity().getContentResolver().query(YellowContentProvider.CONTENT_URI_LISTINGS, null, ListingsTable.COLUMN_LISTING_ID + "=" + mListingId, null, null);
        int nameIdIndex = cursor.getColumnIndex(ListingsTable.COLUMN_NAME);
        int streetIdIndex = cursor.getColumnIndex(ListingsTable.COLUMN_STREET);
        int cityIdIndex = cursor.getColumnIndex(ListingsTable.COLUMN_CITY);
        int websiteIdIndex = cursor.getColumnIndex(ListingsTable.COLUMN_MERCHANT_URL);
        cursor.moveToFirst();
        String name = cursor.getString(nameIdIndex);
        String street = cursor.getString(streetIdIndex);
        String city = cursor.getString(cityIdIndex);
        String website = cursor.getString(websiteIdIndex);
        cursor.close();

        nameTextView.setText(name);
        locationTextView.setText(street + ", " + city);
        websiteTextView.setText(website);
    }
}
