package com.example.andy.prototype.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.example.andy.prototype.R;
import com.example.andy.prototype.adapter.EmployeesAdapter;
import com.example.andy.prototype.model.Employee;

import java.util.ArrayList;

public class CompanyDetailsActivity extends RootActivity implements View.OnClickListener{

    private ListView listMyEmployees, listMyDevices, listMyVehicles, listMyProperties;
    private Button btnMyEmployees, btnMyDevices, btnMyVehicles, btnMyProperties;

    private String [] data1 ={"Hiren", "Pratik", "Dhruv", "Narendra", "Piyush", "Priyank", "AAA", "BBB", "CCC", "DDD"};
    private String [] data2 ={"Kirit", "Miral", "Bhushan", "Jiten", "Ajay", "Kamlesh", "AAA", "BBB", "CCC", "DDD"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_details);

        btnMyEmployees = findViewById(R.id.btn_my_employees);
        btnMyDevices = findViewById(R.id.btn_my_devices);
        btnMyVehicles = findViewById(R.id.btn_my_vehicles);
        btnMyProperties = findViewById(R.id.btn_my_properties);

        btnMyEmployees.setOnClickListener(this);
        btnMyDevices.setOnClickListener(this);
        btnMyVehicles.setOnClickListener(this);
        btnMyProperties.setOnClickListener(this);

        listMyEmployees = (ListView)findViewById(R.id.list_my_employees);
        listMyDevices = (ListView)findViewById(R.id.list_my_devices);
        listMyVehicles = (ListView)findViewById(R.id.list_my_vehicles);
        listMyProperties = (ListView)findViewById(R.id.list_my_properties);

        ArrayList<Employee> employees = new ArrayList<Employee>();
        EmployeesAdapter employeesAdapter = new EmployeesAdapter(this, employees, listMyEmployees);
        listMyEmployees.setAdapter(employeesAdapter);

        employeesAdapter.add(new Employee("John", "Manager", 3000, 400));
        employeesAdapter.add(new Employee("John", "Manager", 3000, 400));
        employeesAdapter.add(new Employee("John", "Manager", 3000, 400));
        employeesAdapter.add(new Employee("John", "Manager", 3000, 400));

        //listMyEmployees.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data1));
        listMyDevices.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data2));
        listMyVehicles.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data2));
        listMyProperties.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data2));

        ListUtils.setDynamicHeight(listMyEmployees);
        ListUtils.setDynamicHeight(listMyDevices);
        ListUtils.setDynamicHeight(listMyVehicles);
        ListUtils.setDynamicHeight(listMyProperties);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_my_employees:
                if(listMyEmployees.getVisibility() == View.VISIBLE){
                    listMyEmployees.setVisibility(View.GONE);
                }
                else{
                    listMyEmployees.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.btn_my_devices:
                if(listMyDevices.getVisibility() == View.VISIBLE){
                    listMyDevices.setVisibility(View.GONE);
                }
                else{
                    listMyDevices.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.btn_my_vehicles:
                if(listMyVehicles.getVisibility() == View.VISIBLE){
                    listMyVehicles.setVisibility(View.GONE);
                }
                else{
                    listMyVehicles.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.btn_my_properties:
                if(listMyProperties.getVisibility() == View.VISIBLE){
                    listMyProperties.setVisibility(View.GONE);
                }
                else{
                    listMyProperties.setVisibility(View.VISIBLE);
                }
                break;

        }
    }

    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }
}
