/*
    Assignment: InClass07
    Group #7

    Sharan Girdhani
    Yash Ghia
    Dinesh Kota
 */

package com.example.sharangirdhani.inclass07;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by sharangirdhani on 10/23/17.
 */

public class GetDataAsyncTask extends AsyncTask<Void,Void,ArrayList<ITunesApp>> {

    public static final String apiURL = "https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json";
    private ArrayList<ITunesApp> iTunesApps ;
    private IData activity ;

    public GetDataAsyncTask(IData activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<ITunesApp> doInBackground(Void... params) {

        try {
            URL url = new URL(apiURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) !=null)
            {
                sb.append(line);
            }

            return parseJSON(sb.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<ITunesApp> parseJSON(String JSONResponse)
    {
        iTunesApps = new ArrayList<>();
        try {
            JSONObject rootJSON = new JSONObject(JSONResponse);
            JSONObject feedJSON= rootJSON.getJSONObject("feed");
            JSONArray appsResults = feedJSON.getJSONArray("entry");
            for(int i=0; i<appsResults.length();i++)
            {
                JSONObject currentApp = appsResults.getJSONObject(i);
                String appName = currentApp.getJSONObject("im:name").getString("label");
                Log.d("appName", appName);
                JSONObject appPriceObject = currentApp.getJSONObject("im:price").getJSONObject("attributes");
                double appPrice = appPriceObject.getDouble("amount");
                Log.d("App Price", String.valueOf(appPrice));
                JSONArray appImageUrls = currentApp.getJSONArray("im:image");
                String appIconUrl = "";
                String appLargeIconUrl = "";
                for(int imageIndex =0; imageIndex<appImageUrls.length();imageIndex++)
                {
                    JSONObject currentImage = appImageUrls.getJSONObject(imageIndex);
                    if(currentImage.getJSONObject("attributes").getInt("height") == 53)
                    {
                        appIconUrl = currentImage.getString("label");
                    }
                    else if(currentImage.getJSONObject("attributes").getInt("height") == 100)
                    {
                        appLargeIconUrl = currentImage.getString("label");
                    }
                }

                ITunesApp app = new ITunesApp(appName,appIconUrl,appLargeIconUrl,appPrice);
                iTunesApps.add(app);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return iTunesApps;
    }
    @Override
    protected void onPostExecute(ArrayList<ITunesApp> iTunesApps) {
        activity.setUpAppData(iTunesApps);
    }

    interface IData
    {
        void setUpAppData(ArrayList<ITunesApp> iTunesApps);
    }
}
