package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityFindTutorItem extends AppCompatActivity {

//    ArrayList<String> txtTutName, txtTutLastName,yearsExperience ,totalTutHours , priceHour;
//    CustomAdapter customAdapter;
//
//    ArrayList<String> listItem;
//    ArrayAdapter adapter;
//
//    ListView user_list;
//
//    DBHelper dbHelper ;
//
//    View _baseView;
//
//    RecyclerView rvFindTutor;
   // static int loggedInUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_tutor_item);

       /*ImageView imgTutProfilePicList = findViewById(R.id.imgTutProfilePicList);
       imgTutProfilePicList.setOnClickListener(view -> startActivity(new Intent(ActivityFindTutorItem.this,ActivityTutorProfileView.class)));*/

//        dbHelper = new DBHelper(this);
//
//        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        loggedInUserId = preferences.getInt("loggedInUserId", -1); // -1 is a default value if key not found
//
//        //Testing recycle view
//        dbHelper = new DBHelper(ActivityFindTutorItem.this);
//
//        txtTutName = new ArrayList<>();
//        txtTutLastName = new ArrayList<>();
//        yearsExperience = new ArrayList<>();
//        totalTutHours = new ArrayList<>();
//        priceHour = new ArrayList<>();
//
//        rvFindTutor = findViewById(R.id.rvFindTutor);
//
//        storeDataInArrays();
//        customAdapter = new CustomAdapter(ActivityFindTutorItem.this, txtTutName, txtTutLastName, yearsExperience, totalTutHours, priceHour);
//        rvFindTutor.setAdapter(customAdapter);
//        rvFindTutor.setLayoutManager(new LinearLayoutManager(ActivityFindTutorItem.this));

    }

//    void storeDataInArrays()
//    {
//        Cursor cursor = dbHelper.viewData(loggedInUserId);
//        if (cursor.getCount() == 0) {
//            Toast.makeText(ActivityFindTutorItem.this, "No events to show", Toast.LENGTH_LONG).show();
//        } else {
//            while (cursor.moveToNext()) {
//
//                txtTutName.add(cursor.getString(0)); // eventTitle
//                txtTutLastName.add(cursor.getString(1)); // eventDate
//                yearsExperience.add(cursor.getString(2)); // eventTime
//                totalTutHours.add(cursor.getString(3)); // eventNotes
//                priceHour.add(cursor.getString(3)); // eventNotes
//
//            }
//
//        }
//
//    }
}