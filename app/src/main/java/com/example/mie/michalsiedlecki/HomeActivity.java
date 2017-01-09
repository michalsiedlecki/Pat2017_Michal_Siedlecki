package com.example.mie.michalsiedlecki;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onLogoutButtonClick(View view) {
        SharedPreferences loginStatus = getSharedPreferences("data", 0);
        SharedPreferences.Editor editorLoginStatus = loginStatus.edit();
        editorLoginStatus.putBoolean("loginStatus", false);
        editorLoginStatus.commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
