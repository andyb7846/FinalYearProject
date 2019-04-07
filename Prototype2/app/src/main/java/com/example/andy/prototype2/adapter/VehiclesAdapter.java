package com.example.andy.prototype2.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.andy.prototype2.R;
import com.example.andy.prototype2.model.Vehicle;

import java.util.ArrayList;

public class VehiclesAdapter extends ArrayAdapter<Vehicle>{

    TextView textManufacturer;
    TextView textModel;
    TextView textRegistration;
    TextView textYearlyCost;

    public VehiclesAdapter(Context context, ArrayList<Vehicle> vehicles) {
        super(context, 0, vehicles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Vehicle vehicle = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_vehicles_list, parent, false);
        }
        // Lookup view for data population
        textManufacturer = (TextView) convertView.findViewById(R.id.text_manufacturer);
        textModel = (TextView) convertView.findViewById(R.id.text_model);
        textRegistration = (TextView) convertView.findViewById(R.id.text_registration);
        textYearlyCost = (TextView) convertView.findViewById(R.id.text_yearly_cost);
        // Populate the data into the template view using the data object
        textManufacturer.setText(vehicle.getManufacturer());
        textModel.setText(vehicle.getModel());
        textRegistration.setText(vehicle.getRegistration());
        textYearlyCost.setText(vehicle.getYearly_cost() + "");
        // Return the completed view to render on screen
        return convertView;
    }
}