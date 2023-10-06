package com.example.a2tor4you;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ActivityCalendar extends AppCompatActivity {
    static String phoneNumber;
    static String password;
    static String selectedRole;
    static int loggedInUserId;
    CalendarView calendarView;
    TextView txtMySession;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        txtMySession = (TextView) findViewById(R.id.txtMySession);
        Button btnAddEvent = findViewById(R.id.btnAddEvent);

         phoneNumber = getIntent().getStringExtra("phone");
//         password = getIntent().getStringExtra("password");
//         selectedRole = getIntent().getStringExtra("selectedRole");
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
         loggedInUserId = preferences.getInt("loggedInUserId", -1); // -1 is a default value if key not found


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = (i1 + 1) + "/" + i2 + "/" + i;
                txtMySession.setText(date);
            }
        });


        // Now, eventDates contains a list of event dates for the specified userID


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Handle date selection here
                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                Toast.makeText(getApplicationContext(), "Selected date: " + selectedDate, Toast.LENGTH_SHORT).show();
            }
        });

        DBHelper dbHelper = new DBHelper(this);

        List<Date> eventDates = dbHelper.getEventDatesForUser(loggedInUserId);

        // Navigation Bar

//        bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        bottomNavigationView.setSelectedItemId(R.id.calendar);
//
//        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                switch (item.getItemId()) {
//
//                    case R.id.calendar:
//                        startActivity(new Intent(getApplicationContext(),ActivityCalendar.class));
//                        return true;
//
//                    case R.id.home:
//                        startActivity(new Intent(getApplicationContext(),ActivityHomeStudent.class));
//                        return true;
//
//                    case R.id.account:
//                        startActivity(new Intent(getApplicationContext(),ActivityAccount.class));
//                        overridePendingTransition(0,0);
//                        return true;
//
//
//                    case R.id.findTutors:
//                        startActivity(new Intent(getApplicationContext(),ActivityFindTutor.class));
//                        overridePendingTransition(0,0);
//                        return true;
//
//                }
//                return false;
//            }
//        });


        btnAddEvent.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ActivityEditEvent.class));
        });

    }
    private void startNewActivity(Class<?> targetActivity) {
        Intent intent = new Intent(ActivityCalendar.this, targetActivity);
        intent.putExtra("phone", phoneNumber);
//        intent.putExtra("password", password);
//        intent.putExtra("selectedRole", selectedRole);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }


    public class CustomCalendarView extends CalendarView {
        public CustomCalendarView(@NonNull Context context) {
            super(context);
        }
        // Constructor and other methods

        public void highlightEventDates(List<Date> eventDates) {
            for (Date eventDate : eventDates) {
                long eventTimeInMillis = eventDate.getTime();
                // Use a custom Drawable or background color to highlight the date
                setHighlightedDate(eventTimeInMillis);
            }
        }

        private void setHighlightedDate(long timeInMillis) {
            // Implement code to highlight the date here
            // You can set a custom background color or add a marker to the date
            // For example:
            // setDateBackgroundColor(timeInMillis, Color.GREEN);
        }
    }


}