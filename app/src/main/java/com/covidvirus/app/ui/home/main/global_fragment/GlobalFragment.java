package com.covidvirus.app.ui.home.main.global_fragment;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.covidvirus.app.R;
import com.covidvirus.app.data.DataManager;
import com.covidvirus.app.data.network.model.GlobalDataModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.covidvirus.app.ui.base.BaseFragment;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;


public class GlobalFragment extends BaseFragment<GlobalViewModel> {
    private static final String TAG = "GlobalFragment";
    private GlobalViewModel viewModel;
    @BindView(R.id.caseValue)
    TextView totalCases;
    @BindView(R.id.deathValue)
    TextView deathCases;
    @BindView(R.id.recoverValue)
    TextView recoverCases;
    @BindView(R.id.txt_confirmed)
    TextView txtConfirmed;
    @BindView(R.id.txt_deaths)
    TextView txtDeaths;
    @BindView(R.id.txt_recovered)
    TextView txtRecovered;
    @BindView(R.id.pie_chart)
    PieChart pieChart;

    public GlobalFragment() {
    }

    @Override
    public GlobalViewModel getViewModel() {
        GlobalViewModelFactory factory = new GlobalViewModelFactory(DataManager.getInstance().getDataService());
        return ViewModelProviders.of(this,factory).get(GlobalViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
       View view = inflater.inflate(R.layout.fragment_global, container, false);
        ButterKnife.bind(this, view);
       return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = getViewModel();
        showGlobal();
    }

    private void showGlobal() {
        viewModel.getGlobalData().observe(this,new GlobalDataObserver());
        viewModel.loadGlobalData();
    }

    private class GlobalDataObserver implements Observer<GlobalDataModel> {
        @Override
        public void onChanged(GlobalDataModel global) {
            if (global == null ) return;
            setAnimation(String.valueOf(global.getNbrCases()),totalCases);
            setAnimation(String.valueOf(global.getNbrDeaths()),deathCases);
            setAnimation(String.valueOf(global.getNbrRecovered()),recoverCases);
            txtConfirmed.setText(String.valueOf(global.getNbrCases()));
            txtDeaths.setText(String.valueOf(global.getNbrDeaths()));
            txtRecovered.setText(String.valueOf(global.getNbrRecovered()));
            ArrayList<PieEntry> entries = new ArrayList<>();

            // NOTE: The order of the entries when being added to the entries array determines their position around the center of
            // the chart.
            entries.add(new PieEntry(global.getNbrCases()));
            entries.add(new PieEntry(global.getNbrDeaths()));
            entries.add(new PieEntry(global.getNbrRecovered()));

            ArrayList<Integer> colors = new ArrayList<>();

            // NOTE: The order of the entries when being added to the entries array determines their position around the center of
            // the chart.
            colors.add(ContextCompat.getColor(getActivity(),R.color.color_confirmed));
            colors.add(ContextCompat.getColor(getActivity(),R.color.color_death));
            colors.add(ContextCompat.getColor(getActivity(),R.color.color_recovered));
            PieDataSet dataSet = new PieDataSet(entries, "");
            dataSet.setColors(colors);
            PieData data = new PieData(dataSet);
            data.setDrawValues(false);
            pieChart.setHoleColor(ContextCompat.getColor(getActivity(),R.color.colorTransparent));

            pieChart.setData(data);
            pieChart.animateY(1500, Easing.EaseInOutQuart);
            pieChart.setHoleRadius(75f);
            // undo all highlights
            pieChart.highlightValues(null);

            pieChart.invalidate();
        }
    }
    private void setanim(ValueAnimator animation, TextView textView) {
        textView.setText(String.valueOf(animation.getAnimatedValue()));
    }

    private void setAnimation(String cases, TextView textView) {
        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(0, Integer.parseInt(cases));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                setanim(animation, textView);
            }
        });
        animator.setEvaluator(new TypeEvaluator<Integer>() {
            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                return Math.round(startValue + (endValue - startValue) * fraction);
            }
        });
        animator.setDuration(2000);
        animator.start();
    }

}
