package com.example.a2tor4you;  // Report adapter

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterReport extends RecyclerView.Adapter<AdapterReport.MyViewHolder>{

    private Context context;
    private DBHelper dbHelper;
    private ArrayList reportID,reportText, reportCategory, reportedAt;

    AdapterReport(Context context,
                  ArrayList reportID,
                  ArrayList reportText,
                  ArrayList reportCategory,
                  ArrayList reportedAt
    ){
        this.context = context;
        this.reportID = reportID;
        this.reportText = reportText;
        this.reportCategory = reportCategory;
        this.reportedAt = reportedAt;
    }

    public void setDbHelper(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public AdapterReport.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.report_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterReport.MyViewHolder holder, int position) {

        holder.reportID.setText(String.valueOf(reportID.get(position)));
        holder.reportText.setText(String.valueOf(reportText.get(position)));
        holder.reportCategory.setText(String.valueOf(reportCategory.get(position)));
        holder.reportedAt.setText(String.valueOf(reportedAt.get(position)));

        holder.btnDeleteReport.setOnClickListener(v -> {
            int reportId = Integer.parseInt((String) reportID.get(position));

            Boolean result = dbHelper.deleteReport(reportId);

            if(result){
                // Remove the event data from ArrayLists
                reportID.remove(position);
                reportText.remove(position);
                reportCategory.remove(position);
                reportedAt.remove(position);


                // Notify the adapter that the data set has changed
                notifyItemRemoved(position);
            }

        });
    }

    @Override
    public int getItemCount() { return reportID.size(); }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView reportID, reportText, reportCategory, reportedAt;
        ImageButton btnDeleteReport;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            reportID = itemView.findViewById(R.id.reportID);
            reportCategory = itemView.findViewById(R.id.reportCategory);
            reportText = itemView.findViewById(R.id.reportText);
            btnDeleteReport = itemView.findViewById(R.id.btnDeleteReport);
            reportedAt = itemView.findViewById(R.id.reportedAt);

        }
    }

}
