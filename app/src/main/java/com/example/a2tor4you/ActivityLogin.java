package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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

public class ActivityLogin extends AppCompatActivity {

    ImageButton btnBack;
    TextView phonePrefix;
    EditText phoneInput;
    EditText password;
    Spinner roleSpinner;
    TextView btnSignUp;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnBack = findViewById(R.id.btnBackLogin);
        btnSignUp = findViewById(R.id.txtSignUp);
        roleSpinner = findViewById(R.id.spinRole);
        phoneInput = findViewById(R.id.txtPhoneNum);
        phonePrefix = findViewById(R.id.txtPrefix);
        password = findViewById(R.id.txtLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);



        //On button back takes user back to main screen
        btnBack.setOnClickListener(view -> startActivity(new Intent(ActivityLogin.this,MainActivity.class)));

        //On text Sign Up click takes user back to main screen
        btnSignUp.setOnClickListener(view -> startActivity(new Intent(ActivityLogin.this,MainActivity.class)));

        //Get role selected and check if anything is selected
        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedRole = parentView.getItemAtPosition(position).toString();
                // Do something with the selected role (e.g., store it or display a message).
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do something when nothing is selected (if needed).
            }

        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phoneInput.length() != 9){
                    phoneInput.setError("Phone number not valid");
                    return;
                }
                String completePhoneNumber = "+27" + phoneInput.getText().toString();
                Intent intent = new Intent(ActivityLogin.this,LoginOtpActivity.class);
                intent.putExtra("phone",completePhoneNumber);
                startActivity(intent);

            }
        });




    }


}