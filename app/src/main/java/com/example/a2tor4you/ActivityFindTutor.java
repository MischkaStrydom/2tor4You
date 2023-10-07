package com.example.a2tor4you;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class ActivityFindTutor extends AppCompatActivity {

    ArrayList<String> firstName, lastname, YearsOfExperience ,TotalTutorHours , pricePerHour;
    private ArrayList<byte[]> imageBytes;
    adapterTutor adapterTutor;

    ArrayList<String> listItem;
    ArrayAdapter adapter;

    ListView user_list;

    DBHelper dbHelper ;

    View _baseView;

    RecyclerView rvFindTutor;
    static String phoneNumber;
    static String password;
    static String selectedRole;
    BottomNavigationView bottomNavigationView;

    static int loggedInUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_tutor);

        /*dbHelper = new DBHelper(this);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        loggedInUserId = preferences.getInt("loggedInUserId", -1); // -1 is a default value if key not found
*/

         phoneNumber = getIntent().getStringExtra("phone");
//        password = getIntent().getStringExtra("password");
//         electedRole = getIntent().getStringExtra("selectedRole");

        ImageView filter = findViewById(R.id.filter);
        filter.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),ActivityGradeAndSubject.class));
        });


        dbHelper = new DBHelper(this);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        loggedInUserId = preferences.getInt("loggedInUserId", -1); // -1 is a default value if key not found

        //Testing recycle view
        dbHelper = new DBHelper(ActivityFindTutor.this);

        firstName = new ArrayList<>();
        lastname = new ArrayList<>();
        YearsOfExperience = new ArrayList<>();
        TotalTutorHours = new ArrayList<>();
        pricePerHour = new ArrayList<>();
        imageBytes = new ArrayList<>();




        rvFindTutor = findViewById(R.id.rvFindTutor);
       // rvFindTutor = findViewById(R.id.rvFindTutor);
        storeDataInArrays();
        adapterTutor = new adapterTutor(ActivityFindTutor.this, firstName, lastname, YearsOfExperience, TotalTutorHours, pricePerHour, imageBytes);
        rvFindTutor.setAdapter(adapterTutor);
        rvFindTutor.setLayoutManager(new LinearLayoutManager(ActivityFindTutor.this));


        //RecyclerView for Tutor Profiles ListView


//        rvFindTutor.setLayoutManager(new LinearLayoutManager(this));
//        rvFindTutor.setAdapter(new RvAdapter());

        // Navigation Bar

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.findTutors);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.findTutors:
                        startActivity(new Intent(getApplicationContext(),ActivityFindTutor.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),ActivityHomeStudent.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.event:
                        startActivity(new Intent(getApplicationContext(),ActivityEditEvent.class));
                        overridePendingTransition(0,0);
                        return true;


                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(),ActivityAccount.class));
                        overridePendingTransition(0,0);
                        return true;



                }
                return false;
            }
        });

    }
    void storeDataInArrays()
    {
        Cursor cursor = dbHelper.viewTutorData();

        if (cursor.getCount() == 0) {
            Toast.makeText(ActivityFindTutor.this, "No events to show", Toast.LENGTH_LONG).show();
        } else {
            while (cursor.moveToNext()) {

                firstName.add(cursor.getString(0)); // eventTitle
                lastname.add(cursor.getString(1)); // eventDate
                YearsOfExperience.add(cursor.getString(2)); // eventTime
                TotalTutorHours.add(cursor.getString(3)); // eventNotes
                pricePerHour.add(cursor.getString(4)); // eventNotes
                byte[] imageBlob = cursor.getBlob(5);
                imageBytes.add(imageBlob);


            }

        }


    }


}

//    private void startNewActivity(Class<?> targetActivity) {
//        Intent intent = new Intent(ActivityFindTutor.this, targetActivity);
//        intent.putExtra("phone", phoneNumber);
////        intent.putExtra("password", password);
////        intent.putExtra("selectedRole", selectedRole);
//        startActivity(intent);
//        overridePendingTransition(0, 0);
//    }

    //RecyclerView for Tutor Profiles ListView - Continued

//    class tutorProfileItemHolder extends RecyclerView.ViewHolder {
//        public tutorProfileItemHolder(@NonNull View itemView) {
//            super(itemView);
//        }
//    }

//    class RvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//        @NonNull
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_find_tutor_item, parent, false);
//                return new tutorProfileItemHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//
//        }
//
//        @Override
//        public int getItemViewType(int position) {
//            return super.getItemViewType(position);
//            /*if (position == 0)
//                return 1;
//            return position % 3;*/
//        }
//
//        @Override
//        public int getItemCount() {
//            return 10;
//        }
//    }

