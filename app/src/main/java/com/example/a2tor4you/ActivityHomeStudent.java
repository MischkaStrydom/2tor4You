package com.example.a2tor4you;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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

    Button message;
    String userInput;

    UserModel userModel;

    DBHelper dbHelper ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_student);
        TextView welcome = findViewById(R.id.txtWelcomeStud);
        dbHelper = new DBHelper(this);

        // Retrieve the userId from the Intent
//        Intent intent = getIntent();
//        int userId = intent.getIntExtra("userId", -1); // -1 is the default value if userId is not found

        phoneNumber = getIntent().getStringExtra("phone");
        password = getIntent().getStringExtra("password");
        selectedRole = getIntent().getStringExtra("selectedRole");
        int userID = dbHelper.getUserId(phoneNumber, password, selectedRole);
        String userName = dbHelper.getUserName(userID);

        if (userID != -1) {


            if (userName != null) {
                welcome.setText("");
                welcome.setText("Welcome, " + userName);

            } else {
                return;
                // Handle the case where no results were found or userName is empty.
                // You can display an error message or take appropriate action.
            }

            // User exists, and userID contains the userID of the matching user.
        } else {
            // No matching user found.
        }


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
                        startNewActivity(ActivityHomeStudent.class);
                        return true;

                    case R.id.calendar:

                        startNewActivity(ActivityCalendar.class);
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


    }
    private void startNewActivity(Class<?> targetActivity) {
        Intent intent = new Intent(ActivityHomeStudent.this, targetActivity);
        intent.putExtra("phone", phoneNumber);
        intent.putExtra("password", password);
        intent.putExtra("selectedRole", selectedRole);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }


}

