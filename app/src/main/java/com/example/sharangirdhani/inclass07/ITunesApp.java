/*
    Assignment: InClass07
    Group #7

    Sharan Girdhani
    Yash Ghia
    Dinesh Kota
 */

package com.example.sharangirdhani.inclass07;

/**
 * Created by sharangirdhani on 10/23/17.
 */

public class ITunesApp {
    private String appName,appIcon,appLargeIcon;
    private double appPrice;
    private long _id;

    public ITunesApp()
    {
        // default constructor
    }
    public ITunesApp(String appName, String appIcon, String appLargeIcon, double appPrice) {
        this.appName = appName;
        this.appIcon = appIcon;
        this.appLargeIcon = appLargeIcon;
        this.appPrice = appPrice;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getAppLargeIcon() {
        return appLargeIcon;
    }

    public void setAppLargeIcon(String appLargeIcon) {
        this.appLargeIcon = appLargeIcon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    public double getAppPrice() {
        return appPrice;
    }

    public void setAppPrice(double appPrice) {
        this.appPrice = appPrice;
    }
}
