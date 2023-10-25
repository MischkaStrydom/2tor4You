package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity {


    DBHelper dbHelper;
    // button
    Button btnForgotCreateNewPassword;
    ImageView btnBack;
    Spinner roleSpinner;
    // Text fields
    EditText txtForgotEmail, txtForgotNewPassword, txtForgotConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        dbHelper = new DBHelper(this);
        btnBack = findViewById(R.id.btnForgotBackChangePassword);
        btnBack.setOnClickListener(view -> startActivity(new Intent(ForgotPassword.this, ActivityLogin.class)));

        btnForgotCreateNewPassword = findViewById(R.id.btnForgotCreateNewPassword);

        txtForgotEmail = findViewById(R.id.txtForgotEmail);
        roleSpinner = findViewById(R.id.spinRole);
        txtForgotNewPassword = findViewById(R.id.txtForgotNewPassword);
        txtForgotConfirmPassword = findViewById(R.id.txtForgotConfirmPassword);

        btnForgotCreateNewPassword.setOnClickListener(v -> {

            String email = txtForgotEmail.getText().toString();
            String role = roleSpinner.getSelectedItem().toString();

            String password = txtForgotNewPassword.getText().toString();
            String confirm = txtForgotConfirmPassword.getText().toString();

        int loggedInUserId = dbHelper.getUserIdForgot(email, role);

            if (loggedInUserId != -1) {

                if (password.equals(confirm)) {
                    boolean isChanged = dbHelper.changeUserPassword(loggedInUserId, password);

                    if(isChanged){
                        Toast.makeText(ForgotPassword.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                        txtForgotEmail.setText("");
                        roleSpinner.setSelection(0);
                        txtForgotNewPassword.setText("");
                        txtForgotConfirmPassword.setText("");
                    }
                    else {
                        Toast.makeText(ForgotPassword.this, "Failed to change password", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(ForgotPassword.this, "New password and confirm password does not match", Toast.LENGTH_SHORT).show();
                }
            }


        });


    }
}