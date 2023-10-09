package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityTutorProfileView extends AppCompatActivity {

    ArrayList<String> firstName, lastname, TotalStudentTaught, YearsOfExperience ,TotalTutorHours , aboutMe, school, uni, reviewText, extraNotes, pricePerHour;
    ArrayList<Integer> tutorID;
    AdapterTutorProfileView AdapterTutorProfileView;

    ArrayList<String> listItem;
    ArrayAdapter adapter;

    ListView user_list;

    RecyclerView rvTutorProfileView;

    //LinearLayout layoutTutorProfile;

    DBHelper dbHelper ;

    static int loggedInUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_profile_view);

        //Intent intent = getIntent();
        //String selectedName = intent.getStringExtra("name");

        // Retrieve data from the intent
//        Intent intent = getIntent();
//        if (intent.hasExtra("tutorId")) {
//            String tutorId = intent.getStringExtra("tutorId");
//
//            // For example, you can make a network request or query a local database to get tutor details
//        } else {
//            // Handle the case where tutorId is not provided in the intent extras
//            // You can show an error message or navigate back to the previous screen
//        }
//
//        Toast.makeText(this, "name: " +firstName, Toast.LENGTH_SHORT).show();
//
//
//        dbHelper = new DBHelper(this);
//
//        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        loggedInUserId = preferences.getInt("loggedInUserId", -1); // -1 is a default value if key not found
//
//        //Testing recycle view
//        dbHelper = new DBHelper(ActivityTutorProfileView.this);
//

        //storeDataInArrays();
//
//        tutorID = new ArrayList<>();
//        firstName = new ArrayList<>();
//        lastname = new ArrayList<>();
//        TotalStudentTaught = new ArrayList<>();
//        YearsOfExperience = new ArrayList<>();
//        TotalTutorHours = new ArrayList<>();
//        aboutMe = new ArrayList<>();
//        school = new ArrayList<>();
//        uni = new ArrayList<>();
//        reviewText = new ArrayList<>();
//        extraNotes = new ArrayList<>();
//        pricePerHour = new ArrayList<>();
//
//        rvTutorProfileView = findViewById(R.id.rvTutorProfileView);

//        storeDataInArrays();
//        AdapterTutorProfileView = new AdapterTutorProfileView(ActivityTutorProfileView.this,tutorID , firstName, lastname, TotalStudentTaught, YearsOfExperience, TotalTutorHours, aboutMe, school, uni, reviewText, extraNotes, pricePerHour);
//        rvTutorProfileView.setAdapter(AdapterTutorProfileView);
//        rvTutorProfileView.setLayoutManager(new LinearLayoutManager(ActivityTutorProfileView.this));

       //storeDataInArrays();

        /*for (int i = 0; i < firstName.size(); i++) {
            // Create a TextView to display tutor information
            TextView textView = new TextView(this);
            textView.setText(firstName.get(i) + " " + lastname.get(i) + "\n" +
                    TotalStudentTaught.get(i) + "\n" +
                    YearsOfExperience.get(i) + "\n" +
                    TotalTutorHours.get(i) + "\n" +
                    aboutMe.get(i) + "\n" +
                    pricePerHour.get(i));*/

    }

//    void storeDataInArrays()
//    {
      //  int tutorId = Integer.parseInt(getIntent().getStringExtra("tutorId"));
    //    Cursor cursor = dbHelper.viewTutorProfileData(tutorId);
//        if (cursor.getCount() == 0) {
//            Toast.makeText(ActivityTutorProfileView.this, "No events to show", Toast.LENGTH_LONG).show();
//        } else {
//            while (cursor.moveToNext()) {
//
//                tutorID.add(cursor.getInt(0)); // tutorID
//                firstName.add(cursor.getString(1)); // firstName
//                lastname.add(cursor.getString(2)); // lastname
//                TotalStudentTaught.add(cursor.getString(3)); // TotalStudentTaught
//                YearsOfExperience.add(cursor.getString(4)); // YearsOfExperience
//                TotalTutorHours.add(cursor.getString(5)); // TotalTutorHours
//                aboutMe.add(cursor.getString(6)); // aboutMe
//                school.add(cursor.getString(7)); // school
//                uni.add(cursor.getString(8)); // uni
//                reviewText.add(cursor.getString(9)); // reviewText
//                extraNotes.add(cursor.getString(10)); // eventNotes
//                pricePerHour.add(cursor.getString(11)); // pricePerHour
//
//            }
//            Toast.makeText(ActivityTutorProfileView.this, "name: " + firstName, Toast.LENGTH_SHORT).show();

        //}

    }
//}