package com.example.meeteatsave;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

       // getSupportActionBar().hide();

        new Handler().postDelayed(() -> startActivity(
                new Intent(getApplicationContext(), LoginActivity.class)), 3000);


    }
}
