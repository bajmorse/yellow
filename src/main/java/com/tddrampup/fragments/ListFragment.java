package com.tddrampup.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.inject.Inject;
import com.tddrampup.R;
import com.tddrampup.contentProviders.YellowContentProvider;
import com.tddrampup.databases.ListingsTable;
import com.tddrampup.databases.ListingsTableHelper;
import com.tddrampup.databases.PreviousQueryTableHelper;
import com.tddrampup.databases.SearchTable;
import com.tddrampup.databases.SearchTableHelper;
import com.tddrampup.models.Listing;
import com.tddrampup.serviceLayers.VolleyServiceLayer;
import com.tddrampup.serviceLayers.VolleyServiceLayerCallback;
import com.tddrampup.singletons.ListingsInterface;

import java.util.List;

import roboguice.fragment.RoboFragment;

/**
 * Created by WX009-PC on 2/19/14.
 */
public class ListFragment extends RoboFragment {

    private static final String API_KEY = "4nd67ycv3yeqtg97dku7m845";
    private static final String MAX_LISTINGS = "40";
    private static final String DEFAULT_WHAT = "Restaurants";
    private static final String DEFAULT_WHERE = "Toronto";

    private ListView mListView;
    private LayoutInflater mLayoutInflater;
    private VolleyServiceLayer volleyServiceLayer;
    private ProgressDialog mProgressDialog;
    private String mUrl;
    private boolean mAllowNetworkCall;
    private boolean mIsSearchQuery;

    public String mWhat;
    public String mWhere;
    //TODO don't make these public

    public onListViewItemClickedListener mListener;

    @Inject
    ListingsInterface mListings;

    public ListFragment() {
        mWhat = DEFAULT_WHAT;
        mWhere = DEFAULT_WHERE;
        mIsSearchQuery = false;
    }

    public ListFragment(final String what, final String where) {
        mWhat = what;
        mWhere = where;
        mIsSearchQuery = true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrl = "http://api.sandbox.yellowapi.com/FindBusiness/?what=" + mWhat + "&where=" + mWhere + "&pgLen=" + MAX_LISTINGS + "&pg=1&dist=1&fmt=JSON&lang=en&UID=jkhlh&apikey=" + API_KEY;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mLayoutInflater = inflater;
        View rootView = mLayoutInflater.inflate(R.layout.list_fragment, container, false);
        mListView = (ListView) rootView.findViewById(R.id.list_view);
        mProgressDialog = new ProgressDialog(getActivity());

        mListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onListViewItemClicked(position, mIsSearchQuery);
            }
        });

        if (mIsSearchQuery) {
            List<String> previousQuery = PreviousQueryTableHelper.getQuery(getActivity());
            if (mWhat.equals(previousQuery.get(0)) && mWhere.equals(previousQuery.get(1))) {
                mAllowNetworkCall = false;
            } else {
                mAllowNetworkCall = true;
                PreviousQueryTableHelper.setQuery(getActivity(), mWhat, mWhere);
            }
        } else {
            Cursor cursor = getActivity().getContentResolver().query(YellowContentProvider.CONTENT_URI_LISTINGS, null, null, null, null);
            if (cursor.moveToFirst()) {
                mAllowNetworkCall = false;
            } else {
                mAllowNetworkCall = true;
            }
        }

        if (mAllowNetworkCall) {
            showLoading();
            volleyServiceLayer = new VolleyServiceLayer(rootView.getContext());
            volleyServiceLayer.volleyServiceLayerCallback = new Callback();
            volleyServiceLayer.GetListings(mUrl);
        }
        else {
            setupAdapter();
        }

        return rootView;
    }

    public interface onListViewItemClickedListener {
        public void onListViewItemClicked(int position, boolean isSearchQuery);
    }

    class Callback implements VolleyServiceLayerCallback {
        public void listCallbackCall(List<Listing> listings) {
            hideLoading();
            if (mIsSearchQuery) {
                SearchTableHelper.addListings(listings, getActivity());
            } else {
                ListingsTableHelper.addListings(listings, getActivity());
            }
            setupAdapter();
        }

        @Override
        public void itemCallbackCall(Listing listing, Boolean isSearchQuery) {
            // do nothing
        }
    }

    private void setupAdapter(){
        final int views[] = { R.id.listing_title, R.id.listing_address, R.id.listing_city };
        final Cursor cursor;
        String[] fields;
        if (mIsSearchQuery) {
            String[] tempFields = { SearchTable.COLUMN_NAME, SearchTable.COLUMN_STREET, SearchTable.COLUMN_CITY };
            fields = tempFields;
            cursor = getActivity().getContentResolver().query(YellowContentProvider.CONTENT_URI_SEARCH_LISTINGS, SearchTableHelper.searchTableAdapterProjection, null, null, null);
        } else {
            String[] tempFields = { ListingsTable.COLUMN_NAME, ListingsTable.COLUMN_STREET, ListingsTable.COLUMN_CITY };
            fields = tempFields;
            cursor = getActivity().getContentResolver().query(YellowContentProvider.CONTENT_URI_LISTINGS, ListingsTableHelper.listingsTableAdapterProjection, null, null, null);
        }
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.row_listview, cursor, fields, views, 0);
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof onListViewItemClickedListener) {
            mListener = (onListViewItemClickedListener) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void showLoading() {
        mProgressDialog.setTitle("Loading:");
        mProgressDialog.setMessage("Fetching listings...");
        mProgressDialog.show();
    }

    private void hideLoading() {
        mProgressDialog.dismiss();
    }

    public boolean isProgressDialogShowing() {
        return mProgressDialog.isShowing();
    }
}
