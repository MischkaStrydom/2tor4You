package com.example.a2tor4you;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapterTutor extends RecyclerView.Adapter<adapterTutor.MyViewHolder>{

    private final ArrayList firstName, lastname,YearsOfExperience,TotalTutorHours,pricePerHour;
    private Context context;
    adapterTutor(Context context,
                  ArrayList firstName,
                  ArrayList lastname,
                  ArrayList YearsOfExperience,
                  ArrayList TotalTutorHours,
                  ArrayList pricePerHour
    ){
        this.context = context;
        this.firstName = firstName;
        this.lastname = lastname;
        this.YearsOfExperience = YearsOfExperience;
        this.TotalTutorHours = TotalTutorHours;
        this.pricePerHour = pricePerHour;
    }

    @NonNull
    @Override
    public adapterTutor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_find_tutor_item, parent, false);
        return new adapterTutor.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterTutor.MyViewHolder holder, int position) {
        holder.firstName.setText(String.valueOf(firstName.get(position)));
        holder.lastname.setText(String.valueOf(lastname.get(position)));
        holder.YearsOfExperience.setText(String.valueOf(YearsOfExperience.get(position)));
        holder.TotalTutorHours.setText(String.valueOf(TotalTutorHours.get(position)));
        holder.pricePerHour.setText(String.valueOf(pricePerHour.get(position)));
    }

    @Override
    public int getItemCount() {
        return firstName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView firstName, lastname, YearsOfExperience, TotalTutorHours, pricePerHour;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.txtTutNameSurnameView);
            lastname = itemView.findViewById(R.id.txtSurname);
            YearsOfExperience = itemView.findViewById(R.id.txtTutYrsExp);
            TotalTutorHours = itemView.findViewById(R.id.txtTutTotalHrsView);
            pricePerHour = itemView.findViewById(R.id.txtPricePerHrView);


        }
    }
}
