package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class ActivityDeleteAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        ImageView btnBack = findViewById(R.id.btnBackDeleteAccount);
        btnBack.setOnClickListener(view -> startActivity(new Intent(ActivityDeleteAccount.this,ActivitySettings.class)));
    }
}