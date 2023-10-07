package com.example.a2tor4you;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapterTutor extends RecyclerView.Adapter<adapterTutor.MyViewHolder>{

    private final ArrayList firstName, lastname,YearsOfExperience,TotalTutorHours,pricePerHour;
    private final ArrayList<byte[]> imageBytes; ;
    private Context context;
    adapterTutor(Context context,
                  ArrayList firstName,
                  ArrayList lastname,
                  ArrayList YearsOfExperience,
                  ArrayList TotalTutorHours,
                  ArrayList pricePerHour,
                 ArrayList<byte[]> imageBytes

    ){
        this.context = context;
        this.firstName = firstName;
        this.lastname = lastname;
        this.YearsOfExperience = YearsOfExperience;
        this.TotalTutorHours = TotalTutorHours;
        this.pricePerHour = pricePerHour;
        this.imageBytes = imageBytes;
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
        // Decode the byte array and set it as the image source
        byte[] imageBlob = imageBytes.get(position);
        if (imageBlob != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.length);
            holder.tutorImage.setImageBitmap(bitmap);
        } else {
            // Handle the case when the image data is null or not available
            // You can set a placeholder image or handle it as needed.
            holder.tutorImage.setImageResource(R.drawable.astronaught1); // Placeholder image resource
        }
    }

    @Override
    public int getItemCount() {
        return firstName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView firstName, lastname, YearsOfExperience, TotalTutorHours, pricePerHour;
        ImageView tutorImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.txtTutNameSurnameView);
            lastname = itemView.findViewById(R.id.txtSurname);
            YearsOfExperience = itemView.findViewById(R.id.txtTutYrsExp);
            TotalTutorHours = itemView.findViewById(R.id.txtTutTotalHrsView);
            pricePerHour = itemView.findViewById(R.id.txtPricePerHrView);
            tutorImage = itemView.findViewById(R.id.imgTutProfilePicList);

        }
    }
}
