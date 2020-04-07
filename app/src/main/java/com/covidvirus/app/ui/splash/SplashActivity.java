package com.covidvirus.app.ui.splash;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.covidvirus.app.R;
import com.covidvirus.app.ui.base.BaseActivity;


import com.covidvirus.app.ui.home.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SplashActivity extends BaseActivity<SplashViewModel> {
    private static final String TAG = "SplashScreenActivity";
    private static final int DELAY = 3000;
    @BindView(R.id.logoImage) ImageView mLogoImage;



    @Override
    public SplashViewModel createViewModel() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(DELAY);
        rotate.setInterpolator(new LinearInterpolator());

        mLogoImage.setAnimation(rotate);

        nextScreen();
    }

    private void nextScreen(){
        Log.d(TAG, "nextScreen: ");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        },DELAY);
    }


}
