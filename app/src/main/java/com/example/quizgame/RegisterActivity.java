package com.example.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {
    // Pattern for passwords
    // At least 1 number and 6 characters
    // Any letters, lower or uppercase
    private static final String passwordRegex = "^(?=.*[a-zA-Z])(?=.*[0-9])(.{6,})$";
    private static final Pattern passwordPattern = Pattern.compile(passwordRegex);

    // Pattern for phone numbers
    // ###-###-#### format
    private static final String phoneRegex = "^\\D?(\\d{3})\\D?\\D?(\\d{3})\\D?(\\d{4})$";
    private static final Pattern phonePattern = Pattern.compile(phoneRegex);

    // Pattern for dates
    // mm/dd/yyyy format
    private static final String dobRegex = "^\\D?(\\d{2})\\D?\\D?(\\d{2})\\D?(\\d{4})$";
    private static final Pattern dobPattern = Pattern.compile(dobRegex);

    Button btnReg;
    private TextInputLayout fn;
    private TextInputLayout ln;
    private TextInputLayout dob;
    private TextInputLayout em;
    private TextInputLayout pw;
    private TextInputLayout pn;
    private TextInputLayout un;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnReg = (Button) findViewById(R.id.Complete);
        fn = (TextInputLayout) findViewById(R.id.enterFirst);
        ln = (TextInputLayout) findViewById(R.id.enterLast);
        dob = (TextInputLayout) findViewById(R.id.enterDOB);
        pn = (TextInputLayout) findViewById(R.id.enterPhone);
        em = (TextInputLayout) findViewById(R.id.enterEmail);
        pw = (TextInputLayout) findViewById(R.id.enterPW);
        un = (TextInputLayout) findViewById(R.id.enterUser);
    }

    // Validate first name
    private boolean firstNameValidate() {
        String firstName = fn.getEditText().getText().toString();
        if (firstName.isEmpty()) { // doesn't accept empty fields
            fn.setError("Field can't be empty");
            return false;
        } else if (firstName.length() < 3) { // doesn't accept if name has less than 3 characters
            fn.setError("Can't have less than 3 characters");
            return false;
        } else if (firstName.length() > 30) { // doesn't accept if name has more than 30 characters
            fn.setError("Can't have more than 30 characters");
            return false;
        } else {
            fn.setError(null);
            return true;
        }
    }

    // Validate last name
    private boolean lastNameValidate() {
        String lastName = ln.getEditText().getText().toString();
        if (lastName.isEmpty()) { // doesn't accept empty fields
            ln.setError("Field cannot be empty");
            return false;
        } else if (lastName.length() < 3) { // doesn't accept if name has less than 3 characters
            ln.setError("Can't have less than 3 characters");
            return false;
        } else if (lastName.length() > 30) { // doesn't accept if name has more than 30 characters
            ln.setError("Can't have more than 30 characters");
            return false;
        } else {
            ln.setError(null);
            return true;
        }
    }

    // Validate date of birth
    private boolean dobValidate() {
        String birthday = dob.getEditText().getText().toString();
        if (birthday.isEmpty()) { // doesn't accept empty fields
            dob.setError("Field cannot be empty");
            return false;
        } else if (!dobPattern.matcher(birthday).matches()) {       // Checks if birthday matches the
            // mm/dd/yyyy pattern
            dob.setError("Please enter a valid date");
            return false;
        }
        else {
            dob.setError(null);
            return true;
        }
    }

    // Validate phone number
    private boolean phoneValidate() {
        String phoneNum = pn.getEditText().getText().toString();
        if (phoneNum.isEmpty()) { // doesn't accept empty fields
            pn.setError("Field cannot be empty");
            return false;
        } else if (!phonePattern.matcher(phoneNum).matches()) {     // Checks if phone number matches the
            // ###-###-#### pattern
            pn.setError("Please enter a valid phone number");
            return false;
        } else {
            pn.setError(null);
            return true;
        }
    }

    // Validate email
    private boolean emailValidate() {
        String email = em.getEditText().getText().toString();
        if (email.isEmpty()) { // doesn't accept empty fields
            em.setError("Field cannot be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {  // Checks if email matches
            // the correct pattern
            em.setError("Please enter a valid email address");
            return false;
        } else {
            em.setError(null);
            return true;
        }
    }

    // Validates username
    private boolean usernameValidate() {
        String username = un.getEditText().getText().toString();
        if (username.isEmpty()) { // doesn't accept empty fields
            un.setError("Field cannot be empty");
            return false;
        } else if (username.length() < 5) { // doesn't accept if username has less than 5 characters
            un.setError("Can't have less than 3 characters");
            return false;
        } else if (username.length() > 30) { // doesn't accept if username has more than 30 characters
            un.setError("Can't have more than 30 characters");
            return false;
        } else {
            un.setError(null);
            return true;
        }
    }

    // Validates password
    private boolean passwordValidate() {
        String password = pw.getEditText().getText().toString();
        if (password.isEmpty()) { // doesn't accept empty fields
            pw.setError("Field cannot be empty");
            return false;
        } else if (!passwordPattern.matcher(password).matches()) {      // Checks if password entered
            // matches the correct pattern
            pw.setError("Must contain at least 1 number and be at least 6 characters long");
            return false;
        } else {
            pw.setError(null);
            return true;
        }
    }

    // onClick for button
    public void completeReg(View view) {
        // Intent created
        Intent registerIntent = new Intent(RegisterActivity.this, HomeActivity.class);
        // Snackbar created
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                "Please make sure all fields are filled correctly", Snackbar.LENGTH_LONG);

        // If any fields do not meet the requirements, snackbar message is shown
        if (!passwordValidate() | !usernameValidate() | !emailValidate() | !phoneValidate() |
                !dobValidate() | !lastNameValidate() | !firstNameValidate()) {
            snackbar.show();
        } else { // All field requirements are met
            btnReg.setEnabled(true);
            // Registration complete, goes back to HomeActivity (previous page)
            startActivity(registerIntent);
        }
    }
}
