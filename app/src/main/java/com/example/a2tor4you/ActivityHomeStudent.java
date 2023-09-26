package com.example.a2tor4you;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivityHomeStudent extends AppCompatActivity {

    /*BottomNavigationView bottomNavigationView;*/

    Button message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_student);

        message = findViewById(R.id.btnMessage);

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityHomeStudent.this,LoginUsernameActivity.class);
                intent.putExtra("phone", LoginOtpActivity.phoneNumber);
                startActivity(intent);
            }
        });

        // Progress Bar
        ProgressBar progressBar = findViewById(R.id.progressBar);
                progressBar.setVisibility(View.INVISIBLE);

        Button btn = findViewById(R.id.btnFindPerfectTutor); // NB: change button name to the button that when a tutor completes a lesson they click then adds points

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                //Increasing the progress bar by 10 every click of the button
                progressBar.incrementProgressBy(10);
            }
        });






        // Navigation Bar

        /*bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.calendar:
                        startActivity(new Intent(getApplicationContext(), ActivityHomeStudent.class));
                        overridePendingTransition();
                        return true;

                    case.R.id.home:
                        return true;

                    case R.id.findTutors:
                    startActivity(new Intent(getApplicationContext(), ActivityFindTutors.class));
                    overridePendingTransition();
                    return true;

                }

                return false;
            }
        });*/

    }
}