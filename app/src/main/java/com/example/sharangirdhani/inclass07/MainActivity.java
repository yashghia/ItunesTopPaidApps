/*
    Assignment: InClass07
    Group #7

    Sharan Girdhani
    Yash Ghia
    Dinesh Kota
 */

package com.example.sharangirdhani.inclass07;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements GetDataAsyncTask.IData,RecycleViewAppListAdapter.IAppListner {

    ProgressDialog loadingApps;
    TextView empty;
    ImageButton reloadButton;
    static ListView listViewApps;
    Switch switchSortingOrder;
    public static ArrayList<ITunesApp> appList ;
    AppsDatabaseManager dm;
    ArrayList<ITunesApp> filteredList = null;
    RecyclerView recycleViewList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("iTunes Top Paid Apps");
        reloadButton = (ImageButton) findViewById(R.id.imageButton);
        empty = (TextView) findViewById(R.id.empty);
        recycleViewList = (RecyclerView) findViewById(R.id.recycleViewApps);
        if(filteredList==null||filteredList.isEmpty()){
            empty.setVisibility(View.VISIBLE);
            recycleViewList.setVisibility(View.INVISIBLE);
        }
        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingApps = new ProgressDialog(MainActivity.this);
                loadingApps.setTitle("Loading Apps");
                loadingApps.setCancelable(false);
                loadingApps.show();
                new GetDataAsyncTask(MainActivity.this).execute();
            }
        });
        listViewApps = (ListView) findViewById(R.id.listViewApps);
        listViewApps.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d("List View","Clicked");
                ITunesApp appToAdd = appList.get(position);
                appList.remove(position);

                appListAdapter adapter = new appListAdapter(MainActivity.this,R.layout.item_row_listview,appList);
                adapter.notifyDataSetChanged();
                listViewApps.setAdapter(adapter);


                dm = new AppsDatabaseManager(MainActivity.this);
                dm.addITunesApp(appToAdd);
                filteredList = (ArrayList<ITunesApp>) dm.getAll();
                if(!filteredList.isEmpty()){
                    empty.setVisibility(View.INVISIBLE);
                    recycleViewList.setVisibility(View.VISIBLE);
                }

                RecycleViewAppListAdapter recycleAdapter = new RecycleViewAppListAdapter(MainActivity.this,filteredList, MainActivity.this);
                recycleAdapter.notifyDataSetChanged();
                recycleViewList.setAdapter(recycleAdapter);
                recycleViewList.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));

                return false;
            }
        });
        switchSortingOrder = (Switch) findViewById(R.id.switchSorting);
        switchSortingOrder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    switchSortingOrder.setText("Ascending");
                    Collections.sort(appList, new Comparator<ITunesApp>() {
                        @Override
                        public int compare(ITunesApp lhs, ITunesApp rhs) {
                            if(lhs.getAppPrice() > rhs.getAppPrice())
                                return 1;
                            else
                                return -1;
                        }
                    });
                }
                else
                {
                    switchSortingOrder.setText("Descending");

                    Collections.sort(appList, new Comparator<ITunesApp>() {
                        @Override
                        public int compare(ITunesApp lhs, ITunesApp rhs) {
                            if(lhs.getAppPrice() > rhs.getAppPrice())
                                return -1;
                            else
                                return 1;
                        }
                    });
                }
                appListAdapter adapter = new appListAdapter(MainActivity.this,R.layout.item_row_listview,appList);
                adapter.notifyDataSetChanged();
                listViewApps.setAdapter(adapter);
            }
        });
        loadingApps = new ProgressDialog(this);
        loadingApps.setTitle("Loading Apps");
        loadingApps.setCancelable(false);
        loadingApps.show();
        new GetDataAsyncTask(this).execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dm = new AppsDatabaseManager(MainActivity.this);
        filteredList = (ArrayList<ITunesApp>) dm.getAll();

        RecycleViewAppListAdapter recycleAdapter = new RecycleViewAppListAdapter(MainActivity.this,filteredList, MainActivity.this);
        recycleAdapter.notifyDataSetChanged();
        recycleViewList.setAdapter(recycleAdapter);
        recycleViewList.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));

        if(!filteredList.isEmpty()){
            empty.setVisibility(View.INVISIBLE);
            recycleViewList.setVisibility(View.VISIBLE);
        }
        else {
            empty.setVisibility(View.VISIBLE);
            recycleViewList.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void setUpAppData(ArrayList<ITunesApp> iTunesApps) {
        loadingApps.dismiss();

        Collections.sort(iTunesApps, new Comparator<ITunesApp>() {
            @Override
            public int compare(ITunesApp lhs, ITunesApp rhs) {
                if(lhs.getAppPrice() > rhs.getAppPrice())
                    return 1;
                else
                    return -1;
            }
        });
        this.appList = iTunesApps;
        appListAdapter adapter = new appListAdapter(MainActivity.this,R.layout.item_row_listview,this.appList);
        adapter.notifyDataSetChanged();
        listViewApps.setAdapter(adapter);
    }

    public void sortList() {
        if(switchSortingOrder.isChecked()) {
            Collections.sort(appList, new Comparator<ITunesApp>() {
                @Override
                public int compare(ITunesApp lhs, ITunesApp rhs) {
                    if(lhs.getAppPrice() > rhs.getAppPrice())
                        return 1;
                    else
                        return -1;
                }
            });
        }
        else {
            Collections.sort(appList, new Comparator<ITunesApp>() {
                @Override
                public int compare(ITunesApp lhs, ITunesApp rhs) {
                    if(lhs.getAppPrice() > rhs.getAppPrice())
                        return -1;
                    else
                        return 1;
                }
            });
        }
    }
    @Override
    public void updateFiltertedList(ArrayList<ITunesApp> updatedList) {

        appListAdapter adapter = new appListAdapter(MainActivity.this,R.layout.item_row_listview,appList);
        adapter.notifyDataSetChanged();
        listViewApps.setAdapter(adapter);

        RecycleViewAppListAdapter recycleAdapter = new RecycleViewAppListAdapter(MainActivity.this,updatedList, MainActivity.this);
        recycleAdapter.notifyDataSetChanged();
        recycleViewList.setAdapter(recycleAdapter);
        recycleViewList.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));

        if(!filteredList.isEmpty()){
            empty.setVisibility(View.INVISIBLE);
            recycleViewList.setVisibility(View.VISIBLE);
        }
        else {
            empty.setVisibility(View.VISIBLE);
            recycleViewList.setVisibility(View.INVISIBLE);
        }
        sortList();
    }
}
