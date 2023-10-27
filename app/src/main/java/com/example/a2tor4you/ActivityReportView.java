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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityReportView extends AppCompatActivity {

    ArrayList<String> reportID, reportCategory, reportText, reportedAt;
    RecyclerView rvReport;

    AdapterReport AdapterReport;

    ArrayList<String> listItem;
    ArrayAdapter adapter;

    ListView user_list;
    DBHelper dbHelper ;
    View _baseView;

    static int loggedInUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_view);

        dbHelper = new DBHelper(this);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        loggedInUserId = preferences.getInt("loggedInUserId", -1); // -1 is a default value if key not found

        ImageButton btnBackReportView = findViewById(R.id.btnBackReportView);

        btnBackReportView.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityReportView.this, ActivityLogin.class);
            startActivity(intent);
        });

        rvReport = findViewById(R.id.rvReport);

        //Testing recycle view
        dbHelper = new DBHelper(ActivityReportView.this);
        reportID = new ArrayList<>();
        reportCategory = new ArrayList<>();
        reportText = new ArrayList<>();
        reportedAt = new ArrayList<>();

        storeDataInArrays();
        AdapterReport = new AdapterReport(ActivityReportView.this,reportID, reportCategory, reportText, reportedAt);
        AdapterReport.setDbHelper(dbHelper);
        rvReport.setAdapter(AdapterReport);
        rvReport.setLayoutManager(new LinearLayoutManager(ActivityReportView.this)) ;

    }

    void storeDataInArrays() {
        Cursor cursor = dbHelper.viewReportData(loggedInUserId);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                reportID.add(cursor.getString(0)); //reportID
                reportCategory.add(cursor.getString(2)); // reportCategory
                reportText.add(cursor.getString(1)); // reportText
                reportedAt.add(cursor.getString(3)); // reportedAt

            } while (cursor.moveToNext());
            cursor.close();
        } else {
            // Handle the case when there is no data or an error occurred.
            Toast.makeText(ActivityReportView.this, "No reports to show", Toast.LENGTH_LONG).show();
        }
    }

    /*void storeDataInArrays() {
        Cursor cursor = dbHelper.viewReportData(loggedInUserId);
        if (cursor.getCount() == 0) {
            Toast.makeText(ActivityReportView.this, "No events to show", Toast.LENGTH_LONG).show();
        } else {
            while (cursor.moveToNext()) {

                reportID.add(cursor.getString(0)); //reportID
                reportCategory.add(cursor.getString(0)); // reportCategory
                reportText.add(cursor.getString(1)); // reportText

            }
        }
    }*/

}