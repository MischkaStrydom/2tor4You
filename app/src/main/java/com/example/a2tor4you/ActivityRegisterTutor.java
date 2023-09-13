package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ActivityRegisterTutor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_tutor);

        ImageButton btnBack = findViewById(R.id.btnBackRegisterTutor);
        btnBack.setOnClickListener(view -> startActivity(new Intent(ActivityRegisterTutor.this,MainActivity.class)));
    }
}