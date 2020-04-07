package com.covidvirus.app.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.covidvirus.app.data.network.services.location.LocationService;

public class MainViewModelFactory implements ViewModelProvider.Factory {

    private final LocationService locationService;

    public MainViewModelFactory(LocationService locationService){
        this.locationService = locationService;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(locationService);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
