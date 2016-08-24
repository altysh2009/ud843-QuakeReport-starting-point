package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;


public class loader extends AsyncTaskLoader<ArrayList<custom_Earth>> {
    String murl;
   static boolean t = false;
    //private static final String USGS_REQUEST_URL =
      //      "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-08-14&endtime=2016-08-15";
    ArrayList<custom_Earth> earthquake = new ArrayList<custom_Earth>();

    public loader(Context context, String url) {
        super(context);
        murl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<custom_Earth> loadInBackground() {
        if (murl == null)
            return null;
        earthquake = Utilts.fetchEarthquakeData(murl);
        return earthquake;
    }
    public static void change (){t = true;}

}