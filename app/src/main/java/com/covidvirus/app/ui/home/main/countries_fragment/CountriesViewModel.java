package com.covidvirus.app.ui.home.main.countries_fragment;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.covidvirus.app.data.network.model.CountryDataModel;
import com.covidvirus.app.data.network.services.DataService;
import java.util.List;
import com.covidvirus.app.ui.base.BaseViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountriesViewModel extends BaseViewModel {

    private static final String TAG = "MainViewModel";
    private DataService mDataService;
    private MutableLiveData<List<CountryDataModel>> mCountriesData;
    private MutableLiveData<CountryDataModel> mCountryData;


    CountriesViewModel(DataService mDataService){
        this.mDataService = mDataService;
        mCountriesData = new MutableLiveData<>();
        mCountryData = new MutableLiveData<>();
    }

    MutableLiveData<List<CountryDataModel>> getCountriesData(){

        return mCountriesData;
    }

    MutableLiveData<CountryDataModel> getCountryData(){
        return mCountryData;
    }

    void loadCountryData(String country){
        mDataService.getDataApi().getDataByCountry(country)
                .enqueue(new CountryDataCallback());
    }

    void loadCountriesData(){
        mDataService.getDataApi().getAllData().enqueue(new CountriesDataCallback());
    }

    private void setCountriesData(List<CountryDataModel> mCountriesData){
        this.mCountriesData.postValue(mCountriesData);
    }

    private void setCountryData(CountryDataModel mCountryData){
        this.mCountryData.postValue(mCountryData);
    }

    private class CountryDataCallback implements Callback<CountryDataModel> {
        @Override
        public void onResponse(@NonNull Call<CountryDataModel> call, @NonNull Response<CountryDataModel> response) {

            if (response.body() != null)
                setCountryData(response.body());
        }
        @Override
        public void onFailure(Call<CountryDataModel> call, Throwable t) {
            Log.e(TAG, "onFailure: "+ t );
        }
    }

    private class CountriesDataCallback implements Callback<List<CountryDataModel>> {
        @Override
        public void onResponse(@NonNull Call<List<CountryDataModel>> call, @NonNull Response<List<CountryDataModel>> response) {

            if (response.body() != null)
                setCountriesData(response.body());
        }
        @Override
        public void onFailure(Call<List<CountryDataModel>> call, Throwable t) {
            Log.e(TAG, "onFailure: "+ t );
        }
    }


}

