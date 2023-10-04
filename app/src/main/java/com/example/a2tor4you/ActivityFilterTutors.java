package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class ActivityFilterTutors extends AppCompatActivity {

    RadioButton rdoOnlineFilter, rdoInPersonFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_tutors);

        // RadioButtons select and deselect
        rdoOnlineFilter = findViewById(R.id.rdoOnlineFilter);
        rdoInPersonFilter = findViewById(R.id.rdoInPersonFilter);

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
    }
}