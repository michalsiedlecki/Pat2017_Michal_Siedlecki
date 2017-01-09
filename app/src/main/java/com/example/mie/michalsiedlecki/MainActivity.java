package com.example.mie.michalsiedlecki;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStartGameButtonClick(View view) {
        SharedPreferences loginStatus = getSharedPreferences("data", 0);
        SharedPreferences.Editor editorLoginStatus = loginStatus.edit();
        editorLoginStatus.putBoolean("loginStatus", true);
        editorLoginStatus.commit();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }


}
