package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2tor4you.utils.AndroidUtil;

import java.util.regex.Pattern;

public class ActivityLogin extends AppCompatActivity {

    ImageView btnBack;
    TextView phonePrefix, btnSignUp;
    EditText phoneInput, userPassword;
    Spinner roleSpinner;
    Button btnLogin, btnCreateNewPassword;
    Context context;
    DBHelper dbHelper ;

   /* // one boolean variable to check whether all the text fields
    // are filled by the user, properly or not.
    boolean isAllFieldsChecked = false;

    // defining our own password pattern
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{4,}" +                // at least 4 characters
                    "$");*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = new DBHelper(this);
        btnBack = findViewById(R.id.btnBackLogin);
        btnSignUp = findViewById(R.id.txtSignUp);
        roleSpinner = findViewById(R.id.spinRole);
        phoneInput = findViewById(R.id.txtPhoneNum);
        phonePrefix = findViewById(R.id.txtPrefix);
        userPassword = findViewById(R.id.txtLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);

        //On button back takes user back to main screen
        btnBack.setOnClickListener(view -> startActivity(new Intent(ActivityLogin.this,MainActivity.class)));

        //On text Sign Up click takes user back to main screen
        btnSignUp.setOnClickListener(view -> startActivity(new Intent(ActivityLogin.this,MainActivity.class)));

        // handle the PROCEED button
//        btnCreateNewPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // store the returned value of the dedicated function which checks
//                // whether the entered data is valid or if any fields are left blank.
//                isAllFieldsChecked = CheckAllFields();
//
//                // the boolean variable turns to be true then
//                // only the user must be proceed to the activity2
//                if (isAllFieldsChecked) {
//                    Intent i = new Intent(ActivityLogin.this, ActivityHomeStudent.class);
//                    startActivity(i);
//                }
//            }
//        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkAllFields()) {
                    // All fields are filled, proceed with registration
                    // ... (your existing registration code)
                } else {
                    // Fields are missing, show an error message
                    AndroidUtil.showToast(getApplicationContext(), "All fields are required.");
                }

                //Get role selected and check if anything is selected
                String selectedRole = roleSpinner.getSelectedItem().toString();
                String password = userPassword.getText().toString();
                String completePhoneNumber = "+27" + phoneInput.getText().toString();


                if(phoneInput.length() != 9){
                    phoneInput.setError("Phone number not valid");
                    return;
                }
                boolean isLoggedIn = dbHelper.login(completePhoneNumber, password, selectedRole);

                if (isLoggedIn) {

                    int loggedInUserId = dbHelper.getUserId(completePhoneNumber, password, selectedRole);
                    if (loggedInUserId != -1) {
                        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("loggedInUserId", loggedInUserId);
                        editor.apply();}
                    else{
                        Toast.makeText(context, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                    }

                    if ("Tutor".equalsIgnoreCase(selectedRole)) { // Use .equalsIgnoreCase to compare strings
                        Intent intent = new Intent(ActivityLogin.this, LoginOtpActivity.class);
                        intent.putExtra("phone", completePhoneNumber);
                        intent.putExtra("password", password);
                        intent.putExtra("selectedRole", selectedRole);

                        startActivity(intent);

                    } else if ("Student".equalsIgnoreCase(selectedRole)) { // Use .equalsIgnoreCase to compare strings
                        Intent intent = new Intent(ActivityLogin.this, LoginOtpActivity.class);
                        intent.putExtra("phone", completePhoneNumber);
                        intent.putExtra("password", password);
                        intent.putExtra("selectedRole", selectedRole);

                        startActivity(intent);
                    }else if ("Admin".equalsIgnoreCase(selectedRole)) { // Use .equalsIgnoreCase to compare strings
                        Intent intent = new Intent(ActivityLogin.this, LoginOtpActivity.class);
                        intent.putExtra("phone", completePhoneNumber);
                        intent.putExtra("password", password);
                        intent.putExtra("selectedRole", selectedRole);

                                //  Intent intent = new Intent(ActivityLogin.this, AdminActivity.class);
                                // Add any extra data or actions for the Tutor role here
                        startActivity(intent);
                            }

                    // Handle other roles if needed
                    }else  {
                    // User does not exist or role does not match
                    // Display an error message to the user
                    AndroidUtil.showToast(getApplicationContext(), "User not registered.");
                }

                dbHelper.close();

            }
        });

    }

    // Check if all required fields are filled

    private boolean checkAllFields() {
        // Check if all fields are filled
        boolean isRoleEmpty = roleSpinner.getSelectedItemPosition() == 0; // Assuming the first item is a hint or prompt
        boolean isPhoneEmpty = phoneInput.getText().toString().trim().isEmpty();
        boolean isPassEmpty = userPassword.getText().toString().trim().isEmpty();

        // Display error messages for empty fields
        if (isRoleEmpty) {
            ((TextView) roleSpinner.getSelectedView()).setError("Role is required");
        }
        if (isPhoneEmpty) {
            phoneInput.setError("Phone number is required");
        }
        if (isPassEmpty) {
            userPassword.setError("Password is required");
        }

        // Return true if all fields are filled, otherwise return false
        return !(isRoleEmpty || isPhoneEmpty || isPassEmpty);
    }


    // function which checks all the text fields
    // are filled or not by the user.
    // when user clicks on the PROCEED button
    // this function is triggered.
   /* private boolean CheckAllFields() {
        if (phoneInput.length() == 0) {
            phoneInput.setError("This field is required");
            return false;
        }

        if (userPassword.length() == 0) {
            userPassword.setError("This field is required");
            return false;
        }

        // after all validation return true.
        return true;
    }
*/
}


