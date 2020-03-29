package com.example.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ResultsActivity extends AppCompatActivity {

    private TextView userScore;
    private TextView userHighScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        userScore = findViewById(R.id.yourScore);
        userHighScore = findViewById(R.id.yourHighScore);

        // get score
        Intent intent = getIntent();
        int finalScore = intent.getIntExtra("score", 0);

        // display current score
        userScore.setText("Your Score: " + "\n" + finalScore + "/5");

        SharedPreferences scorePrefs = getSharedPreferences("highScore", MODE_PRIVATE);
        int highScore = scorePrefs.getInt("highScore", 0);
        // if final score is higher than the previous high score, set + display new high score
        if (highScore < finalScore) {
            Toast.makeText(ResultsActivity.this, "Great job! You beat your high score", Toast.LENGTH_SHORT).show();
            userHighScore.setText("Your New High Score: " + "\n" + finalScore + "/5");
            SharedPreferences.Editor editor = scorePrefs.edit();
            editor.putInt("highScore", finalScore);
            editor.commit();
        } else { // if final score is less than or equal to previous high score, keep + display original high score
            userHighScore.setText("Your High Score: " + "\n" + highScore + "/5");
        }
    }

    // Try again button clicked to restart quiz (goes to rules page)
    public void tryAgainButton (View view) {
        Intent tryIntent = new Intent (ResultsActivity.this, RulesActivity.class);
        startActivity(tryIntent);
    }
}