package com.example.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

// Rules activity to display rules
public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
    }

    // Button clicked --> go to quiz
    public void clickNext (View view) {
        Intent nextIntent = new Intent (RulesActivity.this, Quiz1Activity.class);

        startActivity(nextIntent);
    }
}
