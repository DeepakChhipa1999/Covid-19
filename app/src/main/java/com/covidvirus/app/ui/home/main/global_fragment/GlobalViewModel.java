package com.covidvirus.app.ui.home.main.global_fragment;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.covidvirus.app.data.network.model.GlobalDataModel;
import com.covidvirus.app.data.network.services.DataService;
import com.covidvirus.app.ui.base.BaseViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GlobalViewModel extends BaseViewModel {

    private static final String TAG = "GlobalFragmentViewModel";
    private DataService mDataService;
    private MutableLiveData<GlobalDataModel> mGlobalData;

    GlobalViewModel(DataService mDataService){
        this.mDataService = mDataService;
        mGlobalData = new MutableLiveData<>();
    }


    MutableLiveData<GlobalDataModel> getGlobalData(){
        return mGlobalData;
    }

    void loadGlobalData(){
        mDataService.getDataApi().getGlobalData().enqueue(new GlobalDataCallback());
    }

    private void setGlobalData(GlobalDataModel mGlobalData){
        this.mGlobalData.postValue(mGlobalData);
    }


    private class GlobalDataCallback implements Callback<GlobalDataModel> {
        @Override
        public void onResponse(@NonNull Call<GlobalDataModel> call, @NonNull Response<GlobalDataModel> response) {

            if (response.body() != null)
                setGlobalData(response.body());
        }
        @Override
        public void onFailure(Call<GlobalDataModel> call, Throwable t) {
            Log.e(TAG, "onFailure: "+ t );
        }
    }

}

