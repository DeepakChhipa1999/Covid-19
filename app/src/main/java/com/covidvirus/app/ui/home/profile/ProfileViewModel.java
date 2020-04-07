package com.covidvirus.app.ui.home.profile;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.covidvirus.app.data.network.model.CountryDataModel;
import com.covidvirus.app.data.network.services.DataService;
import com.covidvirus.app.ui.base.BaseViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends BaseViewModel {

    private static final String TAG = "MainViewModel";
    private DataService mDataService;
    private MutableLiveData<CountryDataModel> mCountryData;


    ProfileViewModel(DataService mDataService){
        this.mDataService = mDataService;
        mCountryData = new MutableLiveData<>();
    }


    MutableLiveData<CountryDataModel> getCountryData(){
        return mCountryData;
    }


    void loadCountryData(String country){
        mDataService.getDataApi().getDataByCountry(country).enqueue(new CountryDataCallback());
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
}

