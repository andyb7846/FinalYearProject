package com.example.andy.prototype2.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.andy.prototype2.R;
import com.example.andy.prototype2.app.AppConfig;
import com.example.andy.prototype2.app.AppController;
import com.example.andy.prototype2.helper.SQLiteHandler;
import com.example.andy.prototype2.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateVehicleActivity extends RootActivity{

    private static final String TAG = CreateVehicleActivity.class.getSimpleName();

    private SQLiteHandler db;
    private SessionManager session;

    private EditText textManufacturer, textModel, textRegistration, textYearlyCost;
    private Button btnCreate, btnUsername;

    private ProgressDialog pDialog;

    private int companyId;
    private int update = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_vehicle);

        companyId = getIntent().getIntExtra("company_id", 0);
        update = getIntent().getIntExtra("update", 0);

        textManufacturer = (EditText) findViewById(R.id.text_manufacturer);
        textModel = (EditText) findViewById(R.id.text_model);
        textRegistration = (EditText) findViewById(R.id.text_registration);
        textYearlyCost = (EditText) findViewById(R.id.text_yearly_cost);

        btnCreate = (Button) findViewById(R.id.btn_create);

        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        String strUsername = user.get("username");
        String uniqueId = user.get("uid");
        btnUsername = findViewById(R.id.btn_username);
        btnUsername.setText(strUsername);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        if(update == 1){
            textManufacturer.setText(getIntent().getStringExtra("manufacturer"));
            textModel.setText(getIntent().getStringExtra("model"));
            textRegistration.setText(getIntent().getStringExtra("registration"));
            textYearlyCost.setText("" + getIntent().getIntExtra("yearly_cost", 0));

            btnCreate.setText("UPDATE");
        }

        // Create Company button Click Event
        btnCreate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String strManufacturer = textManufacturer.getText().toString().trim();
                String strModel = textModel.getText().toString().trim();
                String strRegistration = textRegistration.getText().toString().trim();
                String strYearlyCost = textYearlyCost.getText().toString().trim();

                // Check for empty data in the form
                if (!strManufacturer.isEmpty() &&
                        !strModel.isEmpty() &&
                        !strRegistration.isEmpty() &&
                        !strYearlyCost.isEmpty()) {
                    // create company
                    checkCreate(strManufacturer,
                            strModel,
                            strRegistration,
                            strYearlyCost);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the company name!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });
    }

    private void checkCreate(final String manufacturer,
                             final String model,
                             final String registration,
                             final String yearlyCost) {
        // Tag used to cancel the request
        String tag_string_req = "req_create_vehicle";

        String url;
        if(update == 1) {
            pDialog.setMessage("Updating vehicle ...");
            url = AppConfig.URL_UPDATE_VEHICLE;
        }
        else {
            pDialog.setMessage("Creating vehicle ...");
            url = AppConfig.URL_CREATE_VEHICLE;
        }
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Create Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    int id = jObj.getInt("id");

                    // Check for error node in json
                    if (id == 0) {

                        if(update == 1) {
                            Toast.makeText(getApplicationContext(),
                                    "Update vehicle successfully", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),
                                    "Create vehicle successfully", Toast.LENGTH_LONG).show();
                            finish();
                        }
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

                params.put("manufacturer", manufacturer);
                params.put("model", model);
                params.put("registration", registration);
                params.put("yearly_cost", yearlyCost);

                HashMap<String, String> user = db.getUserDetails();
                String uniqueId = user.get("uid");
                params.put("unique_id", uniqueId);
                System.out.println("-----------------------unique id is : " + uniqueId);
                params.put("company_id", Integer.toString(companyId));
                System.out.println("-----------------------company id is : " + companyId);

                if(update == 1){
                    params.put("vehicle_id", Integer.toString(getIntent().getIntExtra("vehicle_id", 0)));
                }

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
