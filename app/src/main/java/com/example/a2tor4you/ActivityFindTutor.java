package com.example.a2tor4you;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class ActivityFindTutor extends AppCompatActivity {

    ArrayList<String>tutorID, firstName, lastname, YearsOfExperience ,TotalTutorHours , pricePerHour;
    private ArrayList<byte[]> imageBytes;
    adapterTutor adapterTutor;

    ArrayList<String> listItem;
    ArrayAdapter adapter;

    ListView user_list;

    DBHelper dbHelper ;

    View _baseView;

    RecyclerView rvFindTutor;
    static String phoneNumber;
    static String password;
    static String selectedRole;
    BottomNavigationView bottomNavigationView;

    static int loggedInUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_tutor);

         phoneNumber = getIntent().getStringExtra("phone");

        ImageView filter = findViewById(R.id.filter);
        filter.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),ActivityGradeAndSubject.class));
        });

        dbHelper = new DBHelper(this);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        loggedInUserId = preferences.getInt("loggedInUserId", -1); // -1 is a default value if key not found

        //Testing recycle view
        dbHelper = new DBHelper(ActivityFindTutor.this);


        tutorID = new ArrayList<>();
        firstName = new ArrayList<>();
        lastname = new ArrayList<>();
        YearsOfExperience = new ArrayList<>();
        TotalTutorHours = new ArrayList<>();
        pricePerHour = new ArrayList<>();
        imageBytes = new ArrayList<>();

        rvFindTutor = findViewById(R.id.rvFindTutor);

        storeDataInArrays();
        adapterTutor = new adapterTutor(ActivityFindTutor.this,tutorID, firstName, lastname, YearsOfExperience, TotalTutorHours, pricePerHour, imageBytes);
        rvFindTutor.setAdapter(adapterTutor);
        rvFindTutor.setLayoutManager(new LinearLayoutManager(ActivityFindTutor.this));

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.findTutors);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.findTutors:
                        startActivity(new Intent(getApplicationContext(),ActivityFindTutor.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),ActivityHomeStudent.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.event:
                        startActivity(new Intent(getApplicationContext(),ActivityEditEvent.class));
                        overridePendingTransition(0,0);
                        return true;


                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(),ActivityAccount.class));
                        overridePendingTransition(0,0);
                        return true;



                }
                return false;
            }
        });

    }
//    void storeDataInArrays()
//    {
//        Cursor cursor = dbHelper.viewTutorData();
//
//        if (cursor.getCount() == 0) {
//            Toast.makeText(ActivityFindTutor.this, "No tutors to show", Toast.LENGTH_LONG).show();
//        } else {
//            while (cursor.moveToNext()) {
//                tutorID.add(cursor.getString(0)); // eventTitle
//                firstName.add(cursor.getString(1)); // eventTitle
//                lastname.add(cursor.getString(2)); // eventDate
//                YearsOfExperience.add(cursor.getString(3)); // eventTime
//                TotalTutorHours.add(cursor.getString(4)); // eventNotes
//                pricePerHour.add(cursor.getString(5)); // eventNotes
//                byte[] imageBlob = cursor.getBlob(6);
//                imageBytes.add(imageBlob);
//
//
//            }
//
//        }
//
//
//    }

    void storeDataInArrays() {
        Cursor cursor = dbHelper.viewTutorData();

        // Check if filter values are provided
        boolean hasFilters = getIntent().hasExtra("selectedSubjects") ||
                getIntent().hasExtra("selectedGrades") ||
                getIntent().hasExtra("onlineFilter") ||
                getIntent().hasExtra("inPersonFilter") ||
                getIntent().hasExtra("qualifiedTeacherFilter") ||
                getIntent().hasExtra("maxPricePerHour");

        if (cursor.getCount() == 0) {
            Toast.makeText(ActivityFindTutor.this, "No tutors were found", Toast.LENGTH_LONG).show();
        } else {

            Boolean isTutorFound = false;
            while (cursor.moveToNext()) {
                if (hasFilters) {
                    // Get the filter criteria from the Intent
                    ArrayList<String> selectedSubjects = getIntent().getStringArrayListExtra("selectedSubjects");
                    ArrayList<String> selectedGrades = getIntent().getStringArrayListExtra("selectedGrades");
                    boolean onlineFilter = getIntent().getBooleanExtra("onlineFilter", false);
                    boolean inPersonFilter = getIntent().getBooleanExtra("inPersonFilter", false);
                    boolean qualifiedTeacherFilter = getIntent().getBooleanExtra("qualifiedTeacherFilter", false);
                    int maxPricePerHour = getIntent().getIntExtra("maxPricePerHour", 0);

                    // Apply your filtering logic here based on the filter criteria
                    int tutorId = Integer.parseInt(cursor.getString(0));
                    float price = Float.parseFloat(cursor.getString(5));
                    int locationOnline = Integer.parseInt(cursor.getString(7));
                    int locationOffline = Integer.parseInt(cursor.getString(8));
                    int extraQualifiedTeacher = Integer.parseInt(cursor.getString(9));

                    // Check if the tutor meets the filter criteria
                    boolean meetsCriteria = true;
//
//                    if (!selectedSubjects.isEmpty()) {
//                        meetsCriteria = selectedSubjects.contains(cursor.getString(subjectNameIndex));
//                    }
//
//                    if (!selectedGrades.isEmpty()) {
//                        meetsCriteria = meetsCriteria && selectedGrades.contains(cursor.getString(gradeIndex));
//                    }

                    if (onlineFilter) {
                        // Apply the online filter
                        meetsCriteria = meetsCriteria && (locationOnline == 1);
                    }


                    if (inPersonFilter) {
                        // Apply the in-person filter

                        meetsCriteria = meetsCriteria && (locationOffline == 1);
                    }

                    if (qualifiedTeacherFilter) {

                        meetsCriteria = meetsCriteria && (extraQualifiedTeacher == 1);
                    }

                    if (maxPricePerHour > 0) {

                        meetsCriteria = meetsCriteria && (price <= maxPricePerHour);
                    }

                    // If the tutor meets all the filter criteria, add them to the display
                    if (meetsCriteria) {
                        tutorID.add(cursor.getString(0)); // tutorID
                        firstName.add(cursor.getString(1)); // firstName
                        lastname.add(cursor.getString(2)); // lastname
                        YearsOfExperience.add(cursor.getString(3)); // YearsOfExperience
                        TotalTutorHours.add(cursor.getString(4)); // TotalTutorHours
                        pricePerHour.add(cursor.getString(5)); // pricePerHour
                        byte[] imageBlob = cursor.getBlob(6);  // profile pic
                        imageBytes.add(imageBlob);
                        isTutorFound = true;

                    }


                } else {
                    // Add all tutors to the display (no filtering)
                    tutorID.add(cursor.getString(0)); // tutorID
                    firstName.add(cursor.getString(1)); // firstName
                    lastname.add(cursor.getString(2)); // lastname
                    YearsOfExperience.add(cursor.getString(3)); // YearsOfExperience
                    TotalTutorHours.add(cursor.getString(4)); // TotalTutorHours
                    pricePerHour.add(cursor.getString(5)); // pricePerHour
                    byte[] imageBlob = cursor.getBlob(6);  // profile pic
                    imageBytes.add(imageBlob);
                    isTutorFound = true;

                }
//                if (!isTutorFound) {
//                    Toast.makeText(ActivityFindTutor.this, "No tutors match the filters", Toast.LENGTH_LONG).show();
//                }


            }
        }
    }




}
