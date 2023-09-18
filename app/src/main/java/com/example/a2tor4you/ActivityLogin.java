package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class ActivityLogin extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        String[] items = {"Role", "Student", "Tutor"};
//        int[] icons = {R.drawable.ic_person, R.drawable.ic_person, R.drawable.ic_tutor};
//
//        Spinner coloredSpinner = findViewById(R.id.spinRole); // Replace with your Spinner's ID
//
//        CustomSpinner adapter = new CustomSpinner(this, items, icons);
//        coloredSpinner.setAdapter(adapter);

        ImageButton btnBack = findViewById(R.id.btnBackLogin);
        btnBack.setOnClickListener(view -> startActivity(new Intent(ActivityLogin.this,MainActivity.class)));

        TextView btn = findViewById(R.id.txtSignUp);
        btn.setOnClickListener(view -> startActivity(new Intent(ActivityLogin.this,MainActivity.class)));




    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}