package com.example.a2tor4you; //FindTutor Adapter

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapterTutor extends RecyclerView.Adapter<adapterTutor.MyViewHolder>{

    private final ArrayList tutorID, firstName, lastname,YearsOfExperience,TotalTutorHours,pricePerHour;
    private final ArrayList<byte[]> imageBytes; ;
    private Context context;

    private DBHelper dbHelper;
    adapterTutor(Context context,
                  ArrayList tutorID,
                  ArrayList firstName,
                  ArrayList lastname,
                  ArrayList YearsOfExperience,
                  ArrayList TotalTutorHours,
                  ArrayList pricePerHour,
                 ArrayList<byte[]> imageBytes

    ){
        this.context = context;
        this.tutorID = tutorID;
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

        holder.imgTutProfilePicList.setOnClickListener(v -> {
            String tutorId = String.valueOf(tutorID.get(position));

            Intent intent = new Intent(context, ActivityTutorProfileView.class);
            intent.putExtra("tutorId", tutorId);
            // Add more data as extras if needed

            context.startActivity(intent);
        });

       /* holder.imgTutProfilePicList.setOnClickListener(v -> {
            int tutorID = Integer.parseInt((String) tutorID.get(position));
            Intent intent = new Intent(context, ActivityTutorProfileView.class);
            intent.putExtra("tutorId", String.valueOf(tutorID.get(position)));
            context.startActivity(intent);

        });*/

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

    public class MyViewHolder extends RecyclerView.ViewHolder{ //implements View.OnClickListener

        TextView firstName, lastname, YearsOfExperience, TotalTutorHours, pricePerHour;
        ImageView tutorImage, imgTutProfilePicList;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.txtTutNameSurnameView);
            lastname = itemView.findViewById(R.id.txtSurname);
            YearsOfExperience = itemView.findViewById(R.id.txtTutYrsExp);
            TotalTutorHours = itemView.findViewById(R.id.txtTutTotalHrsView);
            pricePerHour = itemView.findViewById(R.id.txtPricePerHrView);
            tutorImage = itemView.findViewById(R.id.imgTutProfilePicList);
            imgTutProfilePicList = itemView.findViewById(R.id.imgTutProfilePicList);
           //itemView.setOnClickListener(this);

        }

        //On item click

      /*  @Override
        public void onClick(View view) {
            int position = getAbsoluteAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                String selectedName = String.valueOf(firstName.get(position));
                Intent intent = new Intent(context, ActivityTutorProfileView.class);
                intent.putExtra("name", selectedName);
                context.startActivity(intent);
            }
        }*/
    }
}
