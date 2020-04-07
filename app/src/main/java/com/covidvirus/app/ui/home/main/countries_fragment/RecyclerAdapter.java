package com.covidvirus.app.ui.home.main.countries_fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.covidvirus.app.data.network.model.CountryDataModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.covidvirus.app.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MainViewHolder> implements Filterable {

    private List<CountryDataModel> mCountriesData;
    private List<CountryDataModel> mCountriesDataFiltered;
    private View.OnClickListener onClickListener;

    public RecyclerAdapter(View.OnClickListener listener) {

        mCountriesData = new ArrayList<>();
        mCountriesDataFiltered = new ArrayList<>();
        onClickListener = listener;
    }

    public void addCountryData(CountryDataModel mCountryData, int position) {
        this.mCountriesData.add(position, mCountryData);
        this.mCountriesDataFiltered.add(position, mCountryData);
        notifyDataSetChanged();
    }

    public void addCountriesData(List<CountryDataModel> mCountriesData) {
        this.mCountriesData.addAll(mCountriesData);
        this.mCountriesDataFiltered.addAll(mCountriesData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, parent, false);
        mView.setOnClickListener(onClickListener);
        return new MainViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mCountriesDataFiltered == null ? 0 : mCountriesDataFiltered.size();
    }

    public CountryDataModel getCountryData(int position) {
        return mCountriesDataFiltered.get(position);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    mCountriesDataFiltered = mCountriesData;
                } else {
                    List<CountryDataModel> filteredList = new ArrayList<>();
                    for (CountryDataModel row : mCountriesData) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getCountry().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    mCountriesDataFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mCountriesDataFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mCountriesDataFiltered = (ArrayList<CountryDataModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_title)
        TextView countryName;
        @BindView(R.id.total_cases_value)
        TextView totalCases;
        @BindView(R.id.death_cases_value)
        TextView deathCases;
        @BindView(R.id.recover_cases_value)
        TextView recoverCases;


        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(int position) {
            CountryDataModel mCountryData = getCountryData(position);
            setCountryName(mCountryData.getCountry());
            setTotalCases(String.valueOf(mCountryData.getNbrCases()));
            setDeathCases(String.valueOf(mCountryData.getNbrDeath()));
            setRecoverCases(String.valueOf(mCountryData.getNbrRecovered()));
        }

        public void setCountryName(String countryName) {
            this.countryName.setText(countryName);
        }

        public void setTotalCases(String totalCases) {
            this.totalCases.setText(totalCases);
        }

        public void setDeathCases(String deathCases) {
            this.deathCases.setText(deathCases);
        }

        public void setRecoverCases(String recoverCases) {
            this.recoverCases.setText(recoverCases);
        }
    }


}
