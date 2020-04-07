package com.covidvirus.app.ui.home.main.countries_fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.covidvirus.app.CountryClickActivity;
import com.covidvirus.app.R;
import com.covidvirus.app.data.DataManager;
import com.covidvirus.app.data.network.model.CountryDataModel;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


import com.covidvirus.app.ui.base.BaseFragment;

public class CountriesFragment extends BaseFragment<CountriesViewModel> {
    private static final String TAG = "CountriesFragment";
    private CountriesViewModel viewModel;
    @BindView(R.id.country_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.searvh_view)
    EditText searchView;
    private RecyclerAdapter recyclerAdapter;

    public CountriesFragment() {
    }

    @Override
    public CountriesViewModel getViewModel() {
        CountriesViewModelFactory factory = new CountriesViewModelFactory(DataManager.getInstance().getDataService());
        return ViewModelProviders.of(this, factory).get(CountriesViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_countries, container, false);
        ButterKnife.bind(this, view);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                recyclerAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = getViewModel();
        setupRecyclerView();
        showCountries();
    }

    private void setupRecyclerView() {
        recyclerAdapter = new RecyclerAdapter(new ItemOnClick());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(recyclerAdapter);
        mRecyclerView.addItemDecoration(new RecyclerItemDecoration(5));

    }

    private void showCountries() {
        viewModel.getCountriesData().observe(this, new CountriesDataObserver());
        viewModel.loadCountriesData();
    }

    private class CountriesDataObserver implements Observer<List<CountryDataModel>> {
        @Override
        public void onChanged(List<CountryDataModel> countries) {
            if (countries == null) return;
            recyclerAdapter.addCountriesData(countries);

        }
    }

    private class ItemOnClick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            int itemPosition = mRecyclerView.getChildLayoutPosition(view);
            CountryDataModel item = recyclerAdapter.getCountryData(itemPosition);
            Intent intent = new Intent(getActivity(), CountryClickActivity.class);
            intent.putExtra("countryDetail", item);
            startActivity(intent);
            //showDetailDialog(item.getCountry());
        }
    }

    private void showDetailDialog(String country){
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View dialogView = layoutInflater.inflate(R.layout.detail_dialog,null);
        cleanDialogView(dialogView);
        AlertDialog alertDialog = new  AlertDialog.Builder(getActivity()).create();
        alertDialog.setView(dialogView);
        alertDialog.setCancelable(true);
        viewModel.loadCountryData(country);

        viewModel.getCountryData().observe(this, new Observer<CountryDataModel>() {
            @Override
            public void onChanged(CountryDataModel countryDataModel) {
                setupDialogView(dialogView, countryDataModel );
            }
        });

        dialogView.findViewById(R.id.aboutCancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void setupDialogView(View dialogView, CountryDataModel country){
        if (country == null) return;
        ((TextView)dialogView.findViewById(R.id.countryTitle)).setText(country.getCountry());
        ((TextView)dialogView.findViewById(R.id.totalCaseValue)).setText(String.valueOf(country.getNbrCases()));
        ((TextView)dialogView.findViewById(R.id.activeCaseValue)).setText(String.valueOf(country.getNbrActiveCases()));
        ((TextView)dialogView.findViewById(R.id.todayCaseValue)).setText(String.valueOf(country.getTodayCases()));
        ((TextView)dialogView.findViewById(R.id.totalDeathValue)).setText(String.valueOf(country.getNbrDeath()));
        ((TextView)dialogView.findViewById(R.id.todayDeathValue)).setText(String.valueOf(country.getTodayDeaths()));
        ((TextView)dialogView.findViewById(R.id.recoverValue)).setText(String.valueOf(country.getNbrRecovered()));
        ((ProgressBar)dialogView.findViewById(R.id.progressBarDetail)).setVisibility(View.GONE);
    }

    private void cleanDialogView(View dialogView){
        ((TextView)dialogView.findViewById(R.id.countryTitle)).setText("");
        ((TextView)dialogView.findViewById(R.id.totalCaseValue)).setText("");
        ((TextView)dialogView.findViewById(R.id.activeCaseValue)).setText("");
        ((TextView)dialogView.findViewById(R.id.todayCaseValue)).setText("");
        ((TextView)dialogView.findViewById(R.id.totalDeathValue)).setText("");
        ((TextView)dialogView.findViewById(R.id.todayDeathValue)).setText("");
        ((TextView)dialogView.findViewById(R.id.recoverValue)).setText("");
        ((ProgressBar)dialogView.findViewById(R.id.progressBarDetail)).setVisibility(View.VISIBLE);
    }


}
