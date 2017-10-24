/*
    Assignment: InClass07
    Group #7

    Sharan Girdhani
    Yash Ghia
    Dinesh Kota
 */
package com.example.sharangirdhani.inclass07;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sharangirdhani on 10/23/17.
 */

public class AppsDatabaseOpenHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "filter.db";
    private static int DATABASE_VERSION = 2;


    public AppsDatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        AppsTable.createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        AppsTable.upgradeTable(db,oldVersion,newVersion);
    }
}
