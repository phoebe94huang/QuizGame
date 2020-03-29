package com.example.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout pw;
    private TextInputLayout un;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pw = (TextInputLayout) findViewById(R.id.enterPW);
        un = (TextInputLayout) findViewById(R.id.enterUser);
        btnLogin = (Button) findViewById(R.id.Complete);

        pw.getEditText().addTextChangedListener(myTextWatcher);
        un.getEditText().addTextChangedListener(myTextWatcher);

        btnLogin.setEnabled(false); // Disables login button to start
    }

    // Validates password
    // Password = testing123
    private boolean passwordValidate() {
        String password = pw.getEditText().getText().toString();
        String acceptPW = "testing123"; // Hardcoded password

        // Checks if entered username is "testing123"
        if (!password.equals(acceptPW)) {
            return false;
        } else {
            return true;
        }
    }

    // Validates username
    // Username = montclair
    private boolean usernameValidate() {
        String username = un.getEditText().getText().toString();
        String acceptUN = "montclair"; // Hardcoded username

        // Checks if entered username is "montclair"
        if (!username.equals(acceptUN)) {
            return false;
        } else {
            return true;
        }
    }

    // TextWatcher
    private TextWatcher myTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            checkForEmptyFields(); // Checks for empty fields
        }
    };

    // Disables login button if there are any empty fields
    void checkForEmptyFields() {
        String password = pw.getEditText().getText().toString();
        String username = un.getEditText().getText().toString();

        // Checks for empty fields
        if (password.isEmpty() || username.isEmpty()) {
            btnLogin.setEnabled(false); // Button disabled if one or both fields are empty
        } else {
            btnLogin.setEnabled(true); // Button enabled --> no empty fields
        }
    }

    // Confirm button clicked --> go to WelcomeActivity
    public void completeLogin (View view) {
        Intent loginIntent = new Intent(LoginActivity.this, RulesActivity.class);
        // Snackbar created
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                "Invalid username and/or password", Snackbar.LENGTH_LONG);

        // If any fields do not meet the requirements, snackbar message is shown
        if (!passwordValidate() | !usernameValidate()) {
            snackbar.show();
        } else { // All field requirements are met
            btnLogin.setEnabled(true);
            // Registration complete, goes to WelcomeActivity
            startActivity(loginIntent);
        }
    }
}