package com.example.a2tor4you;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class CustomSpinner extends ArrayAdapter<String> {
    private final Context context;
    private final String[] items;
    private final int[] icons; // Array to store resource IDs for icons

    public CustomSpinner(Context context, String[] items, int[] icons) {
        super(context, R.layout.spinner_item, items);
        this.context = context;
        this.items = items;
        this.icons = icons;
    }

    public CustomSpinner(@NonNull Context context, int resource, @NonNull List<String> objects, Context context1, String[] items, int[] icons) {
        super(context, resource, objects);
        this.context = context1;
        this.items = items;
        this.icons = icons;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createCustomView(position, convertView, parent);
    }

    private View createCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.spinner_item, parent, false);

        ImageView imageView = rowView.findViewById(R.id.spinner_icon);
        TextView textView = rowView.findViewById(R.id.spinner_text);

        imageView.setImageResource(icons[position]);
        textView.setText(items[position]);

        return rowView;
    }
}
