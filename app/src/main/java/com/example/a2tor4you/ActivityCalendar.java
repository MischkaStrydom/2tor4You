package com.example.a2tor4you;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivityCalendar extends AppCompatActivity {

    /*BottomNavigationView bottomNavigationView;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

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