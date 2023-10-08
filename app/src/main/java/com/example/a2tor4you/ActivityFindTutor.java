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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActivityFindTutor extends AppCompatActivity {

    ArrayList<String> firstName, lastname, YearsOfExperience, TotalTutorHours, pricePerHour;
    ArrayList<Integer> tutorID;
    private ArrayList<byte[]> imageBytes;
    adapterTutor adapterTutor;

    ArrayList<String> listItem;
    ArrayAdapter adapter;

    ListView user_list;

    DBHelper dbHelper;

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
            startActivity(new Intent(getApplicationContext(), ActivityFilterTutors.class));
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
        adapterTutor = new adapterTutor(ActivityFindTutor.this, tutorID, firstName, lastname, YearsOfExperience, TotalTutorHours, pricePerHour, imageBytes);
        rvFindTutor.setAdapter(adapterTutor);
        rvFindTutor.setLayoutManager(new LinearLayoutManager(ActivityFindTutor.this));

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.findTutors);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.findTutors:
                        startActivity(new Intent(getApplicationContext(), ActivityFindTutor.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), ActivityHomeStudent.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.event:
                        startActivity(new Intent(getApplicationContext(), ActivityEditEvent.class));
                        overridePendingTransition(0, 0);
                        return true;


                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(), ActivityAccount.class));
                        overridePendingTransition(0, 0);
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


    //
    private void storeDataInArrays() {
        Cursor cursor = dbHelper.viewTutorData();
       // String selectedGrade = getIntent().getStringExtra("selectedGrade");

        boolean onlineFilter = getIntent().getBooleanExtra("onlineFilter", false);
        boolean inPersonFilter = getIntent().getBooleanExtra("inPersonFilter", false);
        boolean qualifiedTeacherFilter = getIntent().getBooleanExtra("qualifiedTeacherFilter", false);
        int maxPricePerHour = getIntent().getIntExtra("maxPricePerHour", 0);

        if (cursor.getCount() == 0) {
            Toast.makeText(ActivityFindTutor.this, "No tutors were found", Toast.LENGTH_LONG).show();
        } else {
            cursor.moveToFirst(); // Reset cursor position
            do {
                int tutorId = cursor.getInt(0);
                String tutorFirstName = cursor.getString(1);
                String tutorLastName = cursor.getString(2);
                String tutorYearsOfExperience = cursor.getString(3);
                String tutorTotalTutorHours = cursor.getString(4);
                String tutorPricePerHour = cursor.getString(5);
                byte[] tutorImageBlob = cursor.getBlob(6);
                boolean onlineAvailability = cursor.getInt(7) == 1;
                boolean inPersonAvailability = cursor.getInt(8) == 1;
                boolean isQualifiedTeacher = cursor.getInt(9) == 1;

                // Check if the tutor meets the grade filter criteria
//                boolean meetsGradeCriteria = true; // Assume all tutors meet the grade criteria by default
//                if (selectedGrade != null && !selectedGrade.isEmpty()) {
//                    // If a grade is selected, check if the tutor teaches that grade
//                    Cursor gradeTutors = dbHelper.viewTutorsBySubjectAndGrade( "Any" ,selectedGrade);
//                    if (gradeTutors == null || gradeTutors.getCount() == 0) {
//                        meetsGradeCriteria = false; // Tutor does not teach the selected grade
//                    }
//                    gradeTutors.close();
//                }

                // Check if the tutor meets the online availability filter criteria
                boolean meetsOnlineFilter = !onlineFilter || onlineAvailability;

                // Check if the tutor meets the in-person availability filter criteria
                boolean meetsInPersonFilter = !inPersonFilter || inPersonAvailability;

                // Check if the tutor meets the teacher qualification filter criteria
                boolean meetsQualifiedTeacherFilter = !qualifiedTeacherFilter || isQualifiedTeacher;

                // Check if the tutor meets the price filter criteria
                boolean meetsPriceFilter = maxPricePerHour == 0 || Float.parseFloat(tutorPricePerHour) <= maxPricePerHour;

                // If the tutor meets all filter criteria, add them to the display
                if (meetsOnlineFilter && meetsInPersonFilter && meetsQualifiedTeacherFilter && meetsPriceFilter) {
                    tutorID.add(tutorId);
                    firstName.add(tutorFirstName);
                    lastname.add(tutorLastName);
                    YearsOfExperience.add(tutorYearsOfExperience);
                    TotalTutorHours.add(tutorTotalTutorHours);
                    pricePerHour.add(tutorPricePerHour);
                    imageBytes.add(tutorImageBlob);
                }
            } while (cursor.moveToNext());

            cursor.close(); // Close the cursor when done
        }
    }
}




//    void storeDataInArrays() {
//        Cursor cursor = dbHelper.viewTutorData();
//        String MATH, HISTORY, AFRIKAANS, ENGLISH;
//        boolean tutorOffersMath, tutorOffersHistory, tutorOffersAfrikaans, tutorOffersEnglish;
//        // Check if filter values are provided
//        boolean hasFilters = getIntent().hasExtra("math") ||
//                getIntent().hasExtra("history") ||
//                getIntent().hasExtra("afrikaans") ||
//                getIntent().hasExtra("english") ||
//                getIntent().hasExtra("selectedGrade") ||
//                getIntent().hasExtra("onlineFilter") ||
//                getIntent().hasExtra("inPersonFilter") ||
//                getIntent().hasExtra("qualifiedTeacherFilter") ||
//                getIntent().hasExtra("maxPricePerHour");
//
//        if (cursor.getCount() == 0) {
//            Toast.makeText(ActivityFindTutor.this, "No tutors were found", Toast.LENGTH_LONG).show();
//        } else {
//            cursor.moveToFirst(); // Reset cursor position
//            do {
//                int tutorId = cursor.getInt(0);
//                String tutorFirstName = cursor.getString(1);
//                String tutorLastName = cursor.getString(2);
//                String tutorYearsOfExperience = cursor.getString(3);
//                String tutorTotalTutorHours = cursor.getString(4);
//                String tutorPricePerHour = cursor.getString(5);
//                byte[] tutorImageBlob = cursor.getBlob(6);
//
//                // By default, assume the tutor meets the criteria
//                boolean meetsCriteria = true;
//
//                if (hasFilters) {
//                    // Apply filtering logic based on the filter criteria
//
//                    boolean math = getIntent().getBooleanExtra("math",false);
//                    boolean history = getIntent().getBooleanExtra("history",false);
//                    boolean afrikaans = getIntent().getBooleanExtra("afrikaans",false);
//                    boolean english = getIntent().getBooleanExtra("english",false);
//                    String selectedGrade = getIntent().getStringExtra("selectedGrade");
//                    boolean onlineFilter = getIntent().getBooleanExtra("onlineFilter", false);
//                    boolean inPersonFilter = getIntent().getBooleanExtra("inPersonFilter", false);
//                    boolean qualifiedTeacherFilter = getIntent().getBooleanExtra("qualifiedTeacherFilter", false);
//                    int maxPricePerHour = getIntent().getIntExtra("maxPricePerHour", 0);
//
//
//
//                    float price = Float.parseFloat(tutorPricePerHour);
//                    int locationOnline = cursor.getInt(7);
//                    int locationOffline = cursor.getInt(8);
//                    int extraQualifiedTeacher = cursor.getInt(9);
//
//                    if(math){
//                        //Check if tutor gives math and check selectedGrade
//                        MATH = "Math";
//                        tutorOffersMath = dbHelper.doesTutorOfferSubjectForGrade("Math", selectedGrade, tutorId);
//                        meetsCriteria = meetsCriteria && tutorOffersMath;
//                    }
//                    if(history){
//                        //Check if tutor gives history and check selectedGrade
//                        HISTORY = "History";
//                        tutorOffersHistory = dbHelper.doesTutorOfferSubjectForGrade("History", selectedGrade, tutorId);
//                        meetsCriteria = meetsCriteria && tutorOffersHistory;
//                    }
//
//                    if(afrikaans){
//                        //Check if tutor gives afrikaans and check selectedGrade
//                        AFRIKAANS = "Afrikaans";
//                        tutorOffersAfrikaans = dbHelper.doesTutorOfferSubjectForGrade("Afrikaans", selectedGrade, tutorId);
//                        meetsCriteria = meetsCriteria && tutorOffersAfrikaans;
//                    }
//                    if(english){
//                        //Check if tutor gives english and check selectedGrade
//                        ENGLISH = "English";
//                        tutorOffersEnglish = dbHelper.doesTutorOfferSubjectForGrade("English", selectedGrade, tutorId);
//                        meetsCriteria = meetsCriteria && tutorOffersEnglish;
//                    }
//
//
//
//
//
//
//
//
//
//                    if (onlineFilter) {
//                        // Apply online filter
//                        meetsCriteria = meetsCriteria && (locationOnline == 1);
//                    }
//
//                    if (inPersonFilter) {
//                        // Apply In-person filter
//                        meetsCriteria = meetsCriteria && (locationOffline == 1);
//                    }
//
//                    if (qualifiedTeacherFilter) {
//                        // Apply Qualified teacher filter
//                        meetsCriteria = meetsCriteria && (extraQualifiedTeacher == 1);
//                    }
//
//                    if (maxPricePerHour > 0) {
//                        // Apply Price filter
//                        meetsCriteria = meetsCriteria && (price <= maxPricePerHour);
//                    }
//                }
//
//                // If the tutor meets all the filter criteria, add them to the display
//                if (meetsCriteria) {
//                    tutorID.add(tutorId); // tutorID
//                    firstName.add(tutorFirstName); // firstName
//                    lastname.add(tutorLastName); // lastname
//                    YearsOfExperience.add(tutorYearsOfExperience); // YearsOfExperience
//                    TotalTutorHours.add(tutorTotalTutorHours); // TotalTutorHours
//                    pricePerHour.add(tutorPricePerHour); // pricePerHour
//                    imageBytes.add(tutorImageBlob);  // profile pic
//                }
//            } while (cursor.moveToNext());
//
//            cursor.close(); // Close the cursor when done
//        }
//    }






