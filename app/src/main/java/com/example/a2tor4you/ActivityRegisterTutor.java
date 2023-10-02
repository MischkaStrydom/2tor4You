package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.a2tor4you.utils.AndroidUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityRegisterTutor extends AppCompatActivity {
    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_tutor);

        ImageView btnBack = findViewById(R.id.btnBackRegisterTutor);
        btnBack.setOnClickListener(view -> startActivity(new Intent(ActivityRegisterTutor.this,MainActivity.class)));

        EditText name = findViewById(R.id.txt_TutorFirstName);
        EditText surname = findViewById(R.id.txt_TutorLastName);
        EditText phoneInput = findViewById(R.id.txt_TutorPhoneNum);
        EditText email = findViewById(R.id.txtTutorEmail);
        EditText password = findViewById(R.id.txtRegisterTutorPassword);
        EditText confirmPass = findViewById(R.id.txtConfirmRegisterTutorPassword);

        CheckBox verification = findViewById(R.id.chk_Agree);
        CheckBox suitable = findViewById(R.id.chk_Suitable);

        Button btn = findViewById(R.id.btnTutorSignUp);
        myDB = new DBHelper(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if(!verification.isChecked()){
                   AndroidUtil.showToast(getApplicationContext(), "Have to check both the check boxes.");
                   return;
               }
                if(!suitable.isChecked()){
                    AndroidUtil.showToast(getApplicationContext(), "Have to check both the check boxes.");
                    return;
                }

                String tutorName = name.getText().toString();
                String tutorSurname = surname.getText().toString();
                String completePhoneNumber = "+27" + phoneInput.getText().toString();
                String tutorEmail = email.getText().toString();
                String tutorPassword = password.getText().toString();
                String confirmPassword = confirmPass.getText().toString();

                long currentTimeMillis = System.currentTimeMillis();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// Customize the format as needed
                String dateTimeString = dateFormat.format(new Date(currentTimeMillis));

                String tutorRole = "Tutor";

                // Check if the phone number is 10 digits
                if (phoneInput.length() != 9) {
                    AndroidUtil.showToast(getApplicationContext(), "Incorrect phone number.");
                    return; // Exit the method and do not proceed
                }

                //Check email exist and check the role
                boolean isEmailRegisteredAsTutor = myDB.isNewTutorEmail(tutorEmail);
                if (isEmailRegisteredAsTutor) {
                    AndroidUtil.showToast(getApplicationContext(), "Email is already registered as a Tutor.");
                    return;
                }


                // Check if passwords match
                if (!tutorPassword.equals(confirmPassword)) {
                    AndroidUtil.showToast(getApplicationContext(), "Passwords do not match.");
                    return; // Exit the method and do not proceed
                }


                ContentValues contentValues = new ContentValues();
                contentValues.put("firstName", tutorName );
                contentValues.put("lastName", tutorSurname );
                contentValues.put("phoneNumber", completePhoneNumber );
                contentValues.put("email", tutorEmail );
                contentValues.put("password", tutorPassword );
                contentValues.put("createdAt", dateTimeString);
                contentValues.put("userRole", tutorRole);

                boolean result = myDB.insertData("User", contentValues);

                if (result) {
                    name.setText("");
                    surname.setText("");
                    phoneInput.setText("");
                    email.setText("");
                    password.setText("");
                    confirmPass.setText("");


                    AndroidUtil.showToast(getApplicationContext(), "Registered Tutor Successful!");

                } else {
                    AndroidUtil.showToast(getApplicationContext(), "An unknown error has occurred!");

                    // take user back
                }

            }
        });



    }
}