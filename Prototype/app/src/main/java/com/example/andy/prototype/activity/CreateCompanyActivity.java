package com.example.andy.prototype.activity;

import android.app.Activity;
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
import com.example.andy.prototype.R;
import com.example.andy.prototype.app.AppConfig;
import com.example.andy.prototype.app.AppController;
import com.example.andy.prototype.helper.SQLiteHandler;
import com.example.andy.prototype.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateCompanyActivity extends RootActivity{

    private static final String TAG = CreateCompanyActivity.class.getSimpleName();

    private SQLiteHandler db;
    private SessionManager session;

    private EditText companyName;
    private Button btnCreate;

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_company);

        companyName = (EditText) findViewById(R.id.company_name);
        btnCreate = (Button) findViewById(R.id.btn_create);

        db = new SQLiteHandler(getApplicationContext());

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Create Company button Click Event
        btnCreate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String strCompanyName = companyName.getText().toString().trim();

                // Check for empty data in the form
                if (!strCompanyName.isEmpty()) {
                    // create company
                    checkCreate(strCompanyName);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the company name!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });
    }

    private void checkCreate(final String companyName) {
        // Tag used to cancel the request
        String tag_string_req = "req_create_company";

        pDialog.setMessage("Creating company ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_CREATE_COMPANY, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Create Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    int id = jObj.getInt("id");

                    // Check for error node in json
                    if (id == 0) {

                        Toast.makeText(getApplicationContext(),
                                "Create company successfully", Toast.LENGTH_LONG).show();
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
                params.put("company_name", companyName);

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
