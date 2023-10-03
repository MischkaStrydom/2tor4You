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

    BottomNavigationView bottomNavigationView;

    Button message;
    String userInput;

    UserModel userModel;
//    static String phoneNumber;
//    static String password;
//    static String selectedRole;
    DBHelper dbHelper ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_student);
        TextView welcome = findViewById(R.id.txtWelcomeStud);
        dbHelper = new DBHelper(this);

        String phoneNumber = getIntent().getStringExtra("phone");
        String password = getIntent().getStringExtra("password");
        String selectedRole = getIntent().getStringExtra("selectedRole");
        long userID = dbHelper.getUserId(phoneNumber, password, selectedRole);
        String userName = dbHelper.getUserName(userID);
        if (userID != -1) {


            if (userName != null) {
                welcome.setText("");
                welcome.setText("Welcome, " + userName);
                // The 'userName' variable now contains the user's first name.
                // You can use it for further customization.
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





        message = findViewById(R.id.btnMessage);

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityHomeStudent.this,LoginUsernameActivity.class);
               // intent.putExtra("phone", LoginOtpActivity.phoneNumber);
                intent.putExtra("phone", phoneNumber);
                intent.putExtra("password", password);
                intent.putExtra("selectedRole", selectedRole);
                intent.putExtra("FullName", userName);
                intent.putExtra("userID", userID);
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
                        return true;

                    case R.id.calendar:
//                        startActivity(new Intent(getApplicationContext(), ActivityCalendar.class));

                        Intent intent = new Intent(ActivityHomeStudent.this,ActivityCalendar.class);
                        // intent.putExtra("phone", LoginOtpActivity.phoneNumber);
                        intent.putExtra("phone", phoneNumber);
                        intent.putExtra("password", password);
                        intent.putExtra("selectedRole", selectedRole);
                        intent.putExtra("FullName", userName);
                        intent.putExtra("userID", userID);
                        startActivity(intent);

                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.findTutors:
                       // startActivity(new Intent(getApplicationContext(), ActivityFindTutor.class));

                        Intent intent1 = new Intent(ActivityHomeStudent.this,ActivityFindTutor.class);
                        // intent.putExtra("phone", LoginOtpActivity.phoneNumber);
                        intent1.putExtra("phone", phoneNumber);
                        intent1.putExtra("password", password);
                        intent1.putExtra("selectedRole", selectedRole);
                        intent1.putExtra("FullName", userName);
                        intent1.putExtra("userID", userID);
                        startActivity(intent1);

                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.messages:
                     //   startActivity(new Intent(getApplicationContext(), LoginUsernameActivity.class));

                        Intent intent2 = new Intent(ActivityHomeStudent.this,LoginUsernameActivity.class);
                       // intent2.putExtra("phone", LoginOtpActivity.phoneNumber);
                        intent2.putExtra("phone", phoneNumber);
                        intent2.putExtra("password", password);
                        intent2.putExtra("selectedRole", selectedRole);
                        intent2.putExtra("FullName", userName);
                        intent2.putExtra("userID", userID);
                        startActivity(intent2);

                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.account:
                      //  startActivity(new Intent(getApplicationContext(), ActivityAccount.class));
                        Intent intent3 = new Intent(ActivityHomeStudent.this,ActivityAccount.class);
                        // intent2.putExtra("phone", LoginOtpActivity.phoneNumber);
                        intent3.putExtra("phone", phoneNumber);
                        intent3.putExtra("password", password);
                        intent3.putExtra("selectedRole", selectedRole);
                        intent3.putExtra("FullName", userName);
                        intent3.putExtra("userID", userID);
                        startActivity(intent3);


                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

    }



    }

}