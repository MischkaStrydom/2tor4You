package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityTutorItemInfo extends AppCompatActivity {


    DBHelper dbHelper;
    TextView FirstName, Lastname, TotalStudentTaught, YearsOfExperience ,TotalTutorHours , aboutMe, school, uni,  extraNotes, pricePerHour;
    ImageView imgTutProfilePicList, btnBack;
    ListView  reviewText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_item_info);
        dbHelper = new DBHelper(this);
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

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> startActivity(new Intent(ActivityTutorItemInfo.this,ActivityFindTutor.class)));

        int tutorId = Integer.parseInt(getIntent().getStringExtra("tutorID"));
        String name = getIntent().getStringExtra("Name");
        String surname = getIntent().getStringExtra("Surname");
        String yof = getIntent().getStringExtra("YOE");
        String tth = getIntent().getStringExtra("TTH");
        String pph = getIntent().getStringExtra("PPH");
        // Retrieve the image byte array
        byte[] imageBlob = getIntent().getByteArrayExtra("ImageBytes");

        FirstName.setText(name.toString());
        Lastname.setText(surname.toString());
        YearsOfExperience.setText(yof.toString());
        TotalTutorHours.setText(tth.toString());
        pricePerHour.setText(pph.toString());

        String about = dbHelper.getFieldTutor("Tutor", tutorId, "aboutMe");
        String School = dbHelper.getFieldTutor("Tutor", tutorId, "school");
        String Uni = dbHelper.getFieldTutor("Tutor", tutorId, "uni");
        String ExtraNotes = dbHelper.getFieldTutor("Tutor", tutorId, "extraNotes");

        aboutMe.setText(about);
        school.setText("School: " + School);
        uni.setText("University: " + Uni);
        extraNotes.setText(ExtraNotes);

        if (imageBlob != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.length);
            imgTutProfilePicList.setImageBitmap(bitmap);
        } else {
            // Handle the case when the image data is null or not available
            // You can set a placeholder image or handle it as needed.
            imgTutProfilePicList.setImageResource(R.drawable.astronaught1); // Placeholder image resource
        }



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


