package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

import com.example.a2tor4you.utils.AndroidUtil;

public class ActivityLogin extends AppCompatActivity {

    ImageView btnBack;
    TextView phonePrefix;
    EditText phoneInput;
    EditText userPassword;
    Spinner roleSpinner;
    TextView btnSignUp;
    Button btnLogin;
    Context context;
    DBHelper dbHelper ;
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


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                    if ("Tutor".equalsIgnoreCase(selectedRole)) { // Use .equalsIgnoreCase to compare strings
                        Intent intent = new Intent(ActivityLogin.this, LoginOtpActivity.class);
                        intent.putExtra("phone", completePhoneNumber);
                        startActivity(intent);
                       // Intent intent = new Intent(ActivityLogin.this, ActivityHomeStudent.class);
                        // Add any extra data or actions for the Tutor role here
                       // startActivity(intent);
                    } else if ("Student".equalsIgnoreCase(selectedRole)) { // Use .equalsIgnoreCase to compare strings
                        Intent intent = new Intent(ActivityLogin.this, LoginOtpActivity.class);
                        intent.putExtra("phone", completePhoneNumber);
                        //Intent intent = new Intent(ActivityLogin.this, ActivityHomeStudent.class);
                        // Add any extra data or actions for the Tutor role here
                        startActivity(intent);
                    }else if ("Admin".equalsIgnoreCase(selectedRole)) { // Use .equalsIgnoreCase to compare strings
                        Intent intent = new Intent(ActivityLogin.this, LoginOtpActivity.class);
                        intent.putExtra("phone", completePhoneNumber);
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


}


