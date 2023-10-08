package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class ActivityFilterTutors extends AppCompatActivity {

    RadioButton rdoOnlineFilter, rdoInPersonFilter, rdoQualifiedTeacher;
    Boolean isOnline, isOffline, isTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_tutors);

        RatingBar rating = findViewById(R.id.starRatingBar);



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
            ArrayList<String> selectedSubjects = new ArrayList<>();
            if (getIntent().hasExtra("selectedSubjects")) {
                selectedSubjects = getIntent().getStringArrayListExtra("selectedSubjects");
            }

            ArrayList<String> selectedGrades = new ArrayList<>();
            if (getIntent().hasExtra("selectedGrades")) {
                selectedGrades = getIntent().getStringArrayListExtra("selectedGrades");
            }

            Intent intent = new Intent(getApplicationContext(), ActivityFindTutor.class);
            intent.putExtra("selectedSubjects", selectedSubjects);
            intent.putExtra("selectedGrades", selectedGrades);
            intent.putExtra("onlineFilter", rdoOnlineFilter.isChecked());
            intent.putExtra("inPersonFilter", rdoInPersonFilter.isChecked());
            intent.putExtra("qualifiedTeacherFilter", rdoQualifiedTeacher.isChecked());
            intent.putExtra("maxPricePerHour", price.getProgress());
            startActivity(intent);
        });


    }
}