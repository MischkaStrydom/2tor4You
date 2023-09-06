package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ActivityLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String[] items = {"Role", "Student", "Tutor"};
        int[] icons = {R.drawable.ic_person, R.drawable.ic_student, R.drawable.ic_tutor};

        Spinner spinner = findViewById(R.id.spinner); // Replace with your Spinner's ID
        CustomSpinner adapter = new CustomSpinner(this, items, icons);
        spinner.setAdapter(adapter);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> startActivity(new Intent(ActivityLogin.this,MainActivity.class)));

        TextView btn = findViewById(R.id.txtSignUp);
        btn.setOnClickListener(view -> startActivity(new Intent(ActivityLogin.this,MainActivity.class)));
    }
}