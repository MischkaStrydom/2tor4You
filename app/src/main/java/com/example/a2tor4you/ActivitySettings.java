package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class ActivitySettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ImageView btnBack = findViewById(R.id.btnBackSettings);
        btnBack.setOnClickListener(view -> startActivity(new Intent(ActivitySettings.this,ActivityAccount.class)));

        ImageView change = findViewById(R.id.btnChange);
        ImageView delete = findViewById(R.id.btnDelete);

        change.setOnClickListener(view -> startActivity(new Intent(ActivitySettings.this,ActivityChangePassword.class)));
        delete.setOnClickListener(view -> startActivity(new Intent(ActivitySettings.this,ActivityDeleteAccount.class)));

    }
}