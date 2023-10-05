package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.a2tor4you.utils.AndroidUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ActivityEditEvent extends AppCompatActivity {
    EditText txt_EventTitle, txt_EventNotes;
    Button btnEventDate, btnEventTime, btn_SaveEvent;
    RadioButton rdo_Online, rdo_InPerson;
    static String eventTitle, eventDate, eventTime,  eventOnline, eventInPerson, eventNotes;
    DBHelper dbHelper ;
    DBHelper myDB;

    boolean isAllFieldsChecked = false;

    //Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        ImageView btnBack = findViewById(R.id.btnBackEditEvent);
        btnBack.setOnClickListener(view -> startActivity(new Intent(ActivityEditEvent.this, ActivityCalendar.class)));

        dbHelper = new DBHelper(this);

        myDB = new DBHelper(this); // Initialize myDB

        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int loggedInUserId = preferences.getInt("loggedInUserId", -1); // -1 is a default value if key not found

        //Get all profile edits/spinners/buttons...

        txt_EventTitle = findViewById(R.id.txt_EventTitle);
        btnEventDate = findViewById(R.id.btnEventDate);
        btnEventTime = findViewById(R.id.btnEventTime);
        rdo_Online = findViewById(R.id.rdo_Online);
        rdo_InPerson = findViewById(R.id.rdo_InPerson);
        txt_EventNotes = findViewById(R.id.txt_EventNotes);
        btn_SaveEvent = findViewById(R.id.btn_SaveEvent);

        // handle the SAVE button
        btn_SaveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // store the returned value of the dedicated function which checks
                // whether the entered data is valid or if any fields are left blank.

                //Insert event details to the database
                eventTitle = txt_EventTitle.getText().toString();
                eventDate = btnEventDate.getText().toString();
                eventTime = btnEventTime.getText().toString();
                eventOnline = rdo_Online.getText().toString();
                eventInPerson = rdo_InPerson.getText().toString();
                eventNotes = txt_EventNotes.getText().toString();

                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues eventValues = new ContentValues();
                eventValues.put("eventTitle", eventTitle);
                eventValues.put("eventDate", eventDate);
                eventValues.put("startTime", eventTime);
                eventValues.put("locationOnline", eventOnline);
                eventValues.put("locationOffline", eventInPerson);
                eventValues.put("notes", eventNotes);

                boolean result = myDB.updateUserData("User", loggedInUserId, eventValues);

                if (isAllFieldsChecked) {

                    if (result) {

                        // the boolean variable turns to be true then
                        // only the user must be proceed to the activity2
                        Toast.makeText(ActivityEditEvent.this, "Profile Successfully Updated", Toast.LENGTH_SHORT).show();

                    } else {
                        AndroidUtil.showToast(getApplicationContext(), "An unknown error has occurred while saving student data!");
                    }
                }

                // Redirect to the activity displaying the list of events
                Intent intent = new Intent(ActivityEditEvent.this, ActivityEventsListView.class);
                startActivity(intent);
            }
        });


        // Date Picker
        btnEventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDatePicker(); // Open date picker dialog
            }
        });

        // Time Picker
        btnEventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openTimePicker(); // Open time picker dialog
            }
        });


        // RadioButtons select and deselect
        rdo_Online = findViewById(R.id.rdo_Online);
        rdo_InPerson = findViewById(R.id.rdo_InPerson);

        rdo_Online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!rdo_Online.isSelected()) {
                    rdo_Online.setChecked(true);
                    rdo_Online.setSelected(true);
                } else {
                    rdo_Online.setChecked(false);
                    rdo_Online.setSelected(false);
                }
            }
        });

        rdo_InPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!rdo_InPerson.isSelected()) {
                    rdo_InPerson.setChecked(true);
                    rdo_InPerson.setSelected(true);
                } else {
                    rdo_InPerson.setChecked(false);
                    rdo_InPerson.setSelected(false);
                }
            }
        });
    }

    private void openTimePicker(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {


                //Showing the picked value in the textView
                btnEventTime.setText(String.valueOf(hour)+ ":"+String.valueOf(minute));

            }
        }, 15, 30, false);

        timePickerDialog.show();
    }

    // function which checks all the text fields
    // are filled or not by the user.
    // when user clicks on the PROCEED button
    // this function is triggered.
    private boolean CheckAllFields() {
        if (txt_EventTitle.length() == 0) {
            txt_EventTitle.setError("This field is required");
            return false;
        }

        if (btnEventDate.length() == 0) {
            btnEventDate.setError("This field is required");
            return false;
        }

        if (btnEventTime.length() == 0) {
            btnEventTime.setError("This field is required");
            return false;
        }

        // after all validation return true.
        return true;
    }


        /*// handle the PROCEED button
        btn_SaveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // store the returned value of the dedicated function which checks
                // whether the entered data is valid or if any fields are left blank.
                isAllFieldsChecked = CheckAllFields();

                // the boolean variable turns to be true then
                // only the user must be proceed to the activity2
                if (isAllFieldsChecked) {
                    Intent i = new Intent(ActivityEditEvent.this, ActivityCalendar.class);
                    startActivity(i);
                }
            }
        });*/


    /*// function which checks all the text fields are filled or not by the user.
    private boolean CheckAllFields() {
        if (txt_EventTitle.length() == 0) {
            txt_EventTitle.setError("This field is required");
            return false;
        }

        if (btnEventDate.length() == 0) {
            btnEventDate.setError("This field is required");
            return false;
        }

        // after all validation return true.
        return true;
    }*/

    private void openDatePicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme , new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                //Showing the picked value in the textView
                btnEventDate.setText(String.valueOf(year)+ "."+String.valueOf(month)+ "."+String.valueOf(day));

            }
        }, 2023, 01, 20);

        datePickerDialog.show();
    }

}

