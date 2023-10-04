package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class ActivityChangePassword extends AppCompatActivity {
    DBHelper dbHelper ;
    // button
    Button btnCreateNewPassword;

    // Text fields
    EditText txtCurrentPassword, txtNewPassword, txtConfirmPassword;

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
        setContentView(R.layout.activity_change_password);


        dbHelper = new DBHelper(this);
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int loggedInUserId = preferences.getInt("loggedInUserId", -1); // -1 is a default value if key not found


        ImageView btnBack = findViewById(R.id.btnBackChangePassword);
        btnBack.setOnClickListener(view -> startActivity(new Intent(ActivityChangePassword.this, ActivitySettings.class)));

        // register buttons with their proper IDs.
        btnCreateNewPassword = findViewById(R.id.btnCreateNewPassword);

        // register all the EditText fields with their IDs.
        txtCurrentPassword = findViewById(R.id.txtCurrentPassword);
        txtNewPassword = findViewById(R.id.txtNewPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);

        // handle the PROCEED button
        btnCreateNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // store the returned value of the dedicated function which checks
                // whether the entered data is valid or if any fields are left blank.
                isAllFieldsChecked = CheckAllFields();
                String curPass = txtCurrentPassword.getText().toString();
                String newPass = txtNewPassword.getText().toString();
                String confirmPass = txtConfirmPassword.getText().toString();

                // the boolean variable turns to be true then
                // only the user must be proceed to the activity2
                if (isAllFieldsChecked) {



                    if (loggedInUserId != -1) {
                        // Fetch user's name and surname from the database based on userID
                        String userPass = dbHelper.getPassword("User", loggedInUserId); // Implement this method

                        // Separate name and surname as separate strings
                        if (userPass.equals(curPass)) {

                            if (newPass.equals(confirmPass)){



                                ContentValues values = new ContentValues();
                                values.put("userID", loggedInUserId);
                                values.put("currentPassword", curPass);
                                values.put("newPassword", newPass);
                                values.put("confirmPassword", confirmPass);

                                boolean isChanged = dbHelper.changeUserPassword(loggedInUserId, newPass);

                                boolean isInserted = dbHelper.insertData("PasswordChange", values);

                                if (isChanged && isInserted) {
                                    Toast.makeText(ActivityChangePassword.this, "Password changed successfully", Toast.LENGTH_SHORT).show();

                                    txtCurrentPassword.setText("");
                                    txtNewPassword.setText("");
                                    txtConfirmPassword.setText("");

                                } else {
                                    Toast.makeText(ActivityChangePassword.this, "Failed to change password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(ActivityChangePassword.this, "New password and confirm password does not match", Toast.LENGTH_SHORT).show();
                            }



                        } else {
                            // Handle the case where the userName format is unexpected
                            Toast.makeText(ActivityChangePassword.this, "Incorrect current password", Toast.LENGTH_SHORT).show();
                        }

                    }


                }
            }
        });
    }

    // function which checks all the text fields
    // are filled or not by the user.
    // when user clicks on the PROCEED button
    // this function is triggered.
    private boolean CheckAllFields() {
        if (txtCurrentPassword.length() == 0) {
            txtCurrentPassword.setError("This field is required");
            return false;
        }

        if (txtNewPassword.length() == 0) {
            txtNewPassword.setError("This field is required");
            return false;
        }

        if (txtConfirmPassword.length() == 0) {
            txtConfirmPassword.setError("This field is required");
            return false;
        }

        // after all validation return true.
        return true;
    }

   /* private boolean validateEmail() {

        // Extract input from EditText
        String emailInput = email.getEditText().getText().toString().trim();

        // if the email input field is empty
        if (emailInput.isEmpty()) {
            email.setError("Field can not be empty");
            return false;
        }

        // Matching the input email to a predefined email pattern
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setError("Please enter a valid email address");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }*/

    private boolean validatePassword() {
        String passwordInput = txtNewPassword.getText().toString().trim();
        // if password field is empty
        // it will display error message "Field can not be empty"
        if (passwordInput.isEmpty()) {
            txtNewPassword.setError("Field can not be empty");
            return false;
        }

        // if password does not matches to the pattern
        // it will display an error message "Password is too weak"
        else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            txtNewPassword.setError("Password is too weak");
            return false;
        } else {
            txtNewPassword.setError(null);
            return true;
        }
    }

    public void confirmInput(View v) {
        if (/*!validateEmail() |*/ !validatePassword()) {
            return;
        }

        // if the email and password matches, a toast message 
        // with email and password is displayed
        /*String input = "Email: " + email.getEditText().getText().toString();
        input += "\n";*/
        String input = "Password: " + txtNewPassword.getText().toString();
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
    }
}