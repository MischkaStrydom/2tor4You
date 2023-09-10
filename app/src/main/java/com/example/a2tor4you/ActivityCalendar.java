package com.example.a2tor4you;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

public class ActivityCalendar extends AppCompatActivity {

    CalendarView calendarView;
    TextView txtMySession;

/*BottomNavigationView bottomNavigationView;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        txtMySession = (TextView) findViewById(R.id.txtMySession);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = (i1 + 1) + "/" + i2 + "/" + i;
                txtMySession.setText(date);
            }
        });





/*bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.calendar);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.findTutors:
                        startActivity(new Intent(getApplicationContext(), ActivityFindTutors.class));
                        overridePendingTransition();
                        return true;

                    case.R.id.calendar:
                        return true;

                    case R.id.messages:
                        startActivity(new Intent(getApplicationContext(), ActivityMessages.class));
                        overridePendingTransition();
                        return true;

                }

                return false;
            }
        });*/


    }
}