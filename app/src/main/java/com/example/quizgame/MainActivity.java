package com.example.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private static int splashTime = 2000; // Splash screen will run for 2000 ms (2s)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Move from splash screen to home screen of app
                Intent splashIntent = new Intent (MainActivity.this, HomeActivity.class);

                // Start HomeActivity
                startActivity(splashIntent);

                // End splash screen
                finish();
            }
        }, splashTime);
    }
}