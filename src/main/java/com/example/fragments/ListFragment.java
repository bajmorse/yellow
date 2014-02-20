package com.example.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.R;
import com.example.adapters.ListingAdapter;
import com.example.models.YellowResponse;
import com.example.singletons.Listings;
import com.google.gson.Gson;
import org.json.JSONObject;

/**
 * Created by WX009-PC on 2/19/14.
 */
public class ListFragment extends Fragment {

    private static final String mUrl = "http://api.sandbox.yellowapi.com/FindBusiness/?what=Restaurants&where=Toronto&pgLen=40&pg=1&dist=1&fmt=JSON&lang=en&UID=jkhlh&apikey=4nd67ycv3yeqtg97dku7m845";

    private RequestQueue mRequestQueue;
    private ListView mListView;
    private ListingAdapter mListingAdapter;
    private LayoutInflater mLayoutInflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mLayoutInflater = inflater;
        View rootView = mLayoutInflater.inflate(R.layout.list_fragment, container, false);

        mListView = (ListView) rootView.findViewById(R.id.list_view);
        mRequestQueue =  Volley.newRequestQueue(getActivity());

        JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,mUrl,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                YellowResponse yellowResponse = gson.fromJson(response.toString(), YellowResponse.class);
                Listings.getInstance().setListings(yellowResponse.getListings());
                mListingAdapter = new ListingAdapter(mLayoutInflater, Listings.getInstance().getListings());
                mListView.setAdapter(mListingAdapter);
                mListingAdapter.notifyDataSetChanged();
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("LOGGING:", error.getMessage());
            }
        });

        mRequestQueue.add(jr);

        return rootView;
    }
}