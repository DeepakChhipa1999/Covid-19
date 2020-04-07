package com.covidvirus.app.data.network.services;

import com.covidvirus.app.data.network.model.CountryDataModel;
import com.covidvirus.app.data.network.model.GlobalDataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DataApi {

    @GET("/all")
    Call<GlobalDataModel> getGlobalData();

    @GET("/countries")
    Call<List<CountryDataModel>> getAllData();

    @GET("/countries/{country}")
    Call<CountryDataModel> getDataByCountry(@Path("country") String country);

}
