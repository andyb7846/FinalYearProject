package com.example.andy.prototype.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.andy.prototype.R;

public class AccountsActivity extends RootActivity implements View.OnClickListener{

    private static final String TAG = AccountsActivity.class.getSimpleName();

    private Button btnMyCompanies;
    private Button btnJobs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accounts);

        btnMyCompanies = (Button) findViewById(R.id.btn_myCompanies);
        btnJobs = (Button) findViewById(R.id.btn_myJobs);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_myCompanies:
                Intent intent = new Intent(getApplicationContext(),
                        MyCompaniesActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_myJobs:
                intent = new Intent(getApplicationContext(),
                        MyJobsActivity.class);
                startActivity(intent);
                break;
            default:
                throw new RuntimeException("Unknown Button ID");
        }
    }
}
