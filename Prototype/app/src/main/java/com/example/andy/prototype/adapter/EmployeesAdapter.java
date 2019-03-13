package com.example.andy.prototype.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.andy.prototype.R;
import com.example.andy.prototype.model.Employee;

import java.util.ArrayList;

public class EmployeesAdapter extends ArrayAdapter<Employee> implements View.OnClickListener{

    private ListView mListView;
    Button btnEmployee;

    TextView textEmployeeTaxId;
    TextView textEmployeePosition;
    TextView textEmployeeSalary;

    public EmployeesAdapter(Context context, ArrayList<Employee> employees, ListView listView) {
        super(context, 0, employees);
        this.mListView = listView;
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
        btnEmployee = (Button) convertView.findViewById(R.id.btn_employee);

        textEmployeeTaxId = (TextView) convertView.findViewById(R.id.text_tax_id);
        textEmployeePosition = (TextView) convertView.findViewById(R.id.text_position);
        textEmployeeSalary = (TextView) convertView.findViewById(R.id.text_salary);
        // Populate the data into the template view using the data object
        btnEmployee.setText(employee.getName());
        btnEmployee.setOnClickListener(this);

        textEmployeePosition.setText(employee.getPosition());
        textEmployeeSalary.setText(employee.getSalary() + "");
        textEmployeeTaxId.setText(employee.getTaxId() + "");
        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public void onClick(View v) {
        if(textEmployeePosition.getVisibility() == View.VISIBLE){
            textEmployeePosition.setVisibility(View.GONE);
            textEmployeeSalary.setVisibility(View.GONE);
            textEmployeeTaxId.setVisibility(View.GONE);
        }
        else{
            textEmployeePosition.setVisibility(View.VISIBLE);
            textEmployeeSalary.setVisibility(View.VISIBLE);
            textEmployeeTaxId.setVisibility(View.VISIBLE);
        }

        //setDynamicHeight();
    }

    public void setDynamicHeight() {

        int height = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(this.mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        for (int i = 0; i < this.getCount(); i++) {
            View listItem = this.getView(i, null, this.mListView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            height += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = this.mListView.getLayoutParams();
        params.height = height + (this.mListView.getDividerHeight() * (this.getCount() - 1));
        this.mListView.setLayoutParams(params);
        this.mListView.requestLayout();
    }
}