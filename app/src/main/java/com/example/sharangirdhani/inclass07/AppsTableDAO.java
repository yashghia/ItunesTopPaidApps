/*
    Assignment: InClass07
    Group #7

    Sharan Girdhani
    Yash Ghia
    Dinesh Kota
 */

package com.example.sharangirdhani.inclass07;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sharangirdhani on 10/23/17.
 */

public class AppsTableDAO {
    private SQLiteDatabase db;

    public AppsTableDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long addITunesApp(ITunesApp iTunesApp)
    {
        ContentValues values = new ContentValues();
        values.put(AppsTable.COLUMN_NAME,iTunesApp.getAppName());
        values.put(AppsTable.COLUMN_LARGE_IMAGE,iTunesApp.getAppLargeIcon());
        values.put(AppsTable.COLUMN_PRICE,iTunesApp.getAppPrice());

        return db.insert(AppsTable.TABLE_NAME,null,values);
    }

    public List<ITunesApp> getAll()
    {
        List<ITunesApp> articlesList = new ArrayList<>();

        Cursor c = db.query(true,AppsTable.TABLE_NAME,new String[]{
                AppsTable.COLUMN_ID,
                AppsTable.COLUMN_NAME,
                AppsTable.COLUMN_LARGE_IMAGE,
                AppsTable.COLUMN_PRICE
        },null,null,null,null,null,null);

        if (c != null && c.moveToFirst()){
            do {
                articlesList.add(buildITunesAppFromCursor(c));
            } while (c.moveToNext());

            if(!c.isClosed()){
                c.close();
            }
        }
        return articlesList;
    }

    public boolean delete(ITunesApp iTunesApp) {
        StringBuilder sb = new StringBuilder();
        sb.append(AppsTable.COLUMN_ID + " = ? ");

        return (db.delete(AppsTable.TABLE_NAME,sb.toString(),new String[]{String.valueOf(iTunesApp.get_id())}) > 0);
    }

    private ITunesApp buildITunesAppFromCursor(Cursor cursor)
    {
        ITunesApp app = new ITunesApp();
        app.set_id(cursor.getLong(0));
        app.setAppName(cursor.getString(1));
        app.setAppLargeIcon(cursor.getString(2));
        app.setAppPrice(cursor.getDouble(3));
        return app;
    }
}
