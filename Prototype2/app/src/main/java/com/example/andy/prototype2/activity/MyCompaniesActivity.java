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
import com.example.andy.prototype2.adapter.CompaniesAdapter;
import com.example.andy.prototype2.app.AppConfig;
import com.example.andy.prototype2.app.AppController;
import com.example.andy.prototype2.app.HttpsTrustManager;
import com.example.andy.prototype2.helper.SQLiteHandler;
import com.example.andy.prototype2.model.Company;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyCompaniesActivity extends RootActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final String TAG = MyCompaniesActivity.class.getSimpleName();

    private SQLiteHandler db;

    private Button btnUsername;
    private Button btnCreateNewCompany;
    private ListView listCompanies;

    private ProgressDialog pDialog;

    CompaniesAdapter companiesAdapter;
    ArrayList<Company> companies = new ArrayList<Company>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_companies);


        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        String strUsername = user.get("username");
        String uniqueId = user.get("uid");
        btnUsername = findViewById(R.id.btn_username);
        btnUsername.setText(strUsername);

        btnCreateNewCompany = findViewById(R.id.btn_create_new_company);
        listCompanies = findViewById(R.id.list_companies);

        btnCreateNewCompany.setOnClickListener(this);

        companiesAdapter = new CompaniesAdapter(this, companies);
        listCompanies.setAdapter(companiesAdapter);
        listCompanies.setOnItemClickListener(this);

        /*
        companiesAdapter.add(new Company("Company1.CO", 120, 30, 30, 30));
        companiesAdapter.add(new Company("Company2.CO", 120, 30, 30, 30));
        companiesAdapter.add(new Company("Company3.CO", 120, 30, 30, 30));
        companiesAdapter.add(new Company("Company4.CO", 120, 30, 30, 30));
        companiesAdapter.add(new Company("Company5.CO", 120, 30, 30, 30));
        companiesAdapter.add(new Company("Company6.CO", 120, 30, 30, 30));
        companiesAdapter.add(new Company("Company7.CO", 120, 30, 30, 30));
        */

        requireCompanies();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(MyCompaniesActivity.this, ""+companies.get(position).getCompany_id(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), CompanyDetailsActivity.class);
        intent.putExtra("company_id", companies.get(position).getCompany_id());
        startActivity(intent);
    }

    private void requireCompanies() {
        // Tag used to cancel the request
        String tag_string_req = "req_require_companies";

        pDialog.setMessage("Requiring companies ...");
        showDialog();

        HttpsTrustManager.allowAllSSL();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REQUIRE_COMPANIES, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Require Response: " + response.toString());
                hideDialog();

                try {
                    JSONArray jArr = new JSONArray(response);
                    for(int i = 0; i < jArr.length(); i ++){
                        JSONObject jObj = jArr.getJSONObject(i);
                        companiesAdapter.add(new Company(jObj.getInt("company_id"), jObj.getString("name"), jObj.getInt("employees"), jObj.getInt("properties"), jObj.getInt("devices"), jObj.getInt("vehicles")));
                    }
                    /*
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
                    */
                    //Log.e(TAG, ""+jObj);
                    //Log.e(TAG, response);

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
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), CreateCompanyActivity.class);
        startActivity(intent);
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
