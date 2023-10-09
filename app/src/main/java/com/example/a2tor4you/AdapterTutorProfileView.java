package com.example.a2tor4you;  // Tutor Profile View adapter

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterTutorProfileView extends RecyclerView.Adapter<AdapterTutorProfileView.MyViewHolder>{

    private Context context;
    private DBHelper dbHelper;
    private final ArrayList tutorID, firstName, lastname, TotalStudentTaught, YearsOfExperience ,TotalTutorHours , aboutMe, school, uni, reviewText, extraNotes, pricePerHour;

    AdapterTutorProfileView(Context context,
                            ArrayList tutorID,
                  ArrayList firstName,
                  ArrayList lastname,
                  ArrayList TotalStudentTaught,
                  ArrayList YearsOfExperience,
                  ArrayList TotalTutorHours,
                  ArrayList aboutMe,
                  ArrayList school,
                  ArrayList uni,
                  ArrayList reviewText,
                  ArrayList extraNotes,
                  ArrayList pricePerHour
    ){

        this.context = context;
        this.tutorID = tutorID;
        this.firstName = firstName;
        this.lastname = lastname;
        this.TotalStudentTaught = TotalStudentTaught;
        this.YearsOfExperience = YearsOfExperience;
        this.TotalTutorHours = TotalTutorHours;
        this.aboutMe = aboutMe;
        this.school = school;
        this.uni = uni;
        this.reviewText = reviewText;
        this.extraNotes = extraNotes;
        this.pricePerHour = pricePerHour;

    }

    @NonNull
    @Override
    public AdapterTutorProfileView.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_tutor_item_info, parent, false);
        return new AdapterTutorProfileView.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.firstName.setText(String.valueOf(firstName.get(position)));
        holder.lastname.setText(String.valueOf(lastname.get(position)));
        holder.TotalStudentTaught.setText(String.valueOf(TotalStudentTaught.get(position)));
        holder.YearsOfExperience.setText(String.valueOf(YearsOfExperience.get(position)));
        holder.TotalTutorHours.setText(String.valueOf(TotalTutorHours.get(position)));
        holder.aboutMe.setText(String.valueOf(aboutMe.get(position)));
        holder.school.setText(String.valueOf(school.get(position)));
        holder.uni.setText(String.valueOf(uni.get(position)));
        holder.reviewText.setText(String.valueOf(reviewText.get(position)));
        holder.extraNotes.setText(String.valueOf(extraNotes.get(position)));
        holder.pricePerHour.setText(String.valueOf(pricePerHour.get(position)));

        /*holder.imgTutProfilePicList.setOnClickListener(v -> {
            int tutorID = Integer.parseInt((String) tutorID.get(position));
            Intent intent = new Intent(context, ActivityTutorProfileView.class);
            intent.putExtra("tutorId", String.valueOf(tutorID.get(position)));
            context.startActivity(intent);
        });*/
    }

    @Override
    public int getItemCount() {
        return firstName.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView firstName, lastname, TotalStudentTaught, YearsOfExperience ,TotalTutorHours , aboutMe, school, uni, reviewText, extraNotes, pricePerHour, imgTutProfilePicList;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //commented out bec was showing errors

            firstName = itemView.findViewById(R.id.txtTutNameSurnameView);
            lastname = itemView.findViewById(R.id.txtSurname);
            TotalStudentTaught = itemView.findViewById(R.id.txtTotalStudsTaughtProfileVal);
            YearsOfExperience = itemView.findViewById(R.id.txtTutYrsExp);
            TotalTutorHours = itemView.findViewById(R.id.txtTutTotalHrsView);
            aboutMe = itemView.findViewById(R.id.txt_EventNotes);
            school = itemView.findViewById(R.id.txtTutSchoolView);
            uni = itemView.findViewById(R.id.txtTutUniView);
            reviewText = itemView.findViewById(R.id.txtTutReviewsList);
            extraNotes = itemView.findViewById(R.id.txtTutNotesView);
            pricePerHour = itemView.findViewById(R.id.txtPricePerHrView);
            //imgTutProfilePicList = itemView.findViewById(R.id.imgTutProfilePicList);

        }
    }
}