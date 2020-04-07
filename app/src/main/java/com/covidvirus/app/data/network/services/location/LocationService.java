package com.covidvirus.app.data.network.services.location;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationService {
    private static final String BASE_URL = "http://ip-api.com";
    private LocationApi mLocationApi;

    private static LocationService mInstance;

    public static LocationService getInstance(){
        return mInstance == null ? new LocationService() : mInstance;
    }

    private LocationService(){
        Retrofit mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        mLocationApi = mRetrofit.create(LocationApi.class);
    }

    public LocationApi getLocationApi(){
        return mLocationApi;
    }
}
