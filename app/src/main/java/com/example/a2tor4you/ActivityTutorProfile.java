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
import android.os.Trace;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2tor4you.utils.AndroidUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ActivityTutorProfile extends AppCompatActivity {
    boolean isCheckedLocationOnline;
    boolean isCheckedLocationOffline;
    boolean isCheckedTeacher;
    Context context;
    DBHelper dbHelper ;
    public static final int CAMERA_ACTION_CODE = 1;
    ImageView imageProfile;

     String userName,profileName,profileSurname,profileEmail, profileNumber;
     String dob,Gender,province, city, School, Uni, extraNotes, about, SUBJECT, GRADE;
     Integer YearsOfExperience, TotalStudentTaught;
     Float totalTutorHours, pricePerHour;
     Boolean locationOnline, locationOffline, extraQualifiedTeacher;

    Spinner gender, spinnerProvince, spinnerCity, spinnerGrade, spinnerSubjects;
    EditText txtTutName, txtTutLastName, txtPhoneNum, txtTutEmail, school, uni, notes, yearsExperience, totalTutHours,
            totalStudents, aboutMe, priceHour;
    Button takePhoto, btnPickDOB, btnSaveTutProfile, btnAdd;


    Calendar calendar = Calendar.getInstance();


    static String tutorName, tutorSurname, completePhoneNumber, tutorEmail;
    static String tutorDOB, tutorGender, tutorProvince , tutorCity, tutorSchool,
            tutorUni, tutorAboutMe, tutorNotes;
    static Integer tutorExperience, studentsTaught;
    static Boolean tutorOnline, tutorOffline, tutorIsTeacher,
            isOnlineChecked, isOfflineChecked, isTeacherChecked;
    static Float tutorHours, tutorPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_profile);

        dbHelper = new DBHelper(this);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int loggedInUserId = preferences.getInt("loggedInUserId", -1); // -1 is a default value if key not found

        context = this;

        String[] subjects = context.getResources().getStringArray(R.array.spinSubjectTeach);
        ContentValues contentValues = new ContentValues();

        for (String subject : subjects) {
            contentValues.clear(); // Clear the previous data in ContentValues
            contentValues.put("subject", subject);
            dbHelper.insertData("Subject", contentValues);
        }

//        String[] subjects = context.getResources().getStringArray(R.array.spinSubjectTeach);
//        ContentValues contentValues = new ContentValues();
//
//        for (String subject : subjects) {
//            contentValues.put("subject", subject);
//            dbHelper.insertData("Subject", contentValues);
//        }

        ImageButton btnBack = findViewById(R.id.btnBackTutorsProfile);
        btnBack.setOnClickListener(view -> startActivity(new Intent(ActivityTutorProfile.this,ActivityAccount.class)));

        txtTutName = findViewById(R.id.txtTutName);
        txtTutLastName = findViewById(R.id.txtTutLastName);
        btnPickDOB = findViewById(R.id.btnPickDOB);
        gender = findViewById(R.id.spinGender);

        spinnerProvince = findViewById(R.id.spinProvince);
        spinnerCity = findViewById(R.id.spinCity);

        spinnerSubjects = findViewById(R.id.spinSubjectTeach);
        spinnerGrade = findViewById(R.id.spinLevelEdu);
        TextView txtSubjects = findViewById(R.id.txtSubjects);

        school = findViewById(R.id.txtTutSchool);
        uni = findViewById(R.id.txtTutUni);

        txtPhoneNum = findViewById(R.id.txtPhoneNum);
        txtTutEmail = findViewById(R.id.txtTutEmail);

        yearsExperience = findViewById(R.id.txtTutYearsExp);
        totalTutHours = findViewById(R.id.txtTotalTutHrs);
        totalStudents = findViewById(R.id.txtTutTotalStudTaught);
        aboutMe = findViewById(R.id.txtTutAboutMe);
        priceHour = findViewById(R.id.txtPricePerHr);
        btnAdd = findViewById(R.id.btnAddTutSubjGrade);

        final CheckBox online = findViewById(R.id.rdoTeachOnline);
        final CheckBox Offline = findViewById(R.id.rdoTeachInPerson);
        final CheckBox teacher = findViewById(R.id.rdoTutQualifiedTeacher);

        notes = findViewById(R.id.txtExtraNotes);
        btnSaveTutProfile =findViewById(R.id.btnSaveTutProfile);


        // Use the CitySpinnerHelper to set up the city spinner
        //SouthAfricaData.setupCitySpinner(this, spinnerProvince, spinnerCity);

        btnPickDOB = findViewById(R.id.btnPickDOB);

        btnPickDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        int tutorID = dbHelper.getTutorId(loggedInUserId);


        //Adding Student Table info to editBoxes
        if (loggedInUserId != -1) {

            List<SubjectGrade> subjectGrades  = dbHelper.getSubjectsAndGradesForTutor(tutorID);

        // Initialize a StringBuilder to build the final text
            StringBuilder subjectText = new StringBuilder();

        // Iterate through the list and concatenate subjects and grades
            for (SubjectGrade subjectGrade : subjectGrades) {
                String subject = subjectGrade.getSubject();
                String grade = subjectGrade.getGrade();

                // Append the subject and grade to the StringBuilder
                subjectText.append("Subject: ").append(subject).append("- Grade: ").append(grade).append(" | ");
            }

// Set the concatenated text to the TextView
            txtSubjects.setText(subjectText.toString());

        } else {
            //  Toast.makeText(ActivityMyProfile.this, "Unexpected error", Toast.LENGTH_SHORT).show();
            //welcome.setText("Guest"); // Display a default value or handle it as needed
        }


        DBHelper myDB = new DBHelper(this); // Initialize myDB
        btnAdd.setOnClickListener(v -> {


            SUBJECT = spinnerSubjects.getSelectedItem().toString();
            GRADE = spinnerGrade.getSelectedItem().toString();


            ContentValues subjectValues = new ContentValues();
            subjectValues.put("tutorID", tutorID);
            subjectValues.put("subjectName", SUBJECT);
            subjectValues.put("grade", GRADE);

            boolean isUserUpdated = myDB.insertData("TutorSubject", subjectValues);

            if (isUserUpdated) {
                Toast.makeText(ActivityTutorProfile.this, "Subject and Grade added Successfully", Toast.LENGTH_SHORT).show();
                spinnerSubjects.setSelection(0);
                spinnerGrade.setSelection(0);

                txtSubjects.setText(txtSubjects.getText() + "Subject: " + SUBJECT + "- Grade: " + GRADE + ", ");


            } else {
                Toast.makeText(ActivityTutorProfile.this, "Unexpected error", Toast.LENGTH_SHORT).show();
            }


        });

//       /* imageProfile = findViewById(R.id.ImgAccount);*/ //Adds image to Account Profile pic
//        takePhoto = findViewById(R.id.btnTutorTakePhoto);
//
//        takePhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if(intent.resolveActivity(getPackageManager()) != null){
//                    startActivityForResult(intent, CAMERA_ACTION_CODE   );
//                }
//                else{
//                    Toast.makeText(ActivityTutorProfile.this, "There is no app that supports this action", Toast.LENGTH_SHORT).show();
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

                txtTutName.setText(profileName);
                txtTutLastName.setText(profileSurname);


            } else {
                // Handle the case where the userName format is unexpected
                Toast.makeText(ActivityTutorProfile.this, "Unexpected error", Toast.LENGTH_SHORT).show();
            }

            profileEmail = dbHelper.getField("User", loggedInUserId, "email");
            txtTutEmail.setText(profileEmail);

            profileNumber = dbHelper.getField("User", loggedInUserId, "phoneNumber");
            if (profileNumber != null && profileNumber.startsWith("+27")) {
                profileNumber = profileNumber.substring(3); // Remove the first three characters
            }
            txtPhoneNum.setText(profileNumber);


        } else {
            Toast.makeText(ActivityTutorProfile.this, "Unexpected error", Toast.LENGTH_SHORT).show();
            //welcome.setText("Guest"); // Display a default value or handle it as needed
        }

        //Adding Tutor Table info to editBoxes
        if (loggedInUserId != -1) {

            dob = dbHelper.getField("Tutor", loggedInUserId,"dob");
            Gender = dbHelper.getField("Tutor", loggedInUserId,"gender");
            province = dbHelper.getField("Tutor", loggedInUserId,"province");
            city = dbHelper.getField("Tutor", loggedInUserId,"city");
            School = dbHelper.getField("Tutor", loggedInUserId,"school");
            Uni = dbHelper.getField("Tutor", loggedInUserId,"uni");
            YearsOfExperience = dbHelper.getFieldAsInt("Tutor", loggedInUserId,"YearsOfExperience");
            totalTutorHours = dbHelper.getFieldAsFloat("Tutor", loggedInUserId, "TotalTutorHours");
            TotalStudentTaught = dbHelper.getFieldAsInt("Tutor", loggedInUserId,"TotalStudentTaught");
            about = dbHelper.getField("Tutor", loggedInUserId,"aboutMe");
            pricePerHour = dbHelper.getFieldAsFloat("Tutor", loggedInUserId,"pricePerHour");
            locationOnline = dbHelper.getInfo("Tutor", loggedInUserId, "locationOnline");
            locationOffline = dbHelper.getInfo("Tutor", loggedInUserId,"locationOffline");
            extraQualifiedTeacher = dbHelper.getInfo("Tutor", loggedInUserId,"extraQualifiedTeacher");
            extraNotes = dbHelper.getField("Tutor", loggedInUserId, "extraNotes");


            boolean isUserExist = dbHelper.isUserIDExists("Tutor", loggedInUserId);
            online.setChecked(locationOnline);

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
                uni.setText(Uni);
                notes.setText(extraNotes);
                aboutMe.setText(about);

                yearsExperience.setText(String.valueOf(YearsOfExperience));
                totalStudents.setText(String.valueOf(TotalStudentTaught));
                totalTutHours.setText(String.valueOf(totalTutorHours));
                priceHour.setText(String.valueOf(pricePerHour));

                online.setChecked(locationOnline);
                Offline.setChecked(locationOffline);
                teacher.setChecked(extraQualifiedTeacher);


            }


        } else {
              Toast.makeText(ActivityTutorProfile.this, "Unexpected error", Toast.LENGTH_SHORT).show();
            //welcome.setText("Guest"); // Display a default value or handle it as needed
        }




        btnSaveTutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // store the returned value of the dedicated function which checks
                // whether the entered data is valid or if any fields are left blank.

                int tutorID = dbHelper.getTutorId(loggedInUserId);

                // Start ActivityTutorProfileView and pass the tutor's ID as an extra
                Intent intent = new Intent(ActivityTutorProfile.this, ActivityTutorProfileView.class);
                intent.putExtra("tutorId", tutorID);
                startActivity(intent);

                //Get state of online
                if (online.isChecked()) {
                    isCheckedLocationOnline = true;
                } else {
                    isCheckedLocationOnline = false;

                }
                //Get state of offline
                if (Offline.isChecked()) {
                    isCheckedLocationOffline = true;
                } else {
                    isCheckedLocationOffline = false;
                }
                //Get state of offline
                if (teacher.isChecked()) {
                    isCheckedTeacher = true;
                } else {
                    isCheckedTeacher = false;
                }


                // Update User Table if they change they info
                tutorName = txtTutName.getText().toString();
                tutorSurname = txtTutLastName.getText().toString();
                completePhoneNumber = "+27" + txtPhoneNum.getText().toString();
                tutorEmail = txtTutEmail.getText().toString();

                ContentValues contentValues = new ContentValues();
                contentValues.put("firstName", tutorName);
                contentValues.put("lastName", tutorSurname);
                contentValues.put("phoneNumber", completePhoneNumber);
                contentValues.put("email", tutorEmail);


                boolean isUserUpdated = dbHelper.updateUserData("User", loggedInUserId, contentValues);

                if (isUserUpdated) {
                    Toast.makeText(ActivityTutorProfile.this, "Profile Successfully Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ActivityTutorProfile.this, "Unexpected error", Toast.LENGTH_SHORT).show();
                }


                // Insert data in Tutor table where the UserID matches
                tutorDOB = btnPickDOB.getText().toString();
                tutorGender = gender.getSelectedItem().toString();
                tutorProvince = spinnerProvince.getSelectedItem().toString();
                tutorCity = spinnerCity.getSelectedItem().toString();
                tutorSchool = school.getText().toString();
                tutorUni = uni.getText().toString();
                tutorAboutMe = aboutMe.getText().toString();
                tutorNotes = notes.getText().toString();
                tutorExperience = Integer.valueOf(yearsExperience.getText().toString());
                studentsTaught = Integer.valueOf(totalStudents.getText().toString());
                tutorHours = Float.valueOf(totalTutHours.getText().toString());
                tutorPrice = Float.valueOf(priceHour.getText().toString());
                tutorOnline = isCheckedLocationOnline;
                tutorOffline = isCheckedLocationOffline;
                tutorIsTeacher = isCheckedTeacher;

                ContentValues tutorValues = new ContentValues();
                tutorValues.put("dob", tutorDOB);
                tutorValues.put("gender", tutorGender);
                tutorValues.put("province", tutorProvince);
                tutorValues.put("city", tutorCity);
                tutorValues.put("school", tutorSchool);
                tutorValues.put("uni", tutorUni);
                tutorValues.put("YearsOfExperience", tutorExperience);
                tutorValues.put("TotalTutorHours", tutorHours);
                tutorValues.put("TotalStudentTaught", studentsTaught);
                tutorValues.put("aboutMe", tutorAboutMe);
                tutorValues.put("pricePerHour", tutorPrice);
                tutorValues.put("locationOnline", tutorOnline);
                tutorValues.put("locationOffline", tutorOffline);
                tutorValues.put("extraQualifiedTeacher", tutorIsTeacher);
                tutorValues.put("extraNotes", tutorNotes);


                boolean result = dbHelper.updateUserData("Tutor", loggedInUserId, tutorValues);

               // if (isAllFieldsChecked) {

                    if (result) {

                        // the boolean variable turns to be true then
                        // only the user must be proceed to the activity2
                        Toast.makeText(ActivityTutorProfile.this, "Profile Successfully Updated", Toast.LENGTH_SHORT).show();

                    } else {
                        AndroidUtil.showToast(getApplicationContext(), "An unknown error has occurred while saving student data!");
                    }
              //  }

            }


        });


    }


    // function which checks all the text fields
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

    private int getIndex(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            String item = spinner.getItemAtPosition(i).toString();
//            Log.d("Debug", "Item at position " + i + ": " + item);
            if (item.equals(value)) {
                return i;
            }}
        return -1; // Not found
    }
    public static class SubjectGrade {
        private String subject;
        private String grade;

        public SubjectGrade(String subject, String grade) {
            this.subject = subject;
            this.grade = grade;
        }

        public String getSubject() {
            return subject;
        }

        public String getGrade() {
            return grade;
        }
    }
}



