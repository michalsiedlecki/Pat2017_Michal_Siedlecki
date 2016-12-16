package com.example.mie.michalsiedlecki;

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
        Toast.makeText(MainActivity.this, "Maybe someday...",
                Toast.LENGTH_LONG).show();
    }
}
