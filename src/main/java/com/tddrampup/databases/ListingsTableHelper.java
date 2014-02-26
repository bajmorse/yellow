package com.tddrampup.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by WX009-PC on 2/26/14.
 */
public class ListingsTableHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "yellowDatabase";
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
}
