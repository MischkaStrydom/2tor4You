package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a2tor4you.utils.AndroidUtil;

public class ActivityReport extends AppCompatActivity {

    EditText txtReport;
    Spinner spinReport;
    Button btnSubmitReport;
    static String reportCategory, reportText;
    DBHelper dbHelper ;
    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        ImageButton btnBackReport = findViewById(R.id.btnBackReport);
        btnBackReport.setOnClickListener(view -> startActivity(new Intent(ActivityReport.this,ActivityAccount.class)));

        dbHelper = new DBHelper(this);

        myDB = new DBHelper(this); // Initialize myDB

        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int loggedInUserId = preferences.getInt("loggedInUserId", -1); // -1 is a default value if key not found

        //Get all profile edits/spinners/buttons...

        spinReport = findViewById(R.id.spinReport);
        txtReport = findViewById(R.id.txtReport);

        // handle the SAVE button
        btnSubmitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Insert event details to the database
                reportCategory = spinReport.getSelectedItem().toString();
                reportText = txtReport.getText().toString();//

                //SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues reportValues = new ContentValues();
                reportValues.put("userID", loggedInUserId);
                reportValues.put("reportCategory", reportCategory);
                reportValues.put("reportText", reportText);

                boolean result = myDB.insertDataUser("Report", reportValues);

                // if (isAllFieldsChecked) {

                if (result) {
                    // the boolean variable turns to be true then
                    // only the user must be proceed to the activity2
                    Toast.makeText(ActivityReport.this, "Report submitted successfully", Toast.LENGTH_SHORT).show();

                    spinReport.setSelection(0);
                    txtReport.setText("");


                } else {
                    AndroidUtil.showToast(getApplicationContext(), "An unknown error has occurred!");
                }
                //  }

                // Redirect to the activity displaying the list of events
//                Intent intent = new Intent(ActivityEditEvent.this, ActivityEventsListView.class);
//                startActivity(intent);
            }
        });

    }

}