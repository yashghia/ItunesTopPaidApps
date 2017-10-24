/*
    Assignment: InClass07
    Group #7

    Sharan Girdhani
    Yash Ghia
    Dinesh Kota
 */
package com.example.sharangirdhani.inclass07;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sharangirdhani on 10/23/17.
 */

public class appListAdapter extends ArrayAdapter{
    private Context mContext;
    private List<ITunesApp> iTunesAppList;
    private  int rowResId;


    public appListAdapter(Context context, int resource, List<ITunesApp> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.rowResId = resource;
        this.iTunesAppList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.rowResId,parent,false);
        }

        ITunesApp iTunesApp = iTunesAppList.get(position);
        double appPrice = iTunesApp.getAppPrice();
        ((TextView)convertView.findViewById(R.id.textViewappName)).setText(iTunesApp.getAppName());
        ((TextView)convertView.findViewById(R.id.textViewappPrice)).setText("Price: USD "+ String.valueOf(appPrice));
        ImageView appIcon = (ImageView) convertView.findViewById(R.id.imageViewappLogo);
        ImageView appImagePrice = (ImageView) convertView.findViewById(R.id.imageViewappPrice);
        Picasso.with(mContext).load(iTunesApp.getAppIcon()).into(appIcon);

        if(appPrice <= 1.99)
        {
            Picasso.with(mContext).load(R.drawable.price_low).into(appImagePrice);
        }
        else if(appPrice >= 2.00 && appPrice <= 5.99)
        {
            Picasso.with(mContext).load(R.drawable.price_medium).into(appImagePrice);
        }
        else if(appPrice > 6.00) {

            Picasso.with(mContext).load(R.drawable.price_medium).into(appImagePrice);
        }
        return convertView;
    }
}
