package com.covidvirus.app.data.network.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataService {
    private static final String BASE_URL = "https://corona.lmao.ninja";
    private DataApi mDataApi;

    private static DataService mInstance;

    public static DataService getInstance(){
        return mInstance == null ? new DataService() : mInstance;
    }

    private DataService(){
        Retrofit mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        mDataApi = mRetrofit.create(DataApi.class);
    }

    public DataApi getDataApi(){
        return mDataApi;
    }
}
