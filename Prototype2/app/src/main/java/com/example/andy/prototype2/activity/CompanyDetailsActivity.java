package com.example.andy.prototype2.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.andy.prototype2.R;
import com.example.andy.prototype2.app.AppConfig;
import com.example.andy.prototype2.app.AppController;
import com.example.andy.prototype2.app.HttpsTrustManager;
import com.example.andy.prototype2.helper.SQLiteHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.andy.prototype2.app.AppController.TAG;

public class CompanyDetailsActivity extends RootActivity implements View.OnClickListener{

    private SQLiteHandler db;
    private TextView textCompanyName, textGross, textYearlyIncome, textYearlyCost, textMonthlyIncome, textMonthlyCost;
    private Button btnUsername, btnEmployees, btnProperties, btnDevices, btnVehicles, btnDelete;
    private int companyId;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_details);

        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        String strUsername = user.get("username");
        String uniqueId = user.get("uid");
        btnUsername = findViewById(R.id.btn_username);
        btnUsername.setText(strUsername);

        textCompanyName = findViewById(R.id.text_company_name);
        textGross = findViewById(R.id.text_gross);
        textYearlyIncome = findViewById(R.id.text_yearly_income);
        textYearlyCost = findViewById(R.id.text_yearly_cost);
        textMonthlyIncome = findViewById(R.id.text_monthly_income);
        textMonthlyCost = findViewById(R.id.text_monthly_cost);

        btnEmployees = findViewById(R.id.btn_employees);
        btnProperties = findViewById(R.id.btn_properties);
        btnDevices = findViewById(R.id.btn_devices);
        btnVehicles = findViewById(R.id.btn_vehicles);
        btnDelete = findViewById(R.id.btn_delete);

        btnEmployees.setOnClickListener(this);
        btnProperties.setOnClickListener(this);
        btnDevices.setOnClickListener(this);
        btnVehicles.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        companyId = getIntent().getIntExtra("company_id", 0);
        textCompanyName.setText(getIntent().getStringExtra("company_name"));
        textGross.append("" + getIntent().getIntExtra("gross", 0));
        textYearlyIncome.append("" + getIntent().getIntExtra("yearly_income", 0));
        textYearlyCost.append("" + getIntent().getIntExtra("yearly_cost", 0));
        textMonthlyIncome.append("" + getIntent().getIntExtra("monthly_income", 0));
        textMonthlyCost.append("" + getIntent().getIntExtra("monthly_cost", 0));

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_delete){
            deleteCompany();
            return;
        }
        Intent intent;
        switch (v.getId()){
            case R.id.btn_employees:
                intent = new Intent(getApplicationContext(), MyEmployeesActivity.class);
                break;
            case R.id.btn_properties:
                intent = new Intent(getApplicationContext(), MyPropertiesActivity.class);
                break;
            case R.id.btn_devices:
                intent = new Intent(getApplicationContext(), MyDevicesActivity.class);
                break;
            case R.id.btn_vehicles:
                intent = new Intent(getApplicationContext(), MyVehiclesActivity.class);
                break;
            default:
                System.out.println("Wrong btn id");
                return;
        }
        intent.putExtra("company_id", companyId);
        startActivity(intent);
    }

    private void deleteCompany() {
        // Tag used to cancel the request
        String tag_string_req = "req_delete_company";

        pDialog.setMessage("Deleting company ...");
        showDialog();

        HttpsTrustManager.allowAllSSL();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DELETE_COMPANY, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Delete Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    int id = jObj.getInt("id");

                    // Check for error node in json
                    if (id == 0) {

                        Toast.makeText(getApplicationContext(),
                                "Delete company successfully", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Create Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("company_id", ""+getIntent().getIntExtra("company_id", 0));

                HashMap<String, String> user = db.getUserDetails();
                String uniqueId = user.get("uid");
                params.put("unique_id", uniqueId);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
