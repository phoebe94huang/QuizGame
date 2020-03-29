package com.example.quizgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Quiz5Activity extends AppCompatActivity {

    private static final long countdownTime = 30000; // 30 second timer

    private TextView scoreView;
    private TextView timerView;
    private CheckBox answer1;
    private CheckBox answer2;
    private CheckBox answer3;
    private CheckBox answer4;
    private CheckBox answer5;
    private Button confirm;

    private int score = 0;

    private CountDownTimer timer;
    private long timeLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz5);

        scoreView = findViewById(R.id.scoreNum);
        timerView = findViewById(R.id.timer);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);
        answer5 = findViewById(R.id.answer5);
        confirm = findViewById(R.id.confirm);

        // Get score
        Intent intent = getIntent();
        int points = intent.getIntExtra("score", 0);
        scoreView.setText("" + points);

        score = points;
        timeLeft = countdownTime;
        countdown();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmAnswerDialog();
            }
        });
    }

    private void confirmAnswerDialog(){
        LayoutInflater confirmDialogInflater = LayoutInflater.from(getApplicationContext());
        View view = confirmDialogInflater.inflate(R.layout.confirm_dialog, null);

        AlertDialog.Builder userConfirm = new AlertDialog.Builder(this);
        userConfirm.setView(view);

        userConfirm
                .setCancelable(false)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("Go back",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = userConfirm.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
    }

    private void checkAnswer(){
        timer.cancel();
        if(answer1.isChecked() && answer3.isChecked() && answer4.isChecked() && answer5.isChecked() ) {
            score++;
            updateScore(score);
            Toast.makeText(Quiz5Activity.this, "Correct! Great job! :)", Toast.LENGTH_SHORT).show();
            // Goes to next page
            Intent intent = new Intent(Quiz5Activity.this, ResultsActivity.class);
            intent.putExtra("score", score); // pass the current score to results
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(Quiz5Activity.this, "Incorrect! The answers are: Jason Mesnick," +
                            " Arie Luyendyk Jr, Peter Weber, and Ben Higgins",
                    Toast.LENGTH_SHORT).show();
            updateScore(score);
            // Goes to next page
            Intent intent = new Intent(Quiz5Activity.this, ResultsActivity.class);
            intent.putExtra("score", score); // pass the current score to results
            startActivity(intent);
            finish();
        }
    }

    // Updates score
    private void updateScore(int pt) {
        scoreView.setText("" + score);
    }

    // Countdown timer --> user has 30 seconds to answer
    // If timer runs out, answer is checked and then goes to next question
    private void countdown(){
        timer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateTimerView();
            }
            @Override
            public void onFinish() {
                timeLeft = 0;
                updateTimerView();
                checkAnswer();
            }
        }.start();
    }

    // Update timer
    private void updateTimerView() {
        int seconds = (int) (timeLeft / 1000) % 60;
        String timeSeconds = String.format(Locale.getDefault(), "%d seconds left", seconds);
        timerView.setText(timeSeconds); // displays seconds countdown

        // less than 5 seconds remaining, change text color to red
        if (timeLeft < 5000) {
            timerView.setTextColor(Color.RED);
        } else if (timeLeft < 10000) { // less than 10 seconds = yellow text
            timerView.setTextColor(Color.YELLOW);
        } else { // green text
            timerView.setTextColor(Color.GREEN);
        }
    }
}