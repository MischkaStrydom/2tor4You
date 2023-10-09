package com.example.a2tor4you;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.database.Cursor;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ActivityFindTutor extends AppCompatActivity {

    ArrayList<String> firstName, lastname, YearsOfExperience, TotalTutorHours, pricePerHour, grade;
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
        grade = new ArrayList<>();

        rvFindTutor = findViewById(R.id.rvFindTutor);

        storeDataInArrays();
        adapterTutor = new adapterTutor(ActivityFindTutor.this, tutorID, firstName, lastname, YearsOfExperience, TotalTutorHours, pricePerHour, imageBytes, grade);
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


//    private void storeDataInArrays() {
//        Cursor cursor = dbHelper.viewTutorData();
//
//        String selectedGrade = getIntent().getStringExtra("grade");
//
//        boolean isMath = getIntent().getBooleanExtra("math", false);
//        boolean isHistory = getIntent().getBooleanExtra("history", false);
//        boolean isAfrikaans = getIntent().getBooleanExtra("afrikaans", false);
//        boolean isEnglish = getIntent().getBooleanExtra("english", false);
//
//        boolean onlineFilter = getIntent().getBooleanExtra("onlineFilter", false);
//        boolean inPersonFilter = getIntent().getBooleanExtra("inPersonFilter", false);
//        boolean qualifiedTeacherFilter = getIntent().getBooleanExtra("qualifiedTeacherFilter", false);
//        int maxPricePerHour = getIntent().getIntExtra("maxPricePerHour", 0);
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
//                boolean onlineAvailability = cursor.getInt(7) == 1;
//                boolean inPersonAvailability = cursor.getInt(8) == 1;
//                boolean isQualifiedTeacher = cursor.getInt(9) == 1;
//
//                String GRADE = cursor.getString(11);
//
//                String subject = cursor.getString(10);
//
//
//                boolean meetSubjectFilter;
//
//
//                if ((isMath && subject.equals("Math")) ||
//                        (isHistory && subject.equals("History")) ||
//                        (isAfrikaans && subject.equals("Afrikaans")) ||
//                        (isEnglish && subject.equals("English")) ||
//                        (!isMath && !isHistory && !isAfrikaans && !isEnglish)) {
//                    meetSubjectFilter = true;
//                } else {
//                    meetSubjectFilter = false;
//                }
//                boolean meetsGradeFilter;
//                if (selectedGrade == null || selectedGrade.isEmpty()) {
//                    // If no grade is selected by the user, consider the tutor to meet the grade filter
//                    meetsGradeFilter = true;
//                } else {
//                    // Check if the tutor's grade matches the selected grade
//                    meetsGradeFilter = GRADE.equals(selectedGrade);
//                }
//
//                // Check if the tutor meets the online availability filter criteria
//                boolean meetsOnlineFilter = !onlineFilter || onlineAvailability;
//
//                // Check if the tutor meets the in-person availability filter criteria
//                boolean meetsInPersonFilter = !inPersonFilter || inPersonAvailability;
//
//                // Check if the tutor meets the teacher qualification filter criteria
//                boolean meetsQualifiedTeacherFilter = !qualifiedTeacherFilter || isQualifiedTeacher;
//
//                // Check if the tutor meets the price filter criteria
//                boolean meetsPriceFilter = maxPricePerHour == 0 || Float.parseFloat(tutorPricePerHour) <= maxPricePerHour;
//
//                // If the tutor meets all filter criteria, add them to the display
//                if (meetSubjectFilter && meetsGradeFilter && meetsOnlineFilter && meetsInPersonFilter && meetsQualifiedTeacherFilter && meetsPriceFilter) {
//                    tutorID.add(tutorId);
//                    firstName.add(tutorFirstName);
//                    lastname.add(tutorLastName);
//                    YearsOfExperience.add(tutorYearsOfExperience);
//                    TotalTutorHours.add(tutorTotalTutorHours);
//                    pricePerHour.add(tutorPricePerHour);
//                    imageBytes.add(tutorImageBlob);
//                    grade.add(GRADE);
//                }
//
//
//            } while (cursor.moveToNext());
//
//
//            cursor.close(); // Close the cursor when done
//        }
//    }

    private void storeDataInArrays() {


        String selectedGrade = getIntent().getStringExtra("grade");

        boolean isMath = getIntent().getBooleanExtra("math", false);
        boolean isHistory = getIntent().getBooleanExtra("history", false);
        boolean isAfrikaans = getIntent().getBooleanExtra("afrikaans", false);
        boolean isEnglish = getIntent().getBooleanExtra("english", false);

        boolean onlineFilter = getIntent().getBooleanExtra("onlineFilter", false);
        boolean inPersonFilter = getIntent().getBooleanExtra("inPersonFilter", false);
        boolean qualifiedTeacherFilter = getIntent().getBooleanExtra("qualifiedTeacherFilter", false);
        int maxPricePerHour = getIntent().getIntExtra("maxPricePerHour", 0);


        Cursor cursor = dbHelper.viewTutorData(selectedGrade);
        Set<Integer> tutorIdsSet = new HashSet<>(); // To store unique tutor IDs

        if (cursor.getCount() == 0) {
            Toast.makeText(ActivityFindTutor.this, "No tutors were found", Toast.LENGTH_LONG).show();
        } else {
            cursor.moveToFirst(); // Reset cursor position
            do {
                int tutorId = cursor.getInt(0);

                // Check if this tutor ID has already been added
                if (tutorIdsSet.contains(tutorId)) {
                    continue; // Skip this tutor since they have already been added
                }

                tutorIdsSet.add(tutorId); // Add the tutor ID to the set

                String tutorFirstName = cursor.getString(1);
                String tutorLastName = cursor.getString(2);
                String tutorYearsOfExperience = cursor.getString(3);
                String tutorTotalTutorHours = cursor.getString(4);
                String tutorPricePerHour = cursor.getString(5);
                byte[] tutorImageBlob = cursor.getBlob(6);
                boolean onlineAvailability = cursor.getInt(7) == 1;
                boolean inPersonAvailability = cursor.getInt(8) == 1;
                boolean isQualifiedTeacher = cursor.getInt(9) == 1;

                String GRADE = cursor.getString(11);
                String subject = cursor.getString(10);

                boolean meetSubjectFilter;

                if ((isMath && subject.equals("Math")) ||
                        (isHistory && subject.equals("History")) ||
                        (isAfrikaans && subject.equals("Afrikaans")) ||
                        (isEnglish && subject.equals("English")) ||
                        (!isMath && !isHistory && !isAfrikaans && !isEnglish)) {
                    meetSubjectFilter = true;
                } else {
                    meetSubjectFilter = false;
                }
                boolean meetsGradeFilter;
                if (selectedGrade == null || selectedGrade.isEmpty()) {
                    // If no grade is selected by the user, consider the tutor to meet the grade filter
                    meetsGradeFilter = true;
                } else {
                    // Check if the tutor's grade matches the selected grade
                    meetsGradeFilter = GRADE.equals(selectedGrade);
                }

                // Check if the tutor meets the online availability filter criteria
                boolean meetsOnlineFilter = !onlineFilter || onlineAvailability;

                // Check if the tutor meets the in-person availability filter criteria
                boolean meetsInPersonFilter = !inPersonFilter || inPersonAvailability;

                // Check if the tutor meets the teacher qualification filter criteria
                boolean meetsQualifiedTeacherFilter = !qualifiedTeacherFilter || isQualifiedTeacher;

                // Check if the tutor meets the price filter criteria
                boolean meetsPriceFilter = maxPricePerHour == 0 || Float.parseFloat(tutorPricePerHour) <= maxPricePerHour;

                // If the tutor meets all filter criteria, add them to the display
                if (meetSubjectFilter && meetsGradeFilter && meetsOnlineFilter && meetsInPersonFilter && meetsQualifiedTeacherFilter && meetsPriceFilter) {
                    tutorID.add(tutorId);
                    firstName.add(tutorFirstName);
                    lastname.add(tutorLastName);
                    YearsOfExperience.add(tutorYearsOfExperience);
                    TotalTutorHours.add(tutorTotalTutorHours);
                    pricePerHour.add(tutorPricePerHour);
                    imageBytes.add(tutorImageBlob);
                    grade.add(GRADE);
                }
                else{
                    Toast.makeText(ActivityFindTutor.this, "No tutors were found with these filters", Toast.LENGTH_LONG).show();
                }
            } while (cursor.moveToNext());

            cursor.close(); // Close the cursor when done
        }

    }


//    private void storeDataInArrays() {
//        Cursor cursor = dbHelper.viewTutorData();
//
//        String selectedGrade = getIntent().getStringExtra("grade");
//
//        boolean isMath = getIntent().getBooleanExtra("math", false);
//        boolean isHistory = getIntent().getBooleanExtra("history", false);
//        boolean isAfrikaans = getIntent().getBooleanExtra("afrikaans", false);
//        boolean isEnglish = getIntent().getBooleanExtra("english", false);
//
//        boolean onlineFilter = getIntent().getBooleanExtra("onlineFilter", false);
//        boolean inPersonFilter = getIntent().getBooleanExtra("inPersonFilter", false);
//        boolean qualifiedTeacherFilter = getIntent().getBooleanExtra("qualifiedTeacherFilter", false);
//        int maxPricePerHour = getIntent().getIntExtra("maxPricePerHour", 0);
//
//        Set<Integer> tutorIdsSet = new HashSet<>(); // To store unique tutor IDs
//
//        if (cursor.getCount() == 0) {
//            Toast.makeText(ActivityFindTutor.this, "No tutors were found", Toast.LENGTH_LONG).show();
//        } else {
//            cursor.moveToFirst(); // Reset cursor position
//            do {
//                int tutorId = cursor.getInt(0);
//
//                // Check if this tutor ID has already been added
//                if (tutorIdsSet.contains(tutorId)) {
//                    continue; // Skip this tutor since they have already been added
//                }
//
//                tutorIdsSet.add(tutorId); // Add the tutor ID to the set
//
//                String tutorFirstName = cursor.getString(1);
//                String tutorLastName = cursor.getString(2);
//                String tutorYearsOfExperience = cursor.getString(3);
//                String tutorTotalTutorHours = cursor.getString(4);
//                String tutorPricePerHour = cursor.getString(5);
//                byte[] tutorImageBlob = cursor.getBlob(6);
//                boolean onlineAvailability = cursor.getInt(7) == 1;
//                boolean inPersonAvailability = cursor.getInt(8) == 1;
//                boolean isQualifiedTeacher = cursor.getInt(9) == 1;
//
//                String GRADE = cursor.getString(11);
//                String subject = cursor.getString(10);
//
//                boolean meetSubjectFilter;
//
//                if ((isMath && subject.equals("Math")) ||
//                        (isHistory && subject.equals("History")) ||
//                        (isAfrikaans && subject.equals("Afrikaans")) ||
//                        (isEnglish && subject.equals("English")) ||
//                        (!isMath && !isHistory && !isAfrikaans && !isEnglish)) {
//                    meetSubjectFilter = true;
//                } else {
//                    meetSubjectFilter = false;
//                }
//
//                boolean meetsGradeFilter;
//                if (selectedGrade == null || selectedGrade.isEmpty()) {
//                    // If no grade is selected by the user, consider the tutor to meet the grade filter
//                    meetsGradeFilter = true;
//                } else {
//                    // Check if the tutor's grade matches the selected grade
//                    meetsGradeFilter = GRADE.equals(selectedGrade);
//                }
//
//                // Check if the tutor meets the online availability filter criteria
//                boolean meetsOnlineFilter = !onlineFilter || onlineAvailability;
//
//                // Check if the tutor meets the in-person availability filter criteria
//                boolean meetsInPersonFilter = !inPersonFilter || inPersonAvailability;
//
//                // Check if the tutor meets the teacher qualification filter criteria
//                boolean meetsQualifiedTeacherFilter = !qualifiedTeacherFilter || isQualifiedTeacher;
//
//                // Check if the tutor meets the price filter criteria
//                boolean meetsPriceFilter = maxPricePerHour == 0 || Float.parseFloat(tutorPricePerHour) <= maxPricePerHour;
//
//                // If the tutor meets all filter criteria, add them to the display
//                if (meetSubjectFilter && meetsGradeFilter && meetsOnlineFilter && meetsInPersonFilter && meetsQualifiedTeacherFilter && meetsPriceFilter) {
//                    tutorID.add(tutorId);
//                    firstName.add(tutorFirstName);
//                    lastname.add(tutorLastName);
//                    YearsOfExperience.add(tutorYearsOfExperience);
//                    TotalTutorHours.add(tutorTotalTutorHours);
//                    pricePerHour.add(tutorPricePerHour);
//                    imageBytes.add(tutorImageBlob);
//                    grade.add(GRADE);
//                }
//            } while (cursor.moveToNext());
//
//            cursor.close(); // Close the cursor when done
//        }
//    }


}










