package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ActivityEditEvent extends AppCompatActivity {

    // button
    Button btn_SaveEvent, btnEventDate;

    // Text fields
    EditText txt_EventTitle;

    boolean isAllFieldsChecked = false;

    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        ImageView btnBack = findViewById(R.id.btnBackEditEvent);
        btnBack.setOnClickListener(view -> startActivity(new Intent(ActivityEditEvent.this, ActivityCalendar.class)));

        btn_SaveEvent = findViewById(R.id.btn_SaveEvent);
        btnEventDate = findViewById(R.id.btnEventDate);

        btnEventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });


        // register all the EditText fields with their IDs.
        txt_EventTitle = findViewById(R.id.txt_EventTitle);

        // handle the PROCEED button
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
        });
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

        // after all validation return true.
        return true;
    }

    private void showDatePicker() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        calendar.set(selectedYear, selectedMonth, selectedDay);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                        String formattedDate = dateFormat.format(calendar.getTime());
                        btnEventDate.setText(formattedDate);
                    }
                },
                year, month, day
        );

        datePickerDialog.show();
    }

}

