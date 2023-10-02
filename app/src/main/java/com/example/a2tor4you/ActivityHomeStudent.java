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


        String userName = dbHelper.getUserName(phoneNumber, password, selectedRole);

        // Ensure that the dbHelper is not null


            if (userName != null) {
                welcome.setText("");
                welcome.setText("Welcome, " + userName);
                // The 'userName' variable now contains the user's first name.
                // You can use it for further customization.
            } else {
                // Handle the case where no results were found or userName is empty.
                // You can display an error message or take appropriate action.
            }


        message = findViewById(R.id.btnMessage);

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityHomeStudent.this,ActivityAccount.class);
               // intent.putExtra("phone", LoginOtpActivity.phoneNumber);
                intent.putExtra("phone", phoneNumber);
                intent.putExtra("password", password);
                intent.putExtra("selectedRole", selectedRole);
                startActivity(intent);
            }
        });



        // Navigation Bar

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.home:
                        return true;

                    case R.id.calendar:
                        startActivity(new Intent(getApplicationContext(), ActivityCalendar.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.findTutors:
                        startActivity(new Intent(getApplicationContext(), ActivityFindTutor.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.messages:
                        startActivity(new Intent(getApplicationContext(), ChatMainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });

    }



//    void setUsername() {
//
//        String username = usernameInput.getText().toString();
//        if (username.isEmpty() || username.length() < 3) {
//            usernameInput.setError("Username length should be at least 3 chars");
//            return;
//        }
//
//        if (userModel != null) {
//            userModel.setUsername(username);
//        } else {
//            userModel = new UserModel(phoneNumber,username, Timestamp.now(), FirebaseUtil.currentUserId());
//        }
//        FirebaseUtil.currentUserDetails().set(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//                if(task.isSuccessful()){
//                    Intent intent = new Intent(LoginUsernameActivity.this,ChatMainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
//                    startActivity(intent);
//                }
//            }
//        });
//    }
//    void getUsername() {
//
//        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//
//                if (task.isSuccessful()) {
//                    userModel = task.getResult().toObject(UserModel.class);
//                    if (userModel != null) {
//                        usernameInput.setText(userModel.getUsername());
//                    }
//                }
//            }
//        });
//    }

}