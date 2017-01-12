package com.example.mie.michalsiedlecki;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {

    private static int SPLASH_TIME_CLOSE = 5000;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences loginStatus = getSharedPreferences("data", 0);
        if(loginStatus.getBoolean("loginStatus", false)){
            openHomeActivity();
            finish();
            return;
        }
        setContentView(R.layout.activity_splash);
    }


    private void openHomeActivity(){
        Intent intent = new Intent(this, HomeActivity.class);
        this.startActivity(intent);
        finish();
    }

    private void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        finish();
    }

    private void lateOpenMainActvity(){
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                openMainActivity();
            }
        }, SPLASH_TIME_CLOSE);
    }

    @Override
    public void onBackPressed() {
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onPause() {
        handler.removeCallbacksAndMessages(null);
        super.onPause();
    }

    @Override
    protected void onResume() {
        lateOpenMainActvity();
        super.onResume();
    }

}