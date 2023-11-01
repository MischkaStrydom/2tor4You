package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AdminActivity extends AppCompatActivity {

     Button startDateButton, endDateButton;
     Button btnGenerate;
     DBHelper myDb;
     Calendar calendar = Calendar.getInstance();
     ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        startDateButton = findViewById(R.id.txtStartDateButton);
        endDateButton = findViewById(R.id.txtendDateButton);
        myDb = new DBHelper(AdminActivity.this);

        TextView tutors = findViewById(R.id.txtDisplayTotalTutors);
        TextView students = findViewById(R.id.txtDisplayTotalStudents);

        TextView tutorsOn = findViewById(R.id.txtDisplayTutorsOnboarded);
        TextView studentsOn = findViewById(R.id.txtDisplayStudentsOnboarded);

        TextView tutorsOff = findViewById(R.id.txtDisplayTutorsOffBoarded);
        TextView studentsOff = findViewById(R.id.txtDisplayStudentsOffBoarded);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(view -> startActivity(new Intent(AdminActivity.this,MainActivity.class)));

        btnGenerate = findViewById(R.id.btnGenerate);

        int countTutors = myDb.getCountRole("Tutor");
        int countStudents = myDb.getCountRole("Student");

        tutors.setText(String.valueOf(" " + " " + countTutors));
        students.setText(String.valueOf(countStudents));


        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(startDateButton);
            }
        });

        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(endDateButton);
            }
        });



        btnGenerate.setOnClickListener(v -> {
            int countTutorsOn = myDb.getTotalBetweenDates("Tutor", startDateButton.getText().toString(), endDateButton.getText().toString());
            int countStudentsOn = myDb.getTotalBetweenDates("Student", startDateButton.getText().toString(), endDateButton.getText().toString());

            int countTutorsOff = myDb.getTotalOffBetweenDates("Tutor", startDateButton.getText().toString(), endDateButton.getText().toString());
            int countStudentsOff = myDb.getTotalOffBetweenDates("Student", startDateButton.getText().toString(), endDateButton.getText().toString());

            tutorsOn.setText(String.valueOf(countTutorsOn));
            studentsOn.setText(String.valueOf(countStudentsOn));

            tutorsOff.setText(String.valueOf(countTutorsOff));
            studentsOff.setText(String.valueOf(countStudentsOff));

        });

    }

    private void showDatePicker(final Button button) {
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
                        button.setText(formattedDate);
                    }
                },
                year, month, day
        );

        datePickerDialog.show();
    }
}