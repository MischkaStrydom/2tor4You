package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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


        next.setOnClickListener(v -> {
            String selectedGrade = getSelectedGrade(rdoGroupGrade1, rdoGroupGrade2);
            String selectedSubjects = getSelectedSubjects(); // Implement this method to capture subjects

            // Pass the selected grade and subjects back to ActivityFindTutor
            Intent intent = new Intent(getApplicationContext(), ActivityFilterTutors.class);
            intent.putExtra("selectedGrade", selectedGrade);
            intent.putExtra("selectedSubjects", selectedSubjects);
            startActivity(intent);



        });



    }
    private String getSelectedGrade(RadioGroup rdoGroupGrade1, RadioGroup rdoGroupGrade2) {
        // Check which RadioGroup is selected and return the selected grade
        // You may need to modify this logic based on your RadioGroup setup
        int selectedId = rdoGroupGrade1.getCheckedRadioButtonId();
        if (selectedId == -1) {
            selectedId = rdoGroupGrade2.getCheckedRadioButtonId();
        }

        if (selectedId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedId);
            return selectedRadioButton.getText().toString();
        }

        return "";
    }

    // Implement this method to capture the selected subjects
    private String getSelectedSubjects() {
        StringBuilder selectedSubjectsBuilder = new StringBuilder();

        CheckBox math = findViewById(R.id.chkMath);
        CheckBox history = findViewById(R.id.chkHistory);
        CheckBox afrikaans = findViewById(R.id.chkAfrikaans);
        CheckBox english = findViewById(R.id.chkEnglish);


        // Check if each CheckBox is checked and append its text to the selectedSubjectsBuilder
        if (math.isChecked()) {
            selectedSubjectsBuilder.append("Math").append(", "); // You can modify the delimiter as needed
        }
        if (history.isChecked()) {
            selectedSubjectsBuilder.append("Science").append(", ");
        }
        if (afrikaans.isChecked()) {
            selectedSubjectsBuilder.append("Afrikaans").append(", "); // You can modify the delimiter as needed
        }
        if (english.isChecked()) {
            selectedSubjectsBuilder.append("English").append(", ");
        }

        // Remove the last comma and space if subjects were selected
        if (selectedSubjectsBuilder.length() > 0) {
            selectedSubjectsBuilder.setLength(selectedSubjectsBuilder.length() - 2); // Remove last ", "
        }

        // Return the selected subjects as a string
        return selectedSubjectsBuilder.toString();
    }


}