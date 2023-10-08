package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class AdminHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        Button btnUserReports = findViewById(R.id.btnUserReports);
        btnUserReports.setOnClickListener(view -> startActivity(new Intent(AdminHome.this,ActivityReportView.class)));
    }
}