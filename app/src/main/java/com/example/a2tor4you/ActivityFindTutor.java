package com.example.a2tor4you;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivityFindTutor extends AppCompatActivity {

    RecyclerView rvFindTutor;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_tutor);

        //RecyclerView for Tutor Profiles ListView

        rvFindTutor = findViewById(R.id.rvFindTutor);
        rvFindTutor.setLayoutManager(new LinearLayoutManager(this));
        rvFindTutor.setAdapter(new RvAdapter());

        // Navigation Bar

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.findTutors);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.findTutors:
                        return true;

                    case R.id.calendar:
                        startActivity(new Intent(getApplicationContext(), ActivityCalendar.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), ActivityHomeStudent.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.messages:
                        startActivity(new Intent(getApplicationContext(), ChatMainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });
    }

    //RecyclerView for Tutor Profiles ListView - Continued

    class tutorProfileItemHolder extends RecyclerView.ViewHolder {
        public tutorProfileItemHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class RvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_find_tutor_item, parent, false);
                return new tutorProfileItemHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
            /*if (position == 0)
                return 1;
            return position % 3;*/
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }
}
