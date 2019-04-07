package com.example.andy.prototype2.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.andy.prototype2.R;
import com.example.andy.prototype2.model.Employee;

import java.util.ArrayList;

public class EmployeesAdapter extends ArrayAdapter<Employee>{

    TextView textName;
    TextView textTitle;
    TextView textSalary;
    TextView textTax;
    TextView textTaxId;
    TextView textGovTaxCode;

    public EmployeesAdapter(Context context, ArrayList<Employee> employees) {
        super(context, 0, employees);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Employee employee = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_employees_list, parent, false);
        }
        // Lookup view for data population
        textName = (TextView) convertView.findViewById(R.id.text_name);
        textTitle = (TextView) convertView.findViewById(R.id.text_title);
        textSalary = (TextView) convertView.findViewById(R.id.text_salary);
        textTax = (TextView) convertView.findViewById(R.id.text_tax);
        // Populate the data into the template view using the data object
        textName.setText(employee.getForename() + " " + employee.getSurname());
        textTitle.setText(employee.getTitle());
        textSalary.setText(employee.getSalary() + "");
        textTax.setText(employee.getTax() + "");
        // Return the completed view to render on screen
        return convertView;
    }
}