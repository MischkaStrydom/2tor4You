package com.example.a2tor4you;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ActivityMyProfile extends AppCompatActivity {

    public static final int CAMERA_ACTION_CODE = 1;
    ImageView imageProfile;
    Button takePhoto;
    ImageButton btnBack;
    DBHelper dbHelper ;

     Button btnPickDOB;
     Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        dbHelper = new DBHelper(this);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int loggedInUserId = preferences.getInt("loggedInUserId", -1); // -1 is a default value if key not found

        EditText name = findViewById(R.id.txt_ProfileName);
        EditText surname = findViewById(R.id.txt_ProfileSurname);
        btnPickDOB = findViewById(R.id.btnPickDOB);

        btnPickDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        Spinner gender = findViewById(R.id.spinGender);
        EditText phoneNum = findViewById(R.id.txtPhoneNum);  // edit the phoneNum from db
        EditText email = findViewById(R.id.txtProEmail);



        btnBack = findViewById(R.id.btnBackMyProfile);
        btnBack.setOnClickListener(view -> startActivity(new Intent(ActivityMyProfile.this,ActivityAccount.class)));


        // Province and city spinners
        Spinner spinnerProvince = findViewById(R.id.spinProvince);
        Spinner spinnerCity = findViewById(R.id.spinCity);

        // Use the CitySpinnerHelper to set up the city spinner
        SouthAfricaData.setupCitySpinner(this, spinnerProvince, spinnerCity);

        EditText school = findViewById(R.id.txtSchool);


        /* imageProfile = findViewById(R.id.ImgAccount);*/ //Adds image to Account Profile pic
        takePhoto = findViewById(R.id.btnStudTakePhoto);

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(intent, CAMERA_ACTION_CODE   );
                }
                else{
                    Toast.makeText(ActivityMyProfile.this, "There is no app that supports this action", Toast.LENGTH_SHORT).show();
                }

            }
        });



        //Adding User Table info to editBoxes
        if (loggedInUserId != -1) {
            // Fetch user's name and surname from the database based on userID
            String userName = dbHelper.getUserName(loggedInUserId); // Implement this method
            String[] nameSurnameArray = userName.split(" ");

            // Seperate name and surname as separate strings
            if (nameSurnameArray.length == 2) {
                String profileName = nameSurnameArray[0];
                String profileSurname = nameSurnameArray[1];

                name.setText(profileName);
                surname.setText(profileSurname);


            } else {
                // Handle the case where the userName format is unexpected
                Toast.makeText(ActivityMyProfile.this, "Unexpected error", Toast.LENGTH_SHORT).show();
            }

            String profileEmail = dbHelper.getEmail(loggedInUserId);
            email.setText(profileEmail);

            String profileNumber = dbHelper.getPhoneNumber(loggedInUserId);
            if (profileNumber != null && profileNumber.startsWith("+27")) {
                profileNumber = profileNumber.substring(3); // Remove the first three characters
            }
            phoneNum.setText(profileNumber);

        } else {
            Toast.makeText(ActivityMyProfile.this, "Unexpected error", Toast.LENGTH_SHORT).show();
            //welcome.setText("Guest"); // Display a default value or handle it as needed
        }




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_ACTION_CODE && resultCode == RESULT_OK && data != null){
            Bundle bundle = data.getExtras();
            Bitmap finalPhoto = (Bitmap) bundle.get("data");
            imageProfile.setImageBitmap(finalPhoto);
        }
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
}