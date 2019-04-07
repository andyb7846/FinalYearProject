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

public class CreatePropertyActivity extends RootActivity{

    private static final String TAG = CreatePropertyActivity.class.getSimpleName();

    private SQLiteHandler db;
    private SessionManager session;

    private EditText textStreetName, textHouseNumber, textPostCode, textYearlyCost;
    private Button btnCreate, btnUsername;

    private ProgressDialog pDialog;

    private int companyId;
    private int update = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_property);

        companyId = getIntent().getIntExtra("company_id", 0);
        update = getIntent().getIntExtra("update", 0);

        textStreetName = (EditText) findViewById(R.id.text_street_name);
        textHouseNumber = (EditText) findViewById(R.id.text_house_number);
        textPostCode = (EditText) findViewById(R.id.text_post_code);
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
            textStreetName.setText(getIntent().getStringExtra("street_name"));
            textHouseNumber.setText(getIntent().getStringExtra("house_number"));
            textPostCode.setText(getIntent().getStringExtra("post_code"));
            textYearlyCost.setText("" + getIntent().getIntExtra("yearly_cost", 0));

            btnCreate.setText("UPDATE");
        }

        // Create Company button Click Event
        btnCreate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String strStreetName = textStreetName.getText().toString().trim();
                String strHouseNumber = textHouseNumber.getText().toString().trim();
                String strPostCode = textPostCode.getText().toString().trim();
                String strYearlyCost = textYearlyCost.getText().toString().trim();

                // Check for empty data in the form
                if (!strStreetName.isEmpty() &&
                        !strHouseNumber.isEmpty() &&
                        !strPostCode.isEmpty() &&
                        !strYearlyCost.isEmpty()) {
                    // create company
                    checkCreate(strStreetName,
                            strHouseNumber,
                            strPostCode,
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

    private void checkCreate(final String streetName,
                             final String houseNmuber,
                             final String postCode,
                             final String yearlyCost) {
        // Tag used to cancel the request
        String tag_string_req = "req_create_property";

        String url;
        if(update == 1) {
            pDialog.setMessage("Updating property ...");
            url = AppConfig.URL_UPDATE_PROPERTY;
        }
        else {
            pDialog.setMessage("Creating property ...");
            url = AppConfig.URL_CREATE_PROPERTY;
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
                                    "Update property successfully", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),
                                    "Create property successfully", Toast.LENGTH_LONG).show();
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

                params.put("street_name", streetName);
                params.put("house_number", houseNmuber);
                params.put("post_code", postCode);
                params.put("yearly_cost", yearlyCost);

                HashMap<String, String> user = db.getUserDetails();
                String uniqueId = user.get("uid");
                params.put("unique_id", uniqueId);
                System.out.println("-----------------------unique id is : " + uniqueId);
                params.put("company_id", Integer.toString(companyId));
                System.out.println("-----------------------company id is : " + companyId);

                if(update == 1){
                    params.put("property_id", Integer.toString(getIntent().getIntExtra("property_id", 0)));
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
