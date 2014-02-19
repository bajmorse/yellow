package com.example.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.R;
import com.example.models.Listing;
import com.example.singletons.Listings;

/**
 * Created by WX009-PC on 2/19/14.
 */
public class ListingAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;

    public ListingAdapter(LayoutInflater layoutInflater) {
        mLayoutInflater = layoutInflater;
    }

    @Override
    public int getCount() {
        return Listings.getInstance().getListings().size();
    }

    @Override
    public Object getItem(int i) {
        return Listings.getInstance().getListings().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null) {
            viewHolder = new ViewHolder();
            view = mLayoutInflater.inflate(R.layout.row_listview, null);
            viewHolder.listingTitle = (TextView) view.findViewById(R.id.listing_title);
            viewHolder.listingAddress = (TextView) view.findViewById(R.id.listing_address);
            viewHolder.listingProvince = (TextView) view.findViewById(R.id.listing_province);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }

        Listing listing = Listings.getInstance().getListings().get(i);
        viewHolder.listingTitle.setText(listing.getName());
        viewHolder.listingAddress.setText(listing.getAddress().getStreet());
        viewHolder.listingProvince.setText(listing.getAddress().getCity());
        return view;
    }

    private class ViewHolder {
        TextView listingTitle;
        TextView listingAddress;
        TextView listingProvince;
    }
}
