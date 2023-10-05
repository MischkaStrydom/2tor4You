package com.example.a2tor4you;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.a2tor4you.Model.UserModel;
import com.example.a2tor4you.utils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

public class ActivityHomeStudent extends AppCompatActivity {

    static String phoneNumber;
    static String password;
    static String selectedRole;
    BottomNavigationView bottomNavigationView;

    /*Button message;*/
    String userInput;

    UserModel userModel;

    DBHelper dbHelper ;
    Button btnViewAllSessions, btnAddEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_student);
        TextView welcome = findViewById(R.id.txtWelcomeStud);
        dbHelper = new DBHelper(this);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int loggedInUserId = preferences.getInt("loggedInUserId", -1); // -1 is a default value if key not found

        if (loggedInUserId != -1) {
            // Fetch user's name and surname from the database based on userID
            String userName = dbHelper.getUserName(loggedInUserId); // Implement this method

            // Display the user's name in the TextView
            welcome.setText("Welcome " + userName);
        } else {
            // Handle the case where the userID is not found (e.g., user not logged in)
            welcome.setText("Guest"); // Display a default value or handle it as needed
        }


        btnViewAllSessions = findViewById(R.id.btnViewAllSessions);
        btnViewAllSessions.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityHomeStudent.this, ActivityEventsListView.class);
            startActivity(intent);
        });

        btnAddEvent = findViewById(R.id.btnAddEvent);
        btnAddEvent.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityHomeStudent.this, ActivityEditEvent.class);
            startActivity(intent);
        });

        // Ensure that the dbHelper is not null

        Button btnMessage = findViewById(R.id.btnMessage);

        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityHomeStudent.this, LoginUsernameActivity.class);
                intent.putExtra("phone", phoneNumber);
                intent.putExtra("password", password);
                intent.putExtra("selectedRole", selectedRole);
                startActivity(intent);
            }
        });


        // Navigation Bar

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),ActivityHomeStudent.class));
                        return true;

                    case R.id.calendar:
                        startActivity(new Intent(getApplicationContext(),ActivityCalendar.class));
                        return true;

                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(),ActivityAccount.class));
                        overridePendingTransition(0,0);
                        return true;


                    case R.id.findTutors:
                        startActivity(new Intent(getApplicationContext(),ActivityFindTutor.class));
                        overridePendingTransition(0,0);
                        return true;



                }
                return false;
            }
        });


    }
    private void startNewActivity(Class<?> targetActivity) {
        Intent intent = new Intent(ActivityHomeStudent.this, targetActivity);
        intent.putExtra("phone", phoneNumber);
//        intent.putExtra("password", password);
//        intent.putExtra("selectedRole", selectedRole);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }


}

