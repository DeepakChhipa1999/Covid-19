package com.covidvirus.app.ui.home.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.covidvirus.app.data.network.services.DataService;


public class ProfileViewModelFactory implements ViewModelProvider.Factory {

    private final DataService mDataService;

    public ProfileViewModelFactory(DataService mDataService){
        this.mDataService = mDataService;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
            return (T) new ProfileViewModel(mDataService);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
