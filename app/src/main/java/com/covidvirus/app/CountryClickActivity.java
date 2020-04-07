package com.covidvirus.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.covidvirus.app.data.network.model.CountryDataModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CountryClickActivity extends AppCompatActivity {
    CountryDataModel countryData;
    @BindView(R.id.caseValue)
    TextView totalCases;
    @BindView(R.id.deathValue)
    TextView deathCases;
    @BindView(R.id.recove_value)
    TextView recoverCases;
  @BindView(R.id.country_title)
    TextView country_title;
    @BindView(R.id.totalCaseValue)
    TextView total_case;
    @BindView(R.id.todayCaseValue)
    TextView todayCase ;
    @BindView(R.id.activeCaseValue)
    TextView activeCase   ;
    @BindView(R.id.todayDeathValue)
    TextView todayDeath;
    @BindView(R.id.totalDeathValue)
    TextView total_death;
    @BindView(R.id.recoverValue)
    TextView recover_value;
    @BindView(R.id.pie_chart_country_click)
    PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_click);
        ButterKnife.bind(this);
        if (this.getIntent()!=null){
            countryData = (CountryDataModel) this.getIntent().getSerializableExtra("countryDetail");
        }
        setAnimation(String.valueOf(countryData.getNbrCases()),totalCases);
        setAnimation(String.valueOf(countryData.getNbrDeath()),deathCases);
        setAnimation(String.valueOf(countryData.getNbrRecovered()),recoverCases);
        if (countryData == null) return;
        totalCases.setText(String.valueOf(countryData.getNbrCases()));
        deathCases.setText(String.valueOf(countryData.getNbrDeath()));
        recoverCases.setText(String.valueOf(countryData.getNbrRecovered()));
        country_title.setText(String.valueOf(countryData.getCountry()));
        total_case.setText(String.valueOf(countryData.getNbrCases()));
        total_death.setText(String.valueOf(countryData.getNbrDeath()));
        recover_value.setText(String.valueOf(countryData.getNbrRecovered()));
        todayCase.setText(String.valueOf(countryData.getTodayCases()));
        todayDeath.setText(String.valueOf(countryData.getTodayDeaths()));
        activeCase.setText(String.valueOf(countryData.getNbrActiveCases()));
        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        entries.add(new PieEntry(countryData.getNbrCases()));
        entries.add(new PieEntry(countryData.getNbrDeath()));
        entries.add(new PieEntry(countryData.getNbrRecovered()));

        ArrayList<Integer> colors = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        colors.add(ContextCompat.getColor(this,R.color.color_confirmed));
        colors.add(ContextCompat.getColor(this,R.color.color_death));
        colors.add(ContextCompat.getColor(this,R.color.color_recovered));
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(false);
        pieChart.setHoleColor(ContextCompat.getColor(this,R.color.colorTransparent));

        pieChart.setData(data);
        pieChart.animateY(1500, Easing.EaseInOutQuart);
        pieChart.setHoleRadius(75f);
        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
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


