package com.covidvirus.app.data;

import android.content.SharedPreferences;

import com.covidvirus.app.App;
import com.covidvirus.app.data.network.services.DataService;
import com.covidvirus.app.data.network.services.location.LocationService;

import static android.content.Context.MODE_PRIVATE;

public class DataManager {

    private static DataManager mInstance;
    private SharedPreferences sharedPreferences;
    public static final String MY_PREFS_NAME = "covid_pref";
    public static final String COUNTRY_KEY = "county";
    public static final String RUN_COUNT_KEY = "run_count";
    public static final int MAX_COUNT = 5;

    private DataManager(){
           sharedPreferences = App.getInstance().getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
    }

    public static synchronized DataManager getInstance(){
        return mInstance == null ? new DataManager() : mInstance;
    }

    public void setDefaultCountry(String country){
        sharedPreferences.edit()
                .putString(COUNTRY_KEY, country)
                .apply();
    }

    public String getDefaultCountry(){
        return sharedPreferences.getString(COUNTRY_KEY, null);
    }

    public void setRunCount(){
        int count = getRunCount();
        if (count == MAX_COUNT || count > MAX_COUNT) count = 0;
        else count++;
        sharedPreferences.edit()
                .putInt(RUN_COUNT_KEY, count)
                .apply();
    }

    public int getRunCount(){
        return sharedPreferences.getInt(RUN_COUNT_KEY, 0);
    }

    public DataService getDataService(){
        return DataService.getInstance();
    }

    public LocationService getLocationService(){
        return LocationService.getInstance();
    }

}
