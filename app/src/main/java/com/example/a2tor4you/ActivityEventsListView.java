package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActivityEventsListView extends AppCompatActivity {

    ArrayList<String>eventID, eventTitle, eventDate, notes, startTime, locationOnline;

    CustomAdapter customAdapter;

    ArrayList<String> listItem;
    ArrayAdapter adapter;

    RecyclerView recyclerView;
    ListView user_list;
    DBHelper dbHelper ;
    View _baseView;
    public static ImageButton btnDeleteEvent;


    static int loggedInUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list_view);


        dbHelper = new DBHelper(this);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        loggedInUserId = preferences.getInt("loggedInUserId", -1); // -1 is a default value if key not found

        ImageButton btnBackUpcomingEvent = findViewById(R.id.btnBackUpcomingEvent);

        btnBackUpcomingEvent.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityEventsListView.this, ActivityHomeStudent.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.user_list);
//        ImageButton btnDelete = findViewById(R.id.btnDeleteEvent);
//
//        btnDelete.setOnClickListener(v -> {
//
//        });

        //Testing recycle view
        dbHelper = new DBHelper(ActivityEventsListView.this);
        eventID = new ArrayList<>();
        eventTitle = new ArrayList<>();
        eventDate = new ArrayList<>();
        notes = new ArrayList<>();
        startTime = new ArrayList<>();
        locationOnline = new ArrayList<>();


        storeDataInArrays();
        customAdapter = new CustomAdapter(ActivityEventsListView.this,eventID, eventTitle, eventDate, notes, startTime, locationOnline);
        customAdapter.setDbHelper(dbHelper);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityEventsListView.this)) ;



    }

    void storeDataInArrays() {
        Cursor cursor = dbHelper.viewData(loggedInUserId);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                eventID.add(cursor.getString(0)); //eventID
                eventTitle.add(cursor.getString(1)); // eventTitle
                eventDate.add(cursor.getString(2)); // eventDate
                notes.add(cursor.getString(3)); // eventTime
                startTime.add(cursor.getString(4)); // eventNotes

                // Assuming there are at least 6 columns in the cursor.
                boolean isLocationOnline = cursor.getInt(5) == 1;
                String locationText = isLocationOnline ? "Online" : "In-Person";
                locationOnline.add(locationText);

            } while (cursor.moveToNext());
            cursor.close();
        } else {
            // Handle the case when there is no data or an error occurred.
            Toast.makeText(ActivityEventsListView.this, "No events to show", Toast.LENGTH_LONG).show();
        }
    }


//    void storeDataInArrays()
//    {
//        Cursor cursor = dbHelper.viewData(loggedInUserId);
//        if (cursor.getCount() == 0) {
//        Toast.makeText(ActivityEventsListView.this, "No events to show", Toast.LENGTH_LONG).show();
//    } else {
//        while (cursor.moveToNext()) {
//
//            eventID.add(cursor.getString(0)); //eventID
//            eventTitle.add(cursor.getString(1)); // eventTitle
//            eventDate.add(cursor.getString(2)); // eventDate
//            notes.add(cursor.getString(3)); // eventTime
//            startTime.add(cursor.getString(4)); // eventNotes
//
//            //locationOnline.add(cursor.getString(4)); // locationOnline
//            boolean isLocationOnline = cursor.getInt(5) == 1;
//            String locationText = isLocationOnline ? "Online" : "In-Person";
//            locationOnline.add(locationText);
//
//
//        }
//
//     }
//    }


}