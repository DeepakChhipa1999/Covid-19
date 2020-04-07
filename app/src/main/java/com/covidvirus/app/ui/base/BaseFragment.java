package com.covidvirus.app.ui.base;

import android.content.Context;
import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;



public abstract class BaseFragment<T extends ViewModel> extends Fragment  {
    private static final String TAG = "BaseFragment";
    private T viewModel;
    public abstract T getViewModel();


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = getViewModel();
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }

}
