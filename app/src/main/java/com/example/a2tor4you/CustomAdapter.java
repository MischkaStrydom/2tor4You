package com.example.a2tor4you;  // Event adapter

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{

    private Context context;
    private ArrayList eventTitle, eventDate, notes, startTime, locationOnline;
    CustomAdapter(Context context,
                  ArrayList eventTitle,
                  ArrayList eventDate,
                  ArrayList notes,
                  ArrayList startTime,
                  ArrayList locationOnline
                  ){
        this.context = context;
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.notes = notes;
        this.startTime = startTime;
        this.locationOnline = locationOnline;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {

        holder.eventTitle.setText(String.valueOf(eventTitle.get(position)));
        holder.eventDate.setText(String.valueOf(eventDate.get(position)));
        holder.notes.setText(String.valueOf(notes.get(position)));
        holder.startTime.setText(String.valueOf(startTime.get(position)));
        holder.locationOnline.setText(String.valueOf(locationOnline.get(position)));
    }

    @Override
    public int getItemCount() {
        return eventTitle.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView eventTitle, eventDate, notes, startTime, locationOnline;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            eventTitle = itemView.findViewById(R.id.eventTitle);
            eventDate = itemView.findViewById(R.id.eventDate);
            notes = itemView.findViewById(R.id.eventNotes);
            startTime = itemView.findViewById(R.id.startTime);
            locationOnline = itemView.findViewById(R.id.eventLocation);
        }
    }
}

