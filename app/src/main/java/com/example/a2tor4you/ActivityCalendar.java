package com.example.a2tor4you;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

public class ActivityCalendar extends AppCompatActivity {

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

        String phoneNumber = getIntent().getStringExtra("phone");
        String password = getIntent().getStringExtra("password");
        String selectedRole = getIntent().getStringExtra("selectedRole");
        String userID = getIntent().getStringExtra("userID");


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

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.calendar:
                        return true;

                    case R.id.home:
                       // startActivity(new Intent(getApplicationContext(), ActivityHomeStudent.class));

                        Intent intent = new Intent(ActivityCalendar.this,ActivityHomeStudent.class);
                        // intent.putExtra("phone", LoginOtpActivity.phoneNumber);
                        intent.putExtra("phone", phoneNumber);
                        intent.putExtra("password", password);
                        intent.putExtra("selectedRole", selectedRole);
                        intent.putExtra("userID", userID);
                        startActivity(intent);

                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.findTutors:
                      //  startActivity(new Intent(getApplicationContext(), ActivityFindTutor.class));

                        Intent intent1 = new Intent(ActivityCalendar.this,ActivityFindTutor.class);
                        // intent.putExtra("phone", LoginOtpActivity.phoneNumber);
                        intent1.putExtra("phone", phoneNumber);
                        intent1.putExtra("password", password);
                        intent1.putExtra("selectedRole", selectedRole);
                        intent1.putExtra("userID", userID);
                        startActivity(intent1);


                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.messages:
                       // startActivity(new Intent(getApplicationContext(), ChatMainActivity.class));

                        Intent intent2 = new Intent(ActivityCalendar.this,LoginUsernameActivity.class);
                        // intent2.putExtra("phone", LoginOtpActivity.phoneNumber);
                        intent2.putExtra("phone", phoneNumber);
                        intent2.putExtra("password", password);
                        intent2.putExtra("selectedRole", selectedRole);
                        intent2.putExtra("userID", userID);
                        startActivity(intent2);

                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.account:
                     //   startActivity(new Intent(getApplicationContext(), ActivityAccount.class));

                        Intent intent3 = new Intent(ActivityCalendar.this,ActivityAccount.class);
                        // intent2.putExtra("phone", LoginOtpActivity.phoneNumber);
                        intent3.putExtra("phone", phoneNumber);
                        intent3.putExtra("password", password);
                        intent3.putExtra("selectedRole", selectedRole);
                        intent3.putExtra("userID", userID);
                        startActivity(intent3);


                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });


        btnAddEvent.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ActivityEditEvent.class));
        });

    }
}