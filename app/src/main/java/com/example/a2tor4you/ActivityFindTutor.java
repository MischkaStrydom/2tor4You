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


        String phoneNumber = getIntent().getStringExtra("phone");
        String password = getIntent().getStringExtra("password");
        String selectedRole = getIntent().getStringExtra("selectedRole");
        String userID = getIntent().getStringExtra("userID");


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

                    case R.id.home:
                        //startActivity(new Intent(getApplicationContext(), ActivityHomeStudent.class));

                        Intent intent1 = new Intent(ActivityFindTutor.this,ActivityHomeStudent.class);
                        // intent.putExtra("phone", LoginOtpActivity.phoneNumber);
                        intent1.putExtra("phone", phoneNumber);
                        intent1.putExtra("password", password);
                        intent1.putExtra("selectedRole", selectedRole);

                        startActivity(intent1);

                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.calendar:
                      //  startActivity(new Intent(getApplicationContext(), ActivityCalendar.class));

                        Intent intent = new Intent(ActivityFindTutor.this,ActivityCalendar.class);
                        // intent.putExtra("phone", LoginOtpActivity.phoneNumber);
                        intent.putExtra("phone", phoneNumber);
                        intent.putExtra("password", password);
                        intent.putExtra("selectedRole", selectedRole);

                        startActivity(intent);

                        overridePendingTransition(0, 0);
                        return true;



                    case R.id.messages:
                      //  startActivity(new Intent(getApplicationContext(), ChatMainActivity.class));

                        Intent intent2 = new Intent(ActivityFindTutor.this,LoginUsernameActivity.class);
                        // intent2.putExtra("phone", LoginOtpActivity.phoneNumber);
                        intent2.putExtra("phone", phoneNumber);
                        intent2.putExtra("password", password);
                        intent2.putExtra("selectedRole", selectedRole);

                        startActivity(intent2);

                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.account:
                        //startActivity(new Intent(getApplicationContext(), ActivityAccount.class));

                        Intent intent3 = new Intent(ActivityFindTutor.this,ActivityAccount.class);
                        // intent2.putExtra("phone", LoginOtpActivity.phoneNumber);
                        intent3.putExtra("phone", phoneNumber);
                        intent3.putExtra("password", password);
                        intent3.putExtra("selectedRole", selectedRole);

                        startActivity(intent3);

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
