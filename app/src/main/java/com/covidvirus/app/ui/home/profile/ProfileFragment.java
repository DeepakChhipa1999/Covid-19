package com.covidvirus.app.ui.home.profile;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.covidvirus.app.R;
import com.covidvirus.app.data.DataManager;
import com.covidvirus.app.data.network.model.CountryDataModel;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.covidvirus.app.ui.base.BaseFragment;

public class ProfileFragment extends BaseFragment<ProfileViewModel> {
    private static final String TAG = "ProfileFragment";
    private ProfileViewModel viewModel;
    @BindView(R.id.about)
    ImageView aboutImageView;
    @BindView(R.id.progressBarProfile)
    ProgressBar progressBar;
    @BindView(R.id.countryTitle)
    TextView countryTitle;
    @BindView(R.id.caseItem)
    LinearLayout caseItem;
    @BindView(R.id.deathItem)
    LinearLayout deathItem;
    @BindView(R.id.recoverItem)
    LinearLayout recoverItem;


    public ProfileFragment() {
    }

    @Override
    public ProfileViewModel getViewModel() {
        ProfileViewModelFactory factory = new ProfileViewModelFactory(DataManager.getInstance().getDataService());
        return ViewModelProviders.of(this, factory).get(ProfileViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        setProfileVisibility(View.INVISIBLE);

        return view;
    }

    private void setProfileVisibility(int id) {
        countryTitle.setVisibility(id);
        caseItem.setVisibility(id);
        deathItem.setVisibility(id);
        recoverItem.setVisibility(id);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        aboutImageView.setOnClickListener(new AboutListener());
        viewModel = getViewModel();
        showProfile();
    }

    private void showProfile() {
        viewModel.getCountryData().observe(this, new CountryDataObserver());
        viewModel.loadCountryData(DataManager.getInstance().getDefaultCountry());
    }

    private void showAboutDialog(){
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View dialogView = layoutInflater.inflate(R.layout.about_dialog,null);
        AlertDialog alertDialog = new  AlertDialog.Builder(getActivity()).create();
        alertDialog.setView(dialogView);
        alertDialog.show();

        dialogView.findViewById(R.id.aboutCancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }

    private class CountryDataObserver implements Observer<CountryDataModel> {
        @Override
        public void onChanged(CountryDataModel country) {
            if (country == null) return;
            setCountryName(country.getCountry());
            setTotalCases(String.valueOf(country.getNbrCases()));
            setActiveCases(String.valueOf(country.getNbrActiveCases()));
            setTodayCases(String.valueOf(country.getTodayCases()));
            setTotalDeathCases(String.valueOf(country.getNbrDeath()));
            setTodayDeathCases(String.valueOf(country.getTodayDeaths()));
            setRecoverCases(String.valueOf(country.getNbrRecovered()));

            setProfileVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    private void setCountryName(String countryName) {
        this.countryTitle.setText(countryName);
    }

    private void setTotalCases(String totalCases) {
        ( (TextView) this.caseItem.findViewById(R.id.totalCaseValue)).setText(totalCases);
    }

    private void setActiveCases(String totalCases) {
        ( (TextView) this.caseItem.findViewById(R.id.activeCaseValue)).setText(totalCases);
    }

    private void setTodayCases(String totalCases) {
        ( (TextView) this.caseItem.findViewById(R.id.todayCaseValue)).setText(totalCases);
    }

    private void setTotalDeathCases(String deathCases) {

        ( (TextView) this.deathItem.findViewById(R.id.totalDeathValue)).setText(deathCases);
    }

    private void setTodayDeathCases(String deathCases) {

        ( (TextView) this.deathItem.findViewById(R.id.todayDeathValue)).setText(deathCases);
    }

    private void setRecoverCases(String recoverCases) {

        ( (TextView) this.recoverItem.findViewById(R.id.recoverValue)).setText(recoverCases);
    }


    private class AboutListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick: about");
            showAboutDialog();

        }
    }

}
