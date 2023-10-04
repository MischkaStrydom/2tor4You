package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ActivityEditEvent extends AppCompatActivity {

    RadioButton rdo_Online, rdo_InPerson;
    Button btn_SaveEvent, btnEventDate, btnEventTime;
    EditText txt_EventTitle;

    /* boolean isAllFieldsChecked = false;*/

    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        ImageView btnBack = findViewById(R.id.btnBackEditEvent);
        btnBack.setOnClickListener(view -> startActivity(new Intent(ActivityEditEvent.this, ActivityCalendar.class)));

        txt_EventTitle = findViewById(R.id.txt_EventTitle);
        btn_SaveEvent = findViewById(R.id.btn_SaveEvent);

        btnEventDate = findViewById(R.id.btnEventDate);
        btnEventTime = findViewById(R.id.btnEventTime);

        btnEventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDatePicker(); // Open date picker dialog
            }
        });

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

