package com.example.andy.prototype2.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.andy.prototype2.R;
import com.example.andy.prototype2.adapter.VehiclesAdapter;
import com.example.andy.prototype2.app.AppConfig;
import com.example.andy.prototype2.app.AppController;
import com.example.andy.prototype2.app.HttpsTrustManager;
import com.example.andy.prototype2.helper.SQLiteHandler;
import com.example.andy.prototype2.model.Vehicle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyVehiclesActivity extends RootActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final String TAG = MyVehiclesActivity.class.getSimpleName();

    private SQLiteHandler db;

    private Button btnUsername;
    private Button btnCreateNewVehicle;
    private ListView listVehicles;

    private int companyId;

    private ProgressDialog pDialog;

    VehiclesAdapter vehiclesAdapter;
    ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_vehicles);

        companyId = getIntent().getIntExtra("company_id", 0);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        String strUsername = user.get("username");
        String uniqueId = user.get("uid");
        btnUsername = findViewById(R.id.btn_username);
        btnUsername.setText(strUsername);

        btnCreateNewVehicle = findViewById(R.id.btn_create_new_vehicle);
        listVehicles = findViewById(R.id.list_vehicles);

        btnCreateNewVehicle.setOnClickListener(this);

        vehiclesAdapter = new VehiclesAdapter(this, vehicles);
        listVehicles.setAdapter(vehiclesAdapter);
        listVehicles.setOnItemClickListener(this);


        requireVehicles();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(MyVehiclesActivity.this, ""+Vehicles.get(position).getVehicle_id(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), CreateVehicleActivity.class);
        intent.putExtra("update", 1);
        intent.putExtra("company_id", companyId);
        intent.putExtra("vehicle_id", vehicles.get(position).getVehicle_id());
        intent.putExtra("manufacturer", vehicles.get(position).getManufacturer());
        intent.putExtra("model", vehicles.get(position).getModel());
        intent.putExtra("registration", vehicles.get(position).getRegistration());
        intent.putExtra("yearly_cost", vehicles.get(position).getYearly_cost());
        startActivity(intent);

    }

    private void requireVehicles() {
        // Tag used to cancel the request
        String tag_string_req = "req_require_Vehicles";

        pDialog.setMessage("Requiring Vehicles ...");
        showDialog();

        HttpsTrustManager.allowAllSSL();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REQUIRE_VEHICLES, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Require Response: " + response.toString());
                hideDialog();

                try {
                    JSONArray jArr = new JSONArray(response);
                    for(int i = 0; i < jArr.length(); i ++){
                        JSONObject jObj = jArr.getJSONObject(i);
                        vehiclesAdapter.add(new Vehicle(jObj.getInt("vehicle_id"),
                                jObj.getString("manufacturer"),
                                jObj.getString("model"),
                                jObj.getString("registration"),
                                jObj.getInt("yearly_cost")));
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

                HashMap<String, String> user = db.getUserDetails();
                String uniqueId = user.get("uid");
                params.put("unique_id", uniqueId);
                params.put("company_id", Integer.toString(companyId));

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), CreateVehicleActivity.class);
        intent.putExtra("company_id", companyId);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        vehiclesAdapter.clear();
        requireVehicles();

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
