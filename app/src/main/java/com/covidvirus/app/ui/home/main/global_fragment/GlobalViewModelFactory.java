package com.covidvirus.app.ui.home.main.global_fragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.covidvirus.app.data.network.services.DataService;

public class GlobalViewModelFactory implements ViewModelProvider.Factory {

    private final DataService mDataService;

    public GlobalViewModelFactory(DataService mDataService){
        this.mDataService = mDataService;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(GlobalViewModel.class)) {
            return (T) new GlobalViewModel(mDataService);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
