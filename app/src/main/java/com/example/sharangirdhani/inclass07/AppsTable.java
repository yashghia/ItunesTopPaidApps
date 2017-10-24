/*
    Assignment: InClass07
    Group #7

    Sharan Girdhani
    Yash Ghia
    Dinesh Kota
 */

package com.example.sharangirdhani.inclass07;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by sharangirdhani on 10/23/17.
 */

public class AppsTable {
    static final String TABLE_NAME = "filter";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_LARGE_IMAGE = "thumb_url";
    static final String COLUMN_PRICE = "price";

    public static void createTable(SQLiteDatabase db)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + TABLE_NAME + " (");
        sb.append(COLUMN_ID + " integer primary key autoincrement, ");
        sb.append(COLUMN_NAME + " text not null, ");
        sb.append(COLUMN_LARGE_IMAGE + " text not null, ");
        sb.append(COLUMN_PRICE + " real DEFAULT 0");
        sb.append(");");
        try
        {
            db.execSQL(sb.toString());
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public static void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        AppsTable.createTable(db);
    }
}
