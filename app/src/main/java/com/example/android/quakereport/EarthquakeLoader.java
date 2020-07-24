package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import static com.example.android.quakereport.EarthquakeActivity.LOG_TAG;

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>>
{
    private String url;

    public EarthquakeLoader(Context context,String url) {
        super(context);
        this.url=url;
    }

    @Override
    public void onStartLoading() {
        Log.e(LOG_TAG,"On start Loading Worked");
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        Log.e(LOG_TAG,"Load in backgroung Worked");
        if (url == null) {
            return null;
        }
        List<Earthquake> earthquakes = QueryUtils.fetchearthquakedata(url);
        return earthquakes;
    }
}
