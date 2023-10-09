package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityTutorItemInfo extends AppCompatActivity {


    DBHelper dbHelper;
    TextView FirstName, Lastname, TotalStudentTaught, YearsOfExperience ,TotalTutorHours , aboutMe, school, uni,  extraNotes, pricePerHour;
    ImageView imgTutProfilePicList;
    ListView  reviewText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_item_info);

        FirstName = findViewById(R.id.txtTutNameSurnameView);
        Lastname = findViewById(R.id.txtSurname);
        TotalStudentTaught = findViewById(R.id.txtTotalStudsTaughtProfileVal);
        YearsOfExperience = findViewById(R.id.txtTutYrsExp);
        TotalTutorHours = findViewById(R.id.txtTutTotalHrsView);
        aboutMe = findViewById(R.id.txt_EventNotes);
        school = findViewById(R.id.txtTutSchoolView);
        uni = findViewById(R.id.txtTutUniView);
        reviewText = findViewById(R.id.txtTutReviewsList);
        extraNotes = findViewById(R.id.txtTutNotesView);
        pricePerHour = findViewById(R.id.txtPricePerHrView);
        imgTutProfilePicList = findViewById(R.id.shapeableImageView);

        String name = getIntent().getStringExtra("Name");
        String surname = getIntent().getStringExtra("Surname");
        String yof = getIntent().getStringExtra("YOE");
        String tth = getIntent().getStringExtra("TTH");
        String pph = getIntent().getStringExtra("PPH");

        FirstName.setText(name.toString());
        Lastname.setText(surname.toString());
        YearsOfExperience.setText(yof.toString());
        TotalTutorHours.setText(tth.toString());
        pricePerHour.setText(pph.toString());




//
//
//        int tutorId = Integer.parseInt(getIntent().getStringExtra("tutorID"));
//
//
//
//        Cursor cursor = dbHelper.viewTutorProfileData(tutorId);
//        if (cursor != null && cursor.moveToFirst()) {
//
//            if (cursor.getCount() == 0) {
//                Toast.makeText(ActivityTutorItemInfo.this, "No events to show", Toast.LENGTH_LONG).show();
//            } else {
//                while (cursor.moveToNext()) {
//
//                   // tutorID.add(cursor.getInt(0)); // tutorID
////                    FirstName.setText(cursor.getString(1)); // firstName
////                    Lastname.setText(cursor.getString(2)); // lastname
////                    TotalStudentTaught.setText(cursor.getString(3)); // TotalStudentTaught
//                    YearsOfExperience.setText(cursor.getString(4)); // YearsOfExperience
//                    TotalTutorHours.setText(cursor.getString(5)); // TotalTutorHours
//                    aboutMe.setText(cursor.getString(6)); // aboutMe
//                    school.setText(cursor.getString(7)); // school
//                    uni.setText(cursor.getString(8)); // uni
//                   // reviewText.setT(cursor.getString(9)); // reviewText
//                    extraNotes.setText(cursor.getString(10)); // eventNotes
//                    pricePerHour.setText(cursor.getString(11)); // pricePerHour
//
//                }
//                Toast.makeText(ActivityTutorItemInfo.this, "name: " + FirstName, Toast.LENGTH_SHORT).show();
//
//            }





        }

//        // Close the cursor when done with it
//        if (cursor != null) {
//            cursor.close();
//        }
//    }

}


