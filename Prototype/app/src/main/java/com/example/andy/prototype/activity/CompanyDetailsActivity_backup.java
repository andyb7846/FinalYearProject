package com.example.andy.prototype.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.andy.prototype.R;

public class CompanyDetailsActivity_backup extends RootActivity implements View.OnClickListener{

    private Button btnMyEmployees;
    private Button btnMyDevices;
    private Button btnMyVehicles;
    private Button btnMyProperties;
    private ListView listMyEmployees;
    private ListView listMyDevices;
    private ListView listMyProperties;
    private ListView listMyVehicles;

    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X", "Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_details_backup);

        btnMyEmployees = findViewById(R.id.btn_my_employees);
        btnMyEmployees.setOnClickListener(this);
        btnMyDevices = findViewById(R.id.btn_my_devices);
        btnMyDevices.setOnClickListener(this);
        btnMyVehicles = findViewById(R.id.btn_my_vehicles);
        btnMyVehicles.setOnClickListener(this);
        btnMyProperties = findViewById(R.id.btn_my_properties);
        btnMyProperties.setOnClickListener(this);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, mobileArray);
        listMyEmployees = findViewById(R.id.list_my_employees);
        listMyDevices = findViewById(R.id.list_my_devices);
        listMyVehicles = findViewById(R.id.list_my_vehicles);
        listMyProperties = findViewById(R.id.list_my_properties);

        listMyEmployees.setAdapter(adapter);
        listMyDevices.setAdapter(adapter);
        listMyVehicles.setAdapter(adapter);
        listMyProperties.setAdapter(adapter);

        listMyEmployees.setVisibility(View.GONE);
        listMyDevices.setVisibility(View.GONE);
        listMyProperties.setVisibility(View.GONE);
        listMyVehicles.setVisibility(View.GONE);

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
        /*
        if(listMyEmployees.getVisibility() == View.VISIBLE){
            listMyEmployees.setVisibility(View.GONE);
        }
        else{
            listMyEmployees.setVisibility(View.VISIBLE);
        }
        */
    }
}
