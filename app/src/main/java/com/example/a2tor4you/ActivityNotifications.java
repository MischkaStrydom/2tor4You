package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

public class ActivityNotifications extends AppCompatActivity {

    DBHelper dbHelper ;
    DBHelper myDB;
    SwitchCompat email,message;
    Boolean isCheckedEmail, isCheckedMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        ImageView btnBack = findViewById(R.id.btnBackNotifications);
        btnBack.setOnClickListener(view -> startActivity(new Intent(ActivityNotifications.this,ActivityAccount.class)));

        dbHelper = new DBHelper(this);

        myDB = new DBHelper(this); // Initialize myDB

        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int loggedInUserId = preferences.getInt("loggedInUserId", -1); // -1 is a default value if key not found

         email = findViewById(R.id.btnEmail);
         message = findViewById(R.id.btnMessage);
        Button btnSavePrefs = findViewById(R.id.btnSavePrefs);
        //Adding Notification Prefs to activity
        if (loggedInUserId != -1) {

            Boolean UserEmail =dbHelper.getInfo("NotificationPreference", loggedInUserId, "emailNotificationsEnabled");
            Boolean UserMess = dbHelper.getInfo("NotificationPreference", loggedInUserId, "messagesNotificationsEnabled");

               email.setChecked(UserEmail);
               message.setChecked(UserMess);

            btnSavePrefs.setOnClickListener(v -> {
            if(email.isChecked()){
                isCheckedEmail = true;
            }
            else{
                isCheckedEmail = false;
            }

            if(message.isChecked()){
                isCheckedMessage = true;
            }
            else{
                isCheckedMessage = false;
            }

            Boolean Email = isCheckedEmail;
            Boolean Message = isCheckedMessage;

            ContentValues eventValues = new ContentValues();

            eventValues.put("emailNotificationsEnabled", Email);
            eventValues.put("messagesNotificationsEnabled", Message);

            boolean insertData = myDB.updateUserData("NotificationPreference", loggedInUserId, eventValues);

            if(insertData)
            {
                Toast.makeText(ActivityNotifications.this, "Preferences added successfully", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(ActivityNotifications.this, "Preferences NOT added", Toast.LENGTH_SHORT).show();

            }

        });
    }
}}