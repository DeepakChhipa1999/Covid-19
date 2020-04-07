package com.covidvirus.app.ui.home;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.covidvirus.app.data.network.model.Location;
import com.covidvirus.app.data.network.services.location.LocationService;
import com.covidvirus.app.ui.base.BaseViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainViewModel extends BaseViewModel {

    private static final String TAG = "MainViewModel";
    private MutableLiveData<Location> location;
    private LocationService locationService;


    MainViewModel(LocationService mDataService){
        this.locationService = mDataService;
        location = new MutableLiveData<>();
    }

    MutableLiveData<Location> getLocationData(){
        return location;
    }


    void loadLocationData(){
        locationService.getLocationApi().getLocationData().enqueue(new LocationDataCallback());
    }

    private void setLocationData(Location location){
        this.location.postValue(location);
    }


    private class LocationDataCallback implements Callback<Location> {
        @Override
        public void onResponse(@NonNull Call<Location> call, @NonNull Response<Location> response) {

            if (response.body() != null)
                setLocationData(response.body());
        }
        @Override
        public void onFailure(Call<Location> call, Throwable t) {
            Log.e(TAG, "onFailure: "+ t );
        }
    }

}

