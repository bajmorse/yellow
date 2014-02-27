package com.tddrampup.contentProviders;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.tddrampup.databases.ListingsTable;
import com.tddrampup.databases.ListingsTableHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by WX009-PC on 2/26/14.
 */
public class YellowContentProvider extends ContentProvider {

    // Database and helper
    private ListingsTableHelper listingsTableHelper;
    private SQLiteDatabase listingsDatabase;

    // Content provider fields
    private static final String AUTHORITY = "com.tddrampup.contentProviders";
    private static final String BASE_PATH = "listings";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    // URI integer values
    private static final int LISTINGS = 1;
    private static final int LISTINGS_ID = 2;

    // Content types
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/listings";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/listing";

    // Projection map
    private static HashMap<String, String> listingsMap;
    // Maps content URI to integer values
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, BASE_PATH, LISTINGS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", LISTINGS_ID);
    }

    @Override
    public boolean onCreate() {
        listingsTableHelper = new ListingsTableHelper(getContext());
        listingsDatabase = listingsTableHelper.getWritableDatabase();

        if(listingsDatabase == null) {
            Log.d("DEBUG", "Database is null!");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Using SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // check if the caller has requested a column which does not exists
        checkColumns(projection);

        // Set the table
        queryBuilder.setTables(ListingsTable.LISTINGS_TABLE);

        int uriType = uriMatcher.match(uri);
        switch (uriType) {
            case LISTINGS:
                queryBuilder.setProjectionMap(listingsMap);
                break;
            case LISTINGS_ID:
                // adding the ID to the original query
                queryBuilder.appendWhere(ListingsTable.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        listingsDatabase = listingsTableHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(listingsDatabase, projection, selection, selectionArgs, null, null, sortOrder);

        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {

        int uriType = uriMatcher.match(uri);
        switch (uriType) {
            case LISTINGS:
                return ""; //TODO
            case LISTINGS_ID:
                return ""; //TODO
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = uriMatcher.match(uri);
        listingsDatabase = listingsTableHelper.getWritableDatabase();
        long id = 0;

//        Log.d("DEBUG", "Insert: " + uri.toString() + " Values: " + values.toString() + " TYPE: " + uriType);

        switch (uriType) {
            case LISTINGS:
                Log.d("DEBUG", "Database: " + listingsDatabase.toString());
                id = listingsDatabase.insert(ListingsTable.LISTINGS_TABLE, null, values);
                listingsDatabase.close();
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = uriMatcher.match(uri);
        listingsDatabase = listingsTableHelper.getWritableDatabase();
        int rowsDeleted = 0;

        switch (uriType) {
            case LISTINGS:
                rowsDeleted = listingsDatabase.delete(ListingsTable.LISTINGS_TABLE, selection, selectionArgs);
                break;
            case LISTINGS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = listingsDatabase.delete(ListingsTable.LISTINGS_TABLE, ListingsTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsDeleted = listingsDatabase.delete(ListingsTable.LISTINGS_TABLE, ListingsTable.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = uriMatcher.match(uri);
        listingsDatabase = listingsTableHelper.getWritableDatabase();
        int rowsUpdated = 0;

        switch (uriType) {
            case LISTINGS:
                rowsUpdated = listingsDatabase.update(ListingsTable.LISTINGS_TABLE, values, selection, selectionArgs);
                break;
            case LISTINGS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = listingsDatabase.update(ListingsTable.LISTINGS_TABLE, values, ListingsTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsUpdated = listingsDatabase.update(ListingsTable.LISTINGS_TABLE, values, ListingsTable.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private void checkColumns(String[] projection) {
        String[] available = {
                ListingsTable.COLUMN_ID,
                ListingsTable.COLUMN_LISTING_ID,
                ListingsTable.COLUMN_NAME,
                ListingsTable.COLUMN_STREET,
                ListingsTable.COLUMN_CITY,
                ListingsTable.COLUMN_MERCHANT_URL,
                ListingsTable.COLUMN_LATITUDE,
                ListingsTable.COLUMN_LONGITUDE
        };

        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));

            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection!");
            }
        }
    }
}
