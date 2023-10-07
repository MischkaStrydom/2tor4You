package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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
import java.util.regex.Pattern;

public class ActivityRegisterStudent extends AppCompatActivity {

    /*// button
    Button btnStudentSignUp;

    // Text fields
    EditText txt_StudentFirstName, txt_StudentLastName, txt_StudentPhoneNum, txtStudentEmail, txtRegisterStudentPassword, txtConfirmRegisterStudentPassword;

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

    *//* private TextInputLayout email;*//*
    *//* private TextInputLayout password;*/

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
                    boolean studentPreffs  = myDB.insertData("NotificationPreference", studentValues);

                    if (studentInsertResult && studentPreffs) {
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


        Button signUp = findViewById(R.id.btnStudentSignUp);

        /*// handle the PROCEED button
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // store the returned value of the dedicated function which checks
                // whether the entered data is valid or if any fields are left blank.
                isAllFieldsChecked = CheckAllFields();

                // the boolean variable turns to be true then
                // only the user must be proceed to the activity2
                if (isAllFieldsChecked) {
                    Intent i = new Intent(ActivityRegisterStudent.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });*/
    }


    /*// function which checks all the text fields
    // are filled or not by the user.
    // when user clicks on the PROCEED button
    // this function is triggered.
    private boolean CheckAllFields() {
        if (txt_StudentFirstName.length() == 0) {
            txt_StudentFirstName.setError("This field is required");
            return false;
        }

        if (txt_StudentLastName.length() == 0) {
            txt_StudentLastName.setError("This field is required");
            return false;
        }

        if (txt_StudentPhoneNum.length() == 0) {
            txt_StudentPhoneNum.setError("This field is required");
            return false;
        }

        if (txtStudentEmail.length() == 0) {
            txtStudentEmail.setError("This field is required");
            return false;
        }

        if (txtRegisterStudentPassword.length() == 0) {
            txtRegisterStudentPassword.setError("This field is required");
            return false;
        }

        if (txtConfirmRegisterStudentPassword.length() == 0) {
            txtConfirmRegisterStudentPassword.setError("This field is required");
            return false;
        }

        // after all validation return true.
        return true;
    }

    private boolean validateEmail() {

        // Extract input from EditText
        String emailInput = txtStudentEmail.getText().toString().trim();

        // if the email input field is empty
        if (emailInput.isEmpty()) {
            txtStudentEmail.setError("Field can not be empty");
            return false;
        }

        // Matching the input email to a predefined email pattern
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            txtStudentEmail.setError("Please enter a valid email address");
            return false;
        } else {
            txtStudentEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = txtRegisterStudentPassword.getText().toString().trim();
        // if password field is empty
        // it will display error message "Field can not be empty"
        if (passwordInput.isEmpty()) {
            txtRegisterStudentPassword.setError("Field can not be empty");
            return false;
        }

        // if password does not matches to the pattern
        // it will display an error message "Password is too weak"
        else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            txtRegisterStudentPassword.setError("Password is too weak");
            return false;
        } else {
            txtRegisterStudentPassword.setError(null);
            return true;
        }
    }

    public void confirmInput(View v) {
        if (!validateEmail() | !validatePassword()) {
            return;
        }

        // if the email and password matches, a toast message
        // with email and password is displayed
        String input = "Email: " + txtStudentEmail.getText().toString();
        input += "\n";
        input = "Password: " + txtRegisterStudentPassword.getText().toString();
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
    }*/
}