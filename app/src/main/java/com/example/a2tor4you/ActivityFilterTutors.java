package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class ActivityFilterTutors extends AppCompatActivity {
    boolean isHandlingCheckedChange = false;
    RadioButton rdoOnlineFilter, rdoInPersonFilter, rdoQualifiedTeacher;
    Boolean isOnline, isOffline, isTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_tutors);
//        RadioGroup rdoGroupGrade1 = findViewById(R.id.rdoGroupGrade1);
//        RadioGroup rdoGroupGrade2 = findViewById(R.id.rdoGroupGrade2);




        // RadioButtons
        rdoOnlineFilter = findViewById(R.id.rdoOnlineFilter);
        rdoInPersonFilter = findViewById(R.id.rdoInPersonFilter);

        rdoQualifiedTeacher = findViewById(R.id.rdoQualifiedTeacher);

        ImageButton btnBackFilterTutors = findViewById(R.id.btnBackFilterTutors);
        btnBackFilterTutors.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),ActivityFindTutor.class));
        });

        Button btn_ShowResults = findViewById(R.id.btn_ShowResults);


        SeekBar price = findViewById(R.id.seekBarHourlyRate);
        TextView txtPrice =findViewById(R.id.txtPrice);
        price.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtPrice.setText("R" + String.valueOf(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
//        rdoGroupGrade1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                if (!isHandlingCheckedChange) {
//                    isHandlingCheckedChange = true;
//                    rdoGroupGrade2.clearCheck();
//                    isHandlingCheckedChange = false;
//                }
//            }
//        });
//
//        rdoGroupGrade2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                if (!isHandlingCheckedChange) {
//                    isHandlingCheckedChange = true;
//                    rdoGroupGrade1.clearCheck();
//                    isHandlingCheckedChange = false;
//                }
//            }
//        });

        rdoOnlineFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!rdoOnlineFilter.isSelected()) {
                    rdoOnlineFilter.setChecked(true);
                    rdoOnlineFilter.setSelected(true);
                } else {
                    rdoOnlineFilter.setChecked(false);
                    rdoOnlineFilter.setSelected(false);
                }
            }
        });

        rdoInPersonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!rdoInPersonFilter.isSelected()) {
                    rdoInPersonFilter.setChecked(true);
                    rdoInPersonFilter.setSelected(true);
                } else {
                    rdoInPersonFilter.setChecked(false);
                    rdoInPersonFilter.setSelected(false);
                }
            }
        });



        rdoQualifiedTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!rdoQualifiedTeacher.isSelected()) {
                    rdoQualifiedTeacher.setChecked(true);
                    rdoQualifiedTeacher.setSelected(true);
                } else {
                    rdoQualifiedTeacher.setChecked(false);
                    rdoQualifiedTeacher.setSelected(false);
                }
            }
        });


        btn_ShowResults.setOnClickListener(v -> {

//            String selectedGrade = getIntent().getStringExtra("selectedGrade");
//            Boolean math = getIntent().getBooleanExtra("math", false);
//            Boolean history = getIntent().getBooleanExtra("history", false);
//            Boolean afrikaans = getIntent().getBooleanExtra("afrikaans",false);
//            Boolean english = getIntent().getBooleanExtra("english",false);
          //  String selectedGrade = getSelectedGrade(rdoGroupGrade1, rdoGroupGrade2);



            Intent intent = new Intent(getApplicationContext(), ActivityFindTutor.class);
         //   intent.putExtra("selectedGrades", selectedGrade);
//            intent.putExtra("selectedSubjects", math);
//            intent.putExtra("selectedSubjects", history);
//            intent.putExtra("selectedSubjects", afrikaans);
//            intent.putExtra("selectedSubjects", english);
            intent.putExtra("onlineFilter", rdoOnlineFilter.isChecked());
            intent.putExtra("inPersonFilter", rdoInPersonFilter.isChecked());
            intent.putExtra("qualifiedTeacherFilter", rdoQualifiedTeacher.isChecked());
            intent.putExtra("maxPricePerHour", price.getProgress());
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
}