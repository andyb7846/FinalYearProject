package com.example.andy.prototype2.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.andy.prototype2.R;
import com.example.andy.prototype2.model.Property;

import java.util.ArrayList;

public class PropertiesAdapter extends ArrayAdapter<Property>{

    TextView textStreetName;
    TextView textHouseNumber;
    TextView textPostCode;
    TextView textYearlyCost;

    public PropertiesAdapter(Context context, ArrayList<Property> propertys) {
        super(context, 0, propertys);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Property property = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_properties_list, parent, false);
        }
        // Lookup view for data population
        textStreetName = (TextView) convertView.findViewById(R.id.text_street_name);
        textHouseNumber = (TextView) convertView.findViewById(R.id.text_house_number);
        textPostCode = (TextView) convertView.findViewById(R.id.text_post_code);
        textYearlyCost = (TextView) convertView.findViewById(R.id.text_yearly_cost);
        // Populate the data into the template view using the data object
        textStreetName.setText(property.getStreet_name());
        textHouseNumber.setText(property.getHouse_number());
        textPostCode.setText(property.getPost_code());
        textYearlyCost.setText(property.getYearly_cost() + "");
        // Return the completed view to render on screen
        return convertView;
    }
}