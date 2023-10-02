package com.example.a2tor4you;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SouthAfricaData {
    public static void setupCitySpinner(final Context context, final Spinner spinnerProvince, final Spinner spinnerCity) {
        final ArrayAdapter<CharSequence> cityAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(cityAdapter);

        spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedProvince = parentView.getItemAtPosition(position).toString();
                cityAdapter.clear();
                cityAdapter.add("City"); // Add "City" as the default option

                // Populate the city spinner based on the selected province
                if ("Eastern Cape".equals(selectedProvince)) {
                    cityAdapter.addAll(context.getResources().getStringArray(R.array.eastern_cape_cities));
                } else if ("Free State".equals(selectedProvince)) {
                    cityAdapter.addAll(context.getResources().getStringArray(R.array.free_state_cities));
                } else if ("Gauteng".equals(selectedProvince)) {
                    cityAdapter.addAll(context.getResources().getStringArray(R.array.gauteng_cities));
                } else if ("KwaZulu-Natal".equals(selectedProvince)) {
                    cityAdapter.addAll(context.getResources().getStringArray(R.array.kwa_zulu_natal_cities));
                } else if ("Limpopo".equals(selectedProvince)) {
                    cityAdapter.addAll(context.getResources().getStringArray(R.array.limpopo_cities));
                } else if ("Mpumalanga".equals(selectedProvince)) {
                    cityAdapter.addAll(context.getResources().getStringArray(R.array.mpumalanga_cities));
                } else if ("North West".equals(selectedProvince)) {
                    cityAdapter.addAll(context.getResources().getStringArray(R.array.north_west_cities));
                } else if ("Northern Cape".equals(selectedProvince)) {
                    cityAdapter.addAll(context.getResources().getStringArray(R.array.northern_cape_cities));
                } else if ("Western Cape".equals(selectedProvince)) {
                    cityAdapter.addAll(context.getResources().getStringArray(R.array.western_cape_cities));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing here
            }
        });
    }
}