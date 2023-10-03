package com.example.a2tor4you;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.w3c.dom.Text;

public class ActivityCalendar extends AppCompatActivity {
    static String phoneNumber;
    static String password;
    static String selectedRole;
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



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = (i1 + 1) + "/" + i2 + "/" + i;
                txtMySession.setText(date);
            }
        });

        // Navigation Bar

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.calendar);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.calendar:
                        startNewActivity(ActivityCalendar.class);
                        return true;

                    case R.id.home:

                        startNewActivity(ActivityHomeStudent.class);
                        return true;

                    case R.id.findTutors:
                        startNewActivity(ActivityFindTutor.class);
                        return true;


                    case R.id.account:
                        startNewActivity(ActivityAccount.class);
                        return true;

                }
                return false;
            }
        });


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
}