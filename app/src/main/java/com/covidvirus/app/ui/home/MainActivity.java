package com.covidvirus.app.ui.home;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.covidvirus.app.R;
import com.covidvirus.app.data.network.model.Location;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.tabs.TabLayout;
import com.covidvirus.app.data.DataManager;
import com.covidvirus.app.ui.base.BaseActivity;
import com.covidvirus.app.ui.home.main.countries_fragment.CountriesFragment;
import com.covidvirus.app.ui.home.main.global_fragment.GlobalFragment;
import com.covidvirus.app.ui.home.profile.ProfileFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity<MainViewModel> {

    public static final String INTERSTITIAL_AD_KEY = "ca-app-pub-4756765024373725/6038319531";
    private final String TAG = "MainActivity";
    @BindView(R.id.mainBanner)
    AdView bannerView;
    @BindView(R.id.mainPager)
    ViewPager mainPager;
    @BindView(R.id.mainTabs)
    TabLayout mainTabLayout;
    private MainPagerAdapter mainAdapter;
    private MainViewModel viewModel;
    private InterstitialAd mInterstitialAd;


    @Override
    public MainViewModel createViewModel() {
        MainViewModelFactory factory = new MainViewModelFactory(DataManager.getInstance().getLocationService());
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initAdMob();
        DataManager.getInstance().setRunCount();
        Log.d(TAG, "onCreate: count"+DataManager.getInstance().getRunCount());
        if (DataManager.getInstance().getDefaultCountry() == null) {
            viewModel.loadLocationData();
            viewModel.getLocationData().observe(this, new LocationDataObserver());
        } else
            loadFragment(R.id.profileContainer, new ProfileFragment());
        setMainPagerAdapter();
        mainTabLayout.setupWithViewPager(mainPager, true);
    }

    private void initAdMob(){
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        bannerView.loadAd(adRequest);
        bannerView.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                bannerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.e(TAG, "onAdFailedToLoad: " + errorCode);
                bannerView.setVisibility(View.GONE);
            }
        });
        if(!bannerView.isLoading()) bannerView.setVisibility(View.GONE);

        if( DataManager.getInstance().getRunCount() == DataManager.MAX_COUNT) {
            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId(INTERSTITIAL_AD_KEY);
            mInterstitialAd.loadAd(new AdRequest.Builder().build());

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    mInterstitialAd.show();
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    Log.e(TAG, "onAdFailedToLoad: " + errorCode);
                }
            });
        }
    }


    private void setMainPagerAdapter() {
        mainAdapter = new MainPagerAdapter(getSupportFragmentManager(), 0);
        mainAdapter.cleanFragments();
        mainAdapter.addFragment(new GlobalFragment());
        mainAdapter.addFragment(new CountriesFragment());
        ArrayList<String> titles = new ArrayList<>();
        titles.add(this.getResources().getString(R.string.global_fragment));
        titles.add(this.getResources().getString(R.string.countries_fragment));
        mainAdapter.addTitles(titles);
        mainPager.setAdapter(mainAdapter);
    }

    private void loadFragment(int viewId, Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(viewId, fragment)
                    .commit();
        }
    }

    private class LocationDataObserver implements Observer<Location> {
        @Override
        public void onChanged(Location location) {
            if (location == null) return;
            Log.d(TAG, "onChanged: location " + location.getCountry());
            DataManager.getInstance().setDefaultCountry(location.getCountry());
            loadFragment(R.id.profileContainer, new ProfileFragment());
        }
    }

}
