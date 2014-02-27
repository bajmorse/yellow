package com.tddrampup.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.tddrampup.contentProviders.YellowContentProvider;
import com.tddrampup.models.Address;
import com.tddrampup.models.GeoCode;
import com.tddrampup.models.Listing;

import java.util.List;

/**
 * Created by WX009-PC on 2/26/14.
 */
public class ListingsTableHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "yellow.db";
    private static final int DATABASE_VERSION = 1;

    public ListingsTableHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ListingsTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ListingsTable.onUpgrade(db, oldVersion, newVersion);
    }

    public static void addListings(List<Listing> listings, Context context) {
        Uri uri = YellowContentProvider.CONTENT_URI;
        context.getContentResolver().delete(uri, null, null);

        for (Listing listing : listings) {
            ListingsTableHelper.addListing(listing, context);
        }
    }

    private static void addListing(Listing listing, Context context) {
        ContentValues values = new ContentValues();
        String id = listing.getId();
        if (id != null) {
            values.put(ListingsTable.COLUMN_LISTING_ID, id);
        } else {
            values.put(ListingsTable.COLUMN_LISTING_ID, "");
        }

        String name = listing.getName();
        if (name != null) {
            values.put(ListingsTable.COLUMN_NAME, name);
        } else {
            values.put(ListingsTable.COLUMN_NAME, "");
        }

        final Address address = listing.getAddress();
        if (address != null) {
            String street = address.getStreet();
            if (street != null) {
                values.put(ListingsTable.COLUMN_STREET, street);
            } else {
                values.put(ListingsTable.COLUMN_STREET, "");
            }

            String city = address.getCity();
            if (city != null) {
                values.put(ListingsTable.COLUMN_CITY, city);
            } else {
                values.put(ListingsTable.COLUMN_CITY, "");
            }
        } else {
            values.put(ListingsTable.COLUMN_STREET, "");
            values.put(ListingsTable.COLUMN_CITY, "");
        }

        String url = listing.getMerchantUrl(); // Will be null
        if (url != null) {
            values.put(ListingsTable.COLUMN_MERCHANT_URL, url);
        } else {
            values.put(ListingsTable.COLUMN_MERCHANT_URL, "");
        }

        final GeoCode geoCode = listing.getGeoCode();
        if (geoCode != null) {
            String latitude = geoCode.getLatitude();
            if (latitude != null) {
                values.put(ListingsTable.COLUMN_LATITUDE, latitude);
            } else {
                values.put(ListingsTable.COLUMN_LATITUDE, "");
            }

            String longitude = geoCode.getLongitude();
            if (longitude != null) {
                values.put(ListingsTable.COLUMN_LONGITUDE, longitude);
            } else {
                values.put(ListingsTable.COLUMN_LONGITUDE, "");
            }
        } else {
            values.put(ListingsTable.COLUMN_LATITUDE, "");
            values.put(ListingsTable.COLUMN_LONGITUDE, "");
        }

        Uri uri = context.getContentResolver().insert(YellowContentProvider.CONTENT_URI, values);
    }

    public static void updateListing(Listing listing, Context context) {
        String listingId = listing.getId();
        String merchantUrl = listing.getMerchantUrl();

        ContentValues values = new ContentValues();
        if (merchantUrl != null) {
            values.put(ListingsTable.COLUMN_MERCHANT_URL, merchantUrl);
        } else {
            values.put(ListingsTable.COLUMN_MERCHANT_URL, "");
        }
        context.getContentResolver().update(YellowContentProvider.CONTENT_URI, values, ListingsTable.COLUMN_LISTING_ID + "=" + listingId, null);
    }
}
