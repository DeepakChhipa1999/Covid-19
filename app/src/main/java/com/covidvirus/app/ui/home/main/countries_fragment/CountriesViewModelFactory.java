package com.covidvirus.app.ui.home.main.countries_fragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.covidvirus.app.data.network.services.DataService;


public class CountriesViewModelFactory implements ViewModelProvider.Factory {

    private final DataService mDataService;

    public CountriesViewModelFactory(DataService mDataService){
        this.mDataService = mDataService;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CountriesViewModel.class)) {
            return (T) new CountriesViewModel(mDataService);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
