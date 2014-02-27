package com.tddrampup.contentProviders;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.tddrampup.databases.ListingsTable;
import com.tddrampup.databases.PreviousQueryTable;
import com.tddrampup.databases.YellowDatabaseHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by WX009-PC on 2/26/14.
 */
public class YellowContentProvider extends ContentProvider {

    // Database
    private SQLiteDatabase yellowDatabase;

    // Helper
    private YellowDatabaseHelper yellowDatabaseHelper;

    // Table Names
    private static final String LISTINGS_TABLE = ListingsTable.LISTINGS_TABLE;
    private static final String PREVIOUS_QUERY_TABLE = PreviousQueryTable.PREVIOUS_QUERY_TABLE;

    // Content provider fields
    private static final String AUTHORITY = "com.tddrampup.contentProviders";
    private static final String LISTINGS_PATH = "listings";
    private static final String QUERY_PATH = "previousQuery";

    // URIs
    public static final Uri CONTENT_URI_LISTINGS = Uri.parse("content://" + AUTHORITY + "/" + LISTINGS_PATH);
    public static final Uri CONTENT_URI_QUERY = Uri.parse("content://" + AUTHORITY + "/" + QUERY_PATH);

    // URI integer values
    private static final int LISTINGS = 1;
    private static final int LISTINGS_ID = 2;
    private static final int PREVIOUS_QUERY = 3;
    private static final int PREVIOUS_QUERY_ID = 4;

//    // Content types
//    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/listings";
//    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/listing";

    // Projection map
    private static HashMap<String, String> projectionMap; // TODO: why???!!!!

    // Maps content URI to integer values
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, LISTINGS_PATH, LISTINGS);
        uriMatcher.addURI(AUTHORITY, LISTINGS_PATH + "/#", LISTINGS_ID);
        uriMatcher.addURI(AUTHORITY, QUERY_PATH, PREVIOUS_QUERY);
        uriMatcher.addURI(AUTHORITY, QUERY_PATH + "/#", PREVIOUS_QUERY_ID);
    }

    @Override
    public boolean onCreate() {
        Log.d("DATABASE", "MAKING THE TABLEZ!!!");
        yellowDatabaseHelper = new YellowDatabaseHelper(getContext());
        yellowDatabase = yellowDatabaseHelper.getWritableDatabase();
        if (yellowDatabase == null) {
           return false;
        } else {
            return true;
        }
    }

    @Override
    public String getType(Uri uri) {
        int uriType = uriMatcher.match(uri);
        switch (uriType) {
            case LISTINGS:
                return ""; //TODO
            case LISTINGS_ID:
                return ""; //TODO
            case PREVIOUS_QUERY:
                return ""; //TODO
            case PREVIOUS_QUERY_ID:
                return ""; //TODO !vogella
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Using SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        checkColumns(projection);

        int uriType = uriMatcher.match(uri);
        switch (uriType) {
            case LISTINGS:
                queryBuilder.setTables(LISTINGS_TABLE);
                queryBuilder.setProjectionMap(projectionMap);
                break;
            case LISTINGS_ID:
                queryBuilder.setTables(LISTINGS_TABLE);
                // adding the ID to the original query
                queryBuilder.appendWhere(ListingsTable.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;
            case PREVIOUS_QUERY:
                queryBuilder.setTables(PREVIOUS_QUERY_TABLE);
                queryBuilder.setProjectionMap(projectionMap);
                break;
            case PREVIOUS_QUERY_ID:
                queryBuilder.setTables(PREVIOUS_QUERY_TABLE);
                // adding the ID to the original query
                queryBuilder.appendWhere(PreviousQueryTable.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        Cursor cursor = queryBuilder.query(yellowDatabase, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = uriMatcher.match(uri);
        long id = 0;

        switch (uriType) {
            case LISTINGS:
                id = yellowDatabase.insert(LISTINGS_TABLE, null, values);
                break;
            case PREVIOUS_QUERY:
                id = yellowDatabase.insert(PREVIOUS_QUERY_TABLE, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        switch (uriType) {
            case LISTINGS:
                return Uri.parse(LISTINGS_PATH + "/" + id);
            case PREVIOUS_QUERY:
                return Uri.parse(QUERY_PATH + "/" + id);
            default:
                return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = uriMatcher.match(uri);
        int rowsDeleted = 0;
        String id;

        switch (uriType) {
            case LISTINGS:
                rowsDeleted = yellowDatabase.delete(LISTINGS_TABLE, selection, selectionArgs);
                break;
            case LISTINGS_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = yellowDatabase.delete(LISTINGS_TABLE, ListingsTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsDeleted = yellowDatabase.delete(LISTINGS_TABLE, ListingsTable.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;
            case PREVIOUS_QUERY:
                rowsDeleted = yellowDatabase.delete(PREVIOUS_QUERY_TABLE, selection, selectionArgs);
                break;
            case PREVIOUS_QUERY_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = yellowDatabase.delete(PREVIOUS_QUERY_TABLE, PreviousQueryTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsDeleted = yellowDatabase.delete(PREVIOUS_QUERY_TABLE, PreviousQueryTable.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
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
        int rowsUpdated = 0;
        String id;

        switch (uriType) {
            case LISTINGS:
                rowsUpdated = yellowDatabase.update(LISTINGS_TABLE, values, selection, selectionArgs);
                break;
            case LISTINGS_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = yellowDatabase.update(LISTINGS_TABLE, values, ListingsTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsUpdated = yellowDatabase.update(LISTINGS_TABLE, values, ListingsTable.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;
            case PREVIOUS_QUERY:
                rowsUpdated = yellowDatabase.update(PREVIOUS_QUERY_TABLE, values, selection, selectionArgs);
                break;
            case PREVIOUS_QUERY_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = yellowDatabase.update(PREVIOUS_QUERY_TABLE, values, PreviousQueryTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsUpdated = yellowDatabase.update(PREVIOUS_QUERY_TABLE, values, PreviousQueryTable.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
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
                ListingsTable.COLUMN_LONGITUDE,
                PreviousQueryTable.COLUMN_ID,
                PreviousQueryTable.COLUMN_WHAT,
                PreviousQueryTable.COLUMN_WHERE
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
