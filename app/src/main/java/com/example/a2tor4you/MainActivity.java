package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView btn = findViewById(R.id.txtLogin);
        btn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,ActivityTutorProfile.class)));

        Button btnTutor = findViewById(R.id.btnTutor);
        btnTutor.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,ActivityRegisterTutor.class)));

        Button btnStudent = findViewById(R.id.btnStudent);
        btnStudent.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,ActivityRegisterStudent.class)));
    }
}