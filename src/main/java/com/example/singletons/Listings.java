package com.example.singletons;

import com.example.models.Listing;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WX009-PC on 2/19/14.
 */
public class Listings {

    private static Listings mInstance = null;
    private List<Listing> mListings;

    private Listings() {
        mListings = new ArrayList<Listing>();
    }

    public static Listings getInstance(){
        if(mInstance == null)
        {
            mInstance = new Listings();
        }
        return mInstance;
    }

    public List<Listing> getListings(){
        return this.mListings;
    }

    public void setListings(List<Listing> value){
        mListings = value;
    }
}
