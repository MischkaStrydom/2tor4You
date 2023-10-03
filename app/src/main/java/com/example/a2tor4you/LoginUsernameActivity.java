package com.example.a2tor4you;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.a2tor4you.Model.UserModel;
import com.example.a2tor4you.utils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

public class LoginUsernameActivity extends AppCompatActivity {

    EditText usernameInput;
    Button letMeInBtn;
    ImageView btnBack;
    BottomNavigationView bottomNavigationView;
    String phoneNumber;
    String userName;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_username);



        String password = getIntent().getStringExtra("password");
        String selectedRole = getIntent().getStringExtra("selectedRole");
        String userID = getIntent().getStringExtra("userID");


        usernameInput = findViewById(R.id.txtUsername);
        letMeInBtn = findViewById(R.id.btnLetMeIn);

        //On button back takes user back to Account Home screen


        phoneNumber = getIntent().getStringExtra("phone");

        getUsername();

        letMeInBtn.setOnClickListener((v -> {
            setUsername();
        }));

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.account);

        // Navigation Bar

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.messages:
                        return true;

                    case R.id.home:
                        //    startActivity(new Intent(getApplicationContext(), ActivityHomeStudent.class));

                        Intent intent3 = new Intent(LoginUsernameActivity.this,ActivityHomeStudent.class);
                        // intent2.putExtra("phone", LoginOtpActivity.phoneNumber);
                        intent3.putExtra("phone", phoneNumber);
                        intent3.putExtra("password", password);
                        intent3.putExtra("selectedRole", selectedRole);

                        startActivity(intent3);

                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.calendar:
                        //  startActivity(new Intent(getApplicationContext(), ActivityCalendar.class));

                        Intent intent = new Intent(LoginUsernameActivity.this,ActivityCalendar.class);
                        // intent.putExtra("phone", LoginOtpActivity.phoneNumber);
                        intent.putExtra("phone", phoneNumber);
                        intent.putExtra("password", password);
                        intent.putExtra("selectedRole", selectedRole);

                        startActivity(intent);


                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.findTutors:
                        //  startActivity(new Intent(getApplicationContext(), ActivityFindTutor.class));

                        Intent intent1 = new Intent(LoginUsernameActivity.this,ActivityFindTutor.class);
                        // intent.putExtra("phone", LoginOtpActivity.phoneNumber);
                        intent1.putExtra("phone", phoneNumber);
                        intent1.putExtra("password", password);
                        intent1.putExtra("selectedRole", selectedRole);

                        startActivity(intent1);

                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.account:
                        //  startActivity(new Intent(getApplicationContext(), ChatMainActivity.class));

                        Intent intent2 = new Intent(LoginUsernameActivity.this,ActivityAccount.class);
                        // intent2.putExtra("phone", LoginOtpActivity.phoneNumber);
                        intent2.putExtra("phone", phoneNumber);
                        intent2.putExtra("password", password);
                        intent2.putExtra("selectedRole", selectedRole);

                        startActivity(intent2);

                        overridePendingTransition(0, 0);
                        return true;



                }
                return false;
            }
        });

    }

    void setUsername() {

        String username = usernameInput.getText().toString();
        if (username.isEmpty() || username.length() < 3) {
            usernameInput.setError("Username length should be at least 3 chars");
            return;
        }

        if (userModel != null) {
            userModel.setUsername(username);
        } else {
            userModel = new UserModel(phoneNumber,username, Timestamp.now(), FirebaseUtil.currentUserId());
        }
        FirebaseUtil.currentUserDetails().set(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Intent intent = new Intent(LoginUsernameActivity.this,ChatMainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                    startActivity(intent);
                }
            }
        });
    }
    void getUsername() {

        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    userModel = task.getResult().toObject(UserModel.class);
                    if (userModel != null) {
                        usernameInput.setText(userModel.getUsername());
                    }
                }
            }
        });
    }



}
