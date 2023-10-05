package com.example.a2tor4you;

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
    private ArrayList eventTitle, eventDate, eventNotes, startTime, locationOnline;
    CustomAdapter(Context context,
                  ArrayList eventTitle,
                  ArrayList eventDate,
                  ArrayList eventNotes,
                  ArrayList eventTime,
                  ArrayList locationOnline
                  ){
        this.context = context;
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.eventNotes = eventNotes;
        this.startTime = eventTime;
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
        holder.eventNotes.setText(String.valueOf(eventNotes.get(position)));
        holder.startTime.setText(String.valueOf(startTime.get(position)));
        holder.locationOnline.setText(String.valueOf(locationOnline.get(position)));

    }

    @Override
    public int getItemCount() {
        return eventTitle.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView eventTitle, eventDate, eventNotes, startTime, locationOnline;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            eventTitle = itemView.findViewById(R.id.eventTitle);
            eventDate = itemView.findViewById(R.id.eventDate);
            eventNotes = itemView.findViewById(R.id.eventNotes);
            startTime = itemView.findViewById(R.id.startTime);
            locationOnline = itemView.findViewById(R.id.eventLocation);


        }
    }
}

