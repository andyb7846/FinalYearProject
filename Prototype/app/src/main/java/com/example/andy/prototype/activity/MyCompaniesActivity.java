package com.example.andy.prototype.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.andy.prototype.R;
import com.example.andy.prototype.adapter.CompaniesAdapter;
import com.example.andy.prototype.helper.SQLiteHandler;
import com.example.andy.prototype.model.Company;

import java.util.ArrayList;
import java.util.HashMap;

public class MyCompaniesActivity extends RootActivity implements View.OnClickListener{
    private static final String TAG = MyCompaniesActivity.class.getSimpleName();

    private SQLiteHandler db;

    private Button btnUsername;
    private Button btnCreateNewCompany;
    private ListView listCompanies;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_companies);

        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        String strUsername = user.get("username");
        btnUsername = findViewById(R.id.btn_username);
        btnUsername.setText(strUsername);

        btnCreateNewCompany = findViewById(R.id.btn_create_new_company);
        listCompanies = findViewById(R.id.list_companies);

        btnCreateNewCompany.setOnClickListener(this);

        ArrayList<Company> companies = new ArrayList<Company>();
        CompaniesAdapter companiesAdapter = new CompaniesAdapter(this, companies);
        listCompanies.setAdapter(companiesAdapter);

        companiesAdapter.add(new Company("Company1.CO", 120, 30, 30, 30));
        companiesAdapter.add(new Company("Company2.CO", 120, 30, 30, 30));
        companiesAdapter.add(new Company("Company3.CO", 120, 30, 30, 30));
        companiesAdapter.add(new Company("Company4.CO", 120, 30, 30, 30));
        companiesAdapter.add(new Company("Company5.CO", 120, 30, 30, 30));
        companiesAdapter.add(new Company("Company6.CO", 120, 30, 30, 30));
        companiesAdapter.add(new Company("Company7.CO", 120, 30, 30, 30));
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), CreateCompanyActivity.class);
        startActivity(intent);
    }
}
