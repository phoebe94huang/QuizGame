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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Quiz3Activity extends AppCompatActivity {

    private static final long countdownTime = 30000; // 30 second timer

    private TextView scoreView;
    private TextView questionView;
    private TextView timerView;
    private RadioButton answer1;
    private RadioButton answer2;
    private RadioButton answer3;
    private RadioButton answer4;
    private RadioButton answer5;
    private Button confirm;


    private int score = 0;
    private CountDownTimer timer;
    private long timeLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz3);

        scoreView = findViewById(R.id.scoreNum);
        questionView = findViewById(R.id.question);
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

        final TextView confirmation = view.findViewById(R.id.confirmAnswer);

        if (answer1.isChecked()) {
            confirmation.setText("The answer you have selected is: " + answer1.getText().toString());
        } else if (answer2.isChecked()) {
            confirmation.setText("The answer you have selected is: " + answer2.getText().toString());
        } else if (answer3.isChecked()) {
            confirmation.setText("The answer you have selected is: " + answer3.getText().toString());
        } else if (answer4.isChecked()) {
            confirmation.setText("The answer you have selected is: " + answer4.getText().toString());
        } else if (answer5.isChecked()) {
            confirmation.setText("The answer you have selected is: " + answer5.getText().toString());
        }

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

    // Checks answer
    private void checkAnswer() {
        timer.cancel();
        if(answer2.isChecked()) { // Correct answer
            score += 1; // Add 1
            updateScore(score);
            Toast.makeText(Quiz3Activity.this, "That's correct! Great job! +1", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Quiz3Activity.this, Quiz4Activity.class); // Goes to next question
            intent.putExtra("score", score); // pass amount earned
            startActivity(intent);
            finish();
        } else { // Wrong answer
            updateScore(score);
            Toast.makeText(Quiz3Activity.this, "That's incorrect. The correct answer is: Jonas Salk", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Quiz3Activity.this, Quiz4Activity.class);
            intent.putExtra("score", score);
            startActivity(intent);
            finish();
        }
    }

    // Update score
    private void updateScore(int pt) {
        scoreView.setText("" + score);
    }

    // Countdown timer --> user has 20 seconds to answer
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
