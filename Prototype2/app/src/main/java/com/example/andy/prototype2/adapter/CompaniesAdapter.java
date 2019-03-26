package com.example.andy.prototype2.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.andy.prototype2.R;
import com.example.andy.prototype2.model.Company;

import java.util.ArrayList;

public class CompaniesAdapter extends ArrayAdapter<Company> {
    public CompaniesAdapter(Context context, ArrayList<Company> companies) {
        super(context, 0, companies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Company company = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_companies_list, parent, false);
        }
        // Lookup view for data population
        TextView textCompanyName = (TextView) convertView.findViewById(R.id.text_company_name);
        TextView textEmployeesAndProperties = (TextView) convertView.findViewById(R.id.text_employees_and_propeties);
        TextView textDevicesAndVehicles = (TextView) convertView.findViewById(R.id.text_devices_and_vehicles);
        // Populate the data into the template view using the data object
        textCompanyName.setText(company.getName());
        textEmployeesAndProperties.setText("Employees: " + company.getEmployees() + " Properties: " + company.getProperties());
        textDevicesAndVehicles.setText("Devices: " + company.getDevices() + " Vehicles: " + company.getVehicles());

        // Return the completed view to render on screen
        return convertView;
    }
}