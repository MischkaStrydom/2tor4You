package com.example.a2tor4you;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.a2tor4you.Model.UserModel;
import com.example.a2tor4you.utils.AndroidUtil;
import com.example.a2tor4you.utils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

public class LoginUsernameActivity extends AppCompatActivity {
    static String phoneNumber;
    static String password;
    static String selectedRole;
    EditText usernameInput;
    Button letMeInBtn;
    ImageView btnBack;
    BottomNavigationView bottomNavigationView;

    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_username);

         password = getIntent().getStringExtra("password");
         selectedRole = getIntent().getStringExtra("selectedRole");

        usernameInput = findViewById(R.id.txtUsername);
        letMeInBtn = findViewById(R.id.btnLetMeIn);

        //On button back takes user back to Account Home screen

        phoneNumber = getIntent().getStringExtra("phone");

        getUsername();

        letMeInBtn.setOnClickListener((v -> {
            setUsername();
        }));

        ImageButton btnBack = findViewById(R.id.btnBackUser);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginUsernameActivity.this,ActivityHomeStudent.class);
                intent.putExtra("phone", phoneNumber);
                intent.putExtra("password", password);
                intent.putExtra("selectedRole", selectedRole);
                startActivity(intent);
            }
        });

        letMeInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAllFields()) {
                    // All fields are filled, proceed with setting the username
                    setUsername();
                } else {
                    // Fields are missing, show an error message
                    AndroidUtil.showToast(getApplicationContext(), "All fields are required.");
                }
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
                    intent.putExtra("phone", phoneNumber);
                    intent.putExtra("password", password);
                    intent.putExtra("selectedRole", selectedRole);
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

    private boolean checkAllFields() {
        // Check if username is entered
        String enteredUsername = usernameInput.getText().toString().trim();

        // Check if username is filled and meets the minimum length requirement
        if (enteredUsername.isEmpty() || enteredUsername.length() < 3) {
            usernameInput.setError("Username length should be at least 3 characters");
            return false;
        }

        return true; // All fields are filled
    }

}
