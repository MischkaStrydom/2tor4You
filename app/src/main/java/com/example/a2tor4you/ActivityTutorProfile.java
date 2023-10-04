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

public class ActivityTutorProfile extends AppCompatActivity {
    Context context;
    DBHelper dbHelper ;
    public static final int CAMERA_ACTION_CODE = 1;
    ImageView imageProfile;

    EditText txtTutName, txtTutLastName, txtPhoneNum, txtTutEmail;
    Button takePhoto, btnPickDOB;
    Calendar calendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_profile);

        dbHelper = new DBHelper(this);




        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int loggedInUserId = preferences.getInt("loggedInUserId", -1); // -1 is a default value if key not found



        String[] subjects = context.getResources().getStringArray(R.array.spinSubjectTeach);
        ContentValues contentValues = new ContentValues();

        for (String subject : subjects) {
            contentValues.put("subject", subject);
            dbHelper.insertData("Subject", contentValues);
        }



        txtTutName = findViewById(R.id.txtTutName);
        txtTutLastName = findViewById(R.id.txtTutLastName);
        txtPhoneNum = findViewById(R.id.txtPhoneNum);
        txtTutEmail = findViewById(R.id.txtTutEmail);

        ImageButton btnBack = findViewById(R.id.btnBackTutorsProfile);
        btnBack.setOnClickListener(view -> startActivity(new Intent(ActivityTutorProfile.this,ActivityAccount.class)));

        // Province and city spinners
        Spinner spinnerProvince = findViewById(R.id.spinProvince);
        Spinner spinnerCity = findViewById(R.id.spinCity);

        // Use the CitySpinnerHelper to set up the city spinner
        SouthAfricaData.setupCitySpinner(this, spinnerProvince, spinnerCity);

        btnPickDOB = findViewById(R.id.btnPickDOB);

        btnPickDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

       /* imageProfile = findViewById(R.id.ImgAccount);*/ //Adds image to Account Profile pic
        takePhoto = findViewById(R.id.btnTutorTakePhoto);

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(intent, CAMERA_ACTION_CODE   );
                }
                else{
                    Toast.makeText(ActivityTutorProfile.this, "There is no app that supports this action", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

   /* // function which checks all the text fields
    // are filled or not by the user.
    // when user clicks on the PROCEED button
    // this function is triggered.
    private boolean CheckAllFields() {
        if (txtTutName.length() == 0) {
            txtTutName.setError("This field is required");
            return false;
        }

        if (txtTutLastName.length() == 0) {
            txtTutLastName.setError("This field is required");
            return false;
        }

        if (txtPhoneNum.length() == 0) {
            txtPhoneNum.setError("This field is required");
            return false;
        }

        if (txtTutEmail.length() == 0) {
            txtTutEmail.setError("This field is required");
            return false;
        }

        // after all validation return true.
        return true;
    }*/

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