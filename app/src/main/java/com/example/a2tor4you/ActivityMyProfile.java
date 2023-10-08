package com.example.a2tor4you;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a2tor4you.utils.AndroidUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ActivityMyProfile extends AppCompatActivity {


    private String userName,profileName,profileSurname,profileEmail, profileNumber;
    private String dob,Gender,province,city, School;

    public static final int CAMERA_ACTION_CODE = 1;
    ImageView imageProfile;

    ImageButton btnBack;
    DBHelper dbHelper ;


    Calendar calendar = Calendar.getInstance();
    Spinner gender, spinnerProvince, spinnerCity;
    EditText txt_ProfileName, txt_ProfileSurname, txtPhoneNum, txtProEmail, school;
    Button btnStudTakePhoto,  btnSaveProfile, takePhoto, btnPickDOB;
    DBHelper myDB;
    static String studentName, studentSurname, completePhoneNumber, studentEmail;
    static String studentDOB, studentGender, studentProvince , studentCity, studentSchool;
    // one boolean variable to check whether all the text fields
    // are filled by the user, properly or not.
    boolean isAllFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        dbHelper = new DBHelper(this);

        myDB = new DBHelper(this); // Initialize myDB


        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int loggedInUserId = preferences.getInt("loggedInUserId", -1); // -1 is a default value if key not found


        //Get all profile edits/spinners/buttons...

        btnBack = findViewById(R.id.btnBackMyProfile);
        txt_ProfileName = findViewById(R.id.txt_ProfileName);
        txt_ProfileSurname = findViewById(R.id.txt_ProfileSurname);
        btnPickDOB = findViewById(R.id.btnPickDOB);
        gender = findViewById(R.id.spinGender);
        txtPhoneNum = findViewById(R.id.txtPhoneNum);
        txtProEmail = findViewById(R.id.txtProEmail);
        spinnerProvince = findViewById(R.id.spinProvince);
        spinnerCity = findViewById(R.id.spinCity);
        school = findViewById(R.id.txtSchool);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);
       // ArrayAdapter<CharSequence> cityAdapter = (ArrayAdapter<CharSequence>) spinnerCity.getAdapter();

        //Functionality of btn back
        btnBack.setOnClickListener(view -> startActivity(new Intent(ActivityMyProfile.this, ActivityAccount.class)));

        //Functionality of btn DOB
        btnPickDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        // Use the CitySpinnerHelper to set up the city spinner
     //   SouthAfricaData.setupCitySpinner(this, spinnerProvince, spinnerCity);

        /* imageProfile = findViewById(R.id.ImgAccount);*/ //Adds image to Account Profile pic
//        btnStudTakePhoto = findViewById(R.id.btnStudTakePhoto);
//
//        btnStudTakePhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivityForResult(intent, CAMERA_ACTION_CODE);
//                } else {
//                    Toast.makeText(ActivityMyProfile.this, "There is no app that supports this action", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });


        //Adding User Table info to editBoxes
        if (loggedInUserId != -1) {
            // Fetch user's name and surname from the database based on userID
            userName = dbHelper.getUserName(loggedInUserId); // Implement this method
            String[] nameSurnameArray = userName.split(" ");

            // Separate name and surname as separate strings
            if (nameSurnameArray.length == 2) {
                profileName = nameSurnameArray[0];
                profileSurname = nameSurnameArray[1];

                txt_ProfileName.setText(profileName);
                txt_ProfileSurname.setText(profileSurname);


            } else {
                // Handle the case where the userName format is unexpected
                Toast.makeText(ActivityMyProfile.this, "Unexpected error", Toast.LENGTH_SHORT).show();
            }

            profileEmail = dbHelper.getField("User", loggedInUserId, "email");
            txtProEmail.setText(profileEmail);

            profileNumber = dbHelper.getField("User", loggedInUserId, "phoneNumber");
            if (profileNumber != null && profileNumber.startsWith("+27")) {
                profileNumber = profileNumber.substring(3); // Remove the first three characters
            }
            txtPhoneNum.setText(profileNumber);

        } else {
            Toast.makeText(ActivityMyProfile.this, "Unexpected error", Toast.LENGTH_SHORT).show();
            //welcome.setText("Guest"); // Display a default value or handle it as needed
        }

        //Adding Student Table info to editBoxes
        if (loggedInUserId != -1) {
            // Fetch user's name and surname from the database based on userID
            dob = dbHelper.getField("Student", loggedInUserId,"dob");
            Gender = dbHelper.getField("Student", loggedInUserId,"gender");
            province = dbHelper.getField("Student", loggedInUserId,"province");
            city = dbHelper.getField("Student", loggedInUserId,"city");
            School = dbHelper.getField("Student", loggedInUserId,"school");


            boolean isUserExist = dbHelper.isUserIDExists("Student", loggedInUserId);

            // Separate name and surname as separate strings
            if (isUserExist) {
                btnPickDOB.setText(dob);

                int positionGen = getIndex(gender, Gender);
                if (positionGen != -1) {
                    gender.setSelection(positionGen);
                }

                int positionProv = getIndex(spinnerProvince, province);
                if (positionProv != -1) {
                    spinnerProvince.setSelection(positionProv);
                }


                int positionCity = getIndex(spinnerCity, city);
                if (positionCity != -1) {
                    // Set the selected city in the spinner
                    spinnerCity.setSelection(positionCity);
                }


                school.setText(School);

            } else {
                // Handle the case where the userName format is unexpected
                // Toast.makeText(ActivityMyProfile.this, "Unexpected error", Toast.LENGTH_SHORT).show();
            }


        } else {
            //  Toast.makeText(ActivityMyProfile.this, "Unexpected error", Toast.LENGTH_SHORT).show();
            //welcome.setText("Guest"); // Display a default value or handle it as needed
        }


        // handle the SAVE button
        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // store the returned value of the dedicated function which checks
                // whether the entered data is valid or if any fields are left blank.


//
                // Update User Table if they change they info
                studentName = txt_ProfileName.getText().toString();
                studentSurname = txt_ProfileSurname.getText().toString();
                completePhoneNumber = "+27" + txtPhoneNum.getText().toString();
                studentEmail = txtProEmail.getText().toString();


                ContentValues contentValues = new ContentValues();

                contentValues.put("firstName", studentName);

                contentValues.put("lastName", studentSurname);

                contentValues.put("phoneNumber", completePhoneNumber);

                contentValues.put("email", studentEmail);


                    boolean isUserUpdated = dbHelper.updateUserData("User", loggedInUserId, contentValues);

                    if (isUserUpdated) {
                        Toast.makeText(ActivityMyProfile.this, "Profile Successfully Updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ActivityMyProfile.this, "Unexpected error", Toast.LENGTH_SHORT).show();
                    }


                // Insert data in Student table where the UserID matches
                studentDOB = btnPickDOB.getText().toString();
                studentGender = gender.getSelectedItem().toString();
                studentProvince = spinnerProvince.getSelectedItem().toString();
                studentCity = spinnerCity.getSelectedItem().toString();
                studentSchool = school.getText().toString();

                ContentValues studentValues = new ContentValues();
                studentValues.put("dob", studentDOB);
                studentValues.put("gender", studentGender);
                studentValues.put("province", studentProvince);
                studentValues.put("city", studentCity);
                studentValues.put("school", studentSchool);


                boolean result = myDB.updateUserData("Student", loggedInUserId, studentValues);

                if (isAllFieldsChecked) {

                    if (result) {

                        // the boolean variable turns to be true then
                        // only the user must be proceed to the activity2
                        Toast.makeText(ActivityMyProfile.this, "Profile Successfully Updated", Toast.LENGTH_SHORT).show();

                    } else {
                        AndroidUtil.showToast(getApplicationContext(), "An unknown error has occurred while saving student data!");
                    }
                }

            }


        });


    }




    @Override
    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_ACTION_CODE && resultCode == RESULT_OK && data != null) {
            Bundle bundle = data.getExtras();
            Bitmap finalPhoto = (Bitmap) bundle.get("data");
            imageProfile.setImageBitmap(finalPhoto);
        }
    }


    // function which checks all the text fields
    // are filled or not by the user.
    // when user clicks on the PROCEED button
    // this function is triggered.
    private boolean CheckAllFields() {
        if (txt_ProfileName.length() == 0) {
            txt_ProfileName.setError("This field is required");
            return false;
        }

        if (txt_ProfileSurname.length() == 0) {
            txt_ProfileSurname.setError("This field is required");
            return false;
        }

        if (txtPhoneNum.length() == 0) {
            txtPhoneNum.setError("This field is required");
            return false;
        }

        if (txtProEmail.length() == 0) {
            txtProEmail.setError("This field is required");
            return false;
        }

        // after all validation return true.
        return true;
    }

    private void showDatePicker() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        calendar.set(selectedYear, selectedMonth, selectedDay);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                        String formattedDate = dateFormat.format(calendar.getTime());
                        btnPickDOB.setText(formattedDate);
                    }
                },
                year, month, day
        );

        datePickerDialog.show();
    }



    private int getIndex(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            String item = spinner.getItemAtPosition(i).toString();
//            Log.d("Debug", "Item at position " + i + ": " + item);
            if (item.equals(value)) {
                return i;
            }}
            return -1; // Not found
        }


    }

