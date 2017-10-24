/*
    Assignment: InClass07
    Group #7

    Sharan Girdhani
    Yash Ghia
    Dinesh Kota
 */

package com.example.sharangirdhani.inclass07;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sharangirdhani on 10/23/17.
 */

public class RecycleViewAppListAdapter extends RecyclerView.Adapter<RecycleViewAppListAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ITunesApp> appArrayList;
    private IAppListner mAppListner;
    public AppsDatabaseManager dm;

    public RecycleViewAppListAdapter(Context mContext, ArrayList<ITunesApp> appArrayList, IAppListner mAppListner) {

        this.mContext = mContext;
        this.appArrayList = appArrayList;
        this.mAppListner = mAppListner;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView textName;
        public ImageView appImage ;
        public TextView appPrice;
        public ImageButton deleteApp;
        public ImageView priceImage ;
        public TextView empty;
        public ViewHolder(View itemView) {
            super(itemView);

            textName = (TextView) itemView.findViewById(R.id.textViewrecycleAppname);
            appPrice = (TextView) itemView.findViewById(R.id.textViewrecycleappPrice);
            appImage = (ImageView) itemView.findViewById(R.id.imageViewRecycleAppIcon);
            priceImage = (ImageView) itemView.findViewById(R.id.imageViewRecycleAppPriceIcon);
            empty = (TextView) itemView.findViewById(R.id.empty);
            deleteApp = (ImageButton) itemView.findViewById(R.id.imageButtonDeleteItem);
            deleteApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    ITunesApp iTunesApp = appArrayList.get(position);

                    dm = new AppsDatabaseManager(mContext);
                    dm.delete(iTunesApp);
                    MainActivity.appList.add(iTunesApp);

                    appArrayList.remove(position);
                    mAppListner.updateFiltertedList(appArrayList);
                }
            });
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View itemView = layoutInflater.inflate(R.layout.item_row_recycleview,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(getItemCount()==0){
            holder.empty.setText("There is no filtered apps to display.");
        }
        ITunesApp currentApp = appArrayList.get(position);
        holder.textName.setText(currentApp.getAppName());
        holder.appPrice.setText(String.valueOf("USD " + currentApp.getAppPrice()));
        Picasso.with(mContext).load(currentApp.getAppLargeIcon()).into(holder.appImage);
        double appPrice = currentApp.getAppPrice();
        if(appPrice <= 1.99)
        {
            Picasso.with(mContext).load(R.drawable.price_low).into(holder.priceImage);
        }
        else if(appPrice >= 2.00 && appPrice <= 5.99)
        {
            Picasso.with(mContext).load(R.drawable.price_medium).into(holder.priceImage);
        }
        else if(appPrice > 6.00) {

            Picasso.with(mContext).load(R.drawable.price_high).into(holder.priceImage);
        }
    }

    @Override
    public int getItemCount() {
        return appArrayList.size();
    }

    interface IAppListner
    {
        void updateFiltertedList(ArrayList<ITunesApp> filtertedList);
    }
}
