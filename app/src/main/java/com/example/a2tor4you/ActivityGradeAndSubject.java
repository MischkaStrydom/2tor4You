package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class ActivityGradeAndSubject extends AppCompatActivity {
    boolean isHandlingCheckedChange = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_and_subject);

        RadioGroup rdoGroupGrade1 = findViewById(R.id.rdoGroupGrade1);
        RadioGroup rdoGroupGrade2 = findViewById(R.id.rdoGroupGrade2);
        Button next = findViewById(R.id.btnNextFindTutor);
        ImageButton back = findViewById(R.id.btnBackFindTutor);

        next.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),ActivityFilterTutors.class));
        });

        back.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),ActivityFindTutor.class));
        });

        rdoGroupGrade1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (!isHandlingCheckedChange) {
                    isHandlingCheckedChange = true;
                    rdoGroupGrade2.clearCheck();
                    isHandlingCheckedChange = false;
                }
            }
        });

        rdoGroupGrade2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (!isHandlingCheckedChange) {
                    isHandlingCheckedChange = true;
                    rdoGroupGrade1.clearCheck();
                    isHandlingCheckedChange = false;
                }
            }
        });



    }


}