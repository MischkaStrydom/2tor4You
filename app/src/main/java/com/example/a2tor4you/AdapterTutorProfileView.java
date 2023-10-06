package com.example.a2tor4you;  // Tutor Profile View adapter

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterTutorProfileView extends RecyclerView.Adapter<AdapterTutorProfileView.MyViewHolder>{

    private Context context;
    private ArrayList firstName, lastname, TotalStudentTaught, YearsOfExperience ,TotalTutorHours , aboutMe, pricePerHour;

    AdapterTutorProfileView(Context context,
                  ArrayList firstName,
                  ArrayList lastname,
                  ArrayList TotalStudentTaught,
                  ArrayList YearsOfExperience,
                  ArrayList TotalTutorHours,
                  ArrayList aboutMe,
                  ArrayList pricePerHour){

        this.context = context;
        this.firstName = firstName;
        this.lastname = lastname;
        this.TotalStudentTaught = TotalStudentTaught;
        this.YearsOfExperience = YearsOfExperience;
        this.TotalTutorHours = TotalTutorHours;
        this.aboutMe = aboutMe;
        this.pricePerHour = pricePerHour;

    }

    @NonNull
    @Override
    public AdapterTutorProfileView.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
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
        holder.pricePerHour.setText(String.valueOf(pricePerHour.get(position)));
    }

    @Override
    public int getItemCount() {
        return firstName.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView firstName, lastname, TotalStudentTaught, YearsOfExperience, TotalTutorHours, aboutMe, pricePerHour;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //commented out bec was showing errors

            /*firstName = itemView.findViewById(R.id.firstName);
            lastname = itemView.findViewById(R.id.lastname);
            TotalStudentTaught = itemView.findViewById(R.id.TotalStudentTaught);
            YearsOfExperience = itemView.findViewById(R.id.YearsOfExperience);
            TotalTutorHours = itemView.findViewById(R.id.TotalTutorHours);
            aboutMe = itemView.findViewById(R.id.aboutMe);
            pricePerHour = itemView.findViewById(R.id.pricePerHour);*/

        }
    }
}