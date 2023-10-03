package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class ActivityDeleteAccount extends AppCompatActivity {

    // button
    Button btnDeleteAccount;

    // Text fields
    EditText txtStudentConfirmPassword;

    // one boolean variable to check whether all the text fields
    // are filled by the user, properly or not.
    boolean isAllFieldsChecked = false;

    // defining our own password pattern
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{4,}" +                // at least 4 characters
                    "$");

    /* private TextInputLayout email;*/
    /* private TextInputLayout password;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        ImageView btnBack = findViewById(R.id.btnBackDeleteAccount);
        btnBack.setOnClickListener(view -> startActivity(new Intent(ActivityDeleteAccount.this, ActivitySettings.class)));

        // register buttons with their proper IDs.
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);

        // register all the EditText fields with their IDs.
        txtStudentConfirmPassword = findViewById(R.id.txtStudentConfirmPassword);

        // handle the PROCEED button
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // store the returned value of the dedicated function which checks
                // whether the entered data is valid or if any fields are left blank.
                isAllFieldsChecked = CheckAllFields();

                // the boolean variable turns to be true then
                // only the user must be proceed to the activity2
                if (isAllFieldsChecked) {
                    Intent i = new Intent(ActivityDeleteAccount.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });

    }

    // function which checks all the text fields
    // are filled or not by the user.
    // when user clicks on the PROCEED button
    // this function is triggered.
    private boolean CheckAllFields() {
        if (txtStudentConfirmPassword.length() == 0) {
            txtStudentConfirmPassword.setError("This field is required");
            return false;
        }
        // after all validation return true.
        return true;
    }

    private boolean validatePassword() {
        String passwordInput = txtStudentConfirmPassword.getText().toString().trim();
        // if password field is empty
        // it will display error message "Field can not be empty"
        if (passwordInput.isEmpty()) {
            txtStudentConfirmPassword.setError("Field can not be empty");
            return false;
        }

        // if password does not matches to the pattern
        // it will display an error message "Password is too weak"
        else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            txtStudentConfirmPassword.setError("Password is too weak");
            return false;
        } else {
            txtStudentConfirmPassword.setError(null);
            return true;
        }
    }

    public void confirmInput(View v) {
        if (!validatePassword()) {
            return;
        }

        // if the email and password matches, a toast message
        // with email and password is displayed
        /*String input = "Email: " + email.getEditText().getText().toString();
        input += "\n";*/
        String input = "Success";
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
    }

}