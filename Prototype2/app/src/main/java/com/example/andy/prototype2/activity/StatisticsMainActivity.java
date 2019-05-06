package com.example.andy.prototype2.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.andy.prototype2.app.AppConfig;
import com.example.andy.prototype2.app.AppController;
import com.example.andy.prototype2.app.HttpsTrustManager;
import com.example.andy.prototype2.helper.SQLiteHandler;
import java.util.HashMap;
import java.util.Map;

import com.example.andy.prototype2.R;

import org.json.JSONArray;
import org.json.JSONException;

public class StatisticsMainActivity extends RootActivity implements View.OnClickListener{

    private static final String TAG = MyCompaniesActivity.class.getSimpleName();
    private SQLiteHandler db;
    private ProgressDialog pDialog;
    private Button btnUsername;
    private Button btnEmployees, btnProperties, btnDevices, btnVehicles;
    JSONArray jArr;
    private String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_main);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        String strUsername = user.get("username");
        String uniqueId = user.get("uid");
        btnUsername = findViewById(R.id.btn_username);
        btnUsername.setText(strUsername);

        btnEmployees = findViewById(R.id.btn_employees);
        btnProperties = findViewById(R.id.btn_properties);
        btnDevices = findViewById(R.id.btn_devices);
        btnVehicles = findViewById(R.id.btn_vehicles);

        btnEmployees.setOnClickListener(this);
        btnProperties.setOnClickListener(this);
        btnDevices.setOnClickListener(this);
        btnVehicles.setOnClickListener(this);

        requireStatistics();
        //drawChart();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btn_employees:
                intent = new Intent(getApplicationContext(), EmployeeStatisticsActivity.class);
                break;
            case R.id.btn_properties:
                intent = new Intent(getApplicationContext(), PropertyStatisticsActivity.class);
                break;
            case R.id.btn_devices:
                intent = new Intent(getApplicationContext(), DeviceStatisticsActivity.class);
                break;
            case R.id.btn_vehicles:
                intent = new Intent(getApplicationContext(), VehicleStatisticsActivity.class);
                break;
            default:
                return;
        }
        intent.putExtra("response", this.response);
        startActivity(intent);
    }

    private void passResponse(String response){
        this.response = response;
    }

    private void requireStatistics() {
        // Tag used to cancel the request
        String tag_string_req = "req_require_statistics";

        pDialog.setMessage("Acquiring statistics ...");
        showDialog();

        HttpsTrustManager.allowAllSSL();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REQUIRE_STATISTICS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Require Response: " + response.toString());
                hideDialog();
                passResponse(response);

                try {
                    jArr = new JSONArray(response);

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

                HashMap<String, String> user = db.getUserDetails();
                String uniqueId = user.get("uid");
                params.put("unique_id", uniqueId);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        requireStatistics();

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
