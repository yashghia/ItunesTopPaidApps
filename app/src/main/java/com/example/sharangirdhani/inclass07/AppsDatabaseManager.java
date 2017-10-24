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

import java.util.List;

/**
 * Created by sharangirdhani on 10/23/17.
 */

public class AppsDatabaseManager {
    private Context mContext;
    private SQLiteDatabase db;
    private AppsTableDAO dao;

    public AppsDatabaseManager(Context mContext) {
        this.mContext = mContext;
        AppsDatabaseOpenHelper databaseOpenHelper = new AppsDatabaseOpenHelper(this.mContext);
        db = databaseOpenHelper.getWritableDatabase();
        dao = new AppsTableDAO(db);
    }

    public long addITunesApp(ITunesApp iTunesApp)
    {
        return dao.addITunesApp(iTunesApp);
    }

    public List<ITunesApp> getAll()
    {
        return dao.getAll();
    }

    public boolean delete(ITunesApp iTunesApp) {
        return dao.delete(iTunesApp);
    }
}
