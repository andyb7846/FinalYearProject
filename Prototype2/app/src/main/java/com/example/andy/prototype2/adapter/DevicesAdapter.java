package com.example.andy.prototype2.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.andy.prototype2.R;
import com.example.andy.prototype2.model.Device;

import java.util.ArrayList;

public class DevicesAdapter extends ArrayAdapter<Device>{

    TextView textBrand;
    TextView textModel;
    TextView textYearlyCost;

    public DevicesAdapter(Context context, ArrayList<Device> devices) {
        super(context, 0, devices);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Device device = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_devices_list, parent, false);
        }
        // Lookup view for data population
        textBrand = (TextView) convertView.findViewById(R.id.text_brand);
        textModel = (TextView) convertView.findViewById(R.id.text_model);
        textYearlyCost = (TextView) convertView.findViewById(R.id.text_yearly_cost);
        // Populate the data into the template view using the data object
        textBrand.setText(device.getBrand());
        textModel.setText(device.getModel());
        textYearlyCost.setText(device.getYearly_cost() + "");
        // Return the completed view to render on screen
        return convertView;
    }
}