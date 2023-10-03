package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a2tor4you.utils.AndroidUtil;
import com.google.type.DateTime;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityRegisterStudent extends AppCompatActivity {

    // button
    Button btnCreateNewPassword;

    // Text fields
    EditText txtCurrentPassword, txtNewPassword, txtConfirmPassword;

    // one boolean variable to check whether all the text fields
    // are filled by the user, properly or not.
    boolean isAllFieldsChecked = false;

    static String studentName;
    static String studentSurname;
    static String completePhoneNumber;
    static String studentEmail ;
    static String studentPassword;
    static String confirmPassword;

    DBHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);

        ImageView btnBack = findViewById(R.id.btnBackStudentRegister);
        btnBack.setOnClickListener(view -> startActivity(new Intent(ActivityRegisterStudent.this,MainActivity.class)));

        EditText name = findViewById(R.id.txt_StudentFirstName);
        EditText surname = findViewById(R.id.txt_StudentLastName);
        EditText phoneInput = findViewById(R.id.txt_StudentPhoneNum);
        EditText email = findViewById(R.id.txtStudentEmail);
        EditText password = findViewById(R.id.txtRegisterStudentPassword);
        EditText confirmPass = findViewById(R.id.txtConfirmRegisterStudentPassword);



        Button btn = findViewById(R.id.btnStudentSignUp);
        myDB = new DBHelper(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               studentName = name.getText().toString();
               studentSurname = surname.getText().toString();
               completePhoneNumber = "+27" + phoneInput.getText().toString();
               studentEmail = email.getText().toString();
               studentPassword = password.getText().toString();
               confirmPassword = confirmPass.getText().toString();

                long currentTimeMillis = System.currentTimeMillis();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// Customize the format as needed
                String dateTimeString = dateFormat.format(new Date(currentTimeMillis));

                String studentRole = "Student";

                // Check if the phone number is 10 digits
                if (phoneInput.length() != 9) {
                    AndroidUtil.showToast(getApplicationContext(), "Incorrect phone number.");
                    return; // Exit the method and do not proceed
                }

                //Check email exist and check the role
                boolean isEmailRegisteredAsStudent = myDB.isNewStudentEmail(studentEmail);
                if (isEmailRegisteredAsStudent) {
                    AndroidUtil.showToast(getApplicationContext(), "Email is already registered as a student.");
                    return;
                }

//
                // Check if passwords match
                if (!studentPassword.equals(confirmPassword)) {
                    AndroidUtil.showToast(getApplicationContext(), "Passwords do not match.");
                    return; // Exit the method and do not proceed
                }



                ContentValues contentValues = new ContentValues();
                contentValues.put("firstName", studentName );
                contentValues.put("lastName", studentSurname );
                contentValues.put("phoneNumber", completePhoneNumber );
                contentValues.put("email", studentEmail );
                contentValues.put("password", studentPassword );
                contentValues.put("createdAt", dateTimeString);
                contentValues.put("userRole", studentRole);

                boolean result = myDB.insertData("User", contentValues);

                if (result) {

                    // Insert the user ID into the "studentTable"
                    int userID = myDB.getUserId(completePhoneNumber, studentPassword, studentRole);
                    ContentValues studentValues = new ContentValues();
                    studentValues.put("userID", userID );
                    boolean studentInsertResult  = myDB.insertData("Student", studentValues);

                    if (studentInsertResult) {
                        name.setText("");
                        surname.setText("");
                        phoneInput.setText("");
                        email.setText("");
                        password.setText("");
                        confirmPass.setText("");

//                        // Pass the userId to another activity
//                        Intent intent = new Intent(ActivityRegisterStudent.this, ActivityLogin.class);
//                        intent.putExtra("userId", userID);
//                        startActivity(intent);


                     AndroidUtil.showToast(getApplicationContext(), "Registered Student Successful!");
                    } else {
                        AndroidUtil.showToast(getApplicationContext(), "An unknown error has occurred while saving student data!");
                    }
                } else {
                    AndroidUtil.showToast(getApplicationContext(), "An unknown error has occurred during user registration!");
                }
            }
        });
        myDB.close();
    }
}