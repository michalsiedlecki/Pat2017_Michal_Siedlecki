package com.example.mie.michalsiedlecki;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by MIE on 13-Dec-16.
 */

public class SplashActivity extends Activity {

    private static int SPLASH_TIME_CLOSE = 5000;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences loginStatus = getSharedPreferences("data", 0);
        if(loginStatus.getBoolean("loginStatus", false) == true){
            openHomeActivity();
            finish();
            return;
        }
        setContentView(R.layout.activity_splash);

        timer = new Timer();
        timer.schedule(new CloseSplash(), SPLASH_TIME_CLOSE);
    }


    private void openHomeActivity(){
        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
    }

    @Override
    public void onBackPressed() {
        timer.cancel();
    }

    private class CloseSplash extends TimerTask {

        @Override
        public void run() {
            waitForCloseSplash();
            openMainActivity();
        }

        private void waitForCloseSplash(){
            try {
                Thread.sleep(SPLASH_TIME_CLOSE);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void openMainActivity(){
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            SplashActivity.this.startActivity(intent);
            SplashActivity.this.finish();
        }
    }
}