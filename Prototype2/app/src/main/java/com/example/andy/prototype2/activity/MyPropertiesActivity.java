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
import com.example.andy.prototype2.adapter.PropertiesAdapter;
import com.example.andy.prototype2.app.AppConfig;
import com.example.andy.prototype2.app.AppController;
import com.example.andy.prototype2.app.HttpsTrustManager;
import com.example.andy.prototype2.helper.SQLiteHandler;
import com.example.andy.prototype2.model.Property;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyPropertiesActivity extends RootActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final String TAG = MyPropertiesActivity.class.getSimpleName();

    private SQLiteHandler db;

    private Button btnUsername;
    private Button btnCreateNewProperty;
    private ListView listProperties;

    private int companyId;

    private ProgressDialog pDialog;

    PropertiesAdapter propertiesAdapter;
    ArrayList<Property> properties = new ArrayList<Property>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_properties);

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

        btnCreateNewProperty = findViewById(R.id.btn_create_new_property);
        listProperties = findViewById(R.id.list_properties);

        btnCreateNewProperty.setOnClickListener(this);

        propertiesAdapter = new PropertiesAdapter(this, properties);
        listProperties.setAdapter(propertiesAdapter);
        listProperties.setOnItemClickListener(this);


        requireProperties();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(MyPropertiesActivity.this, ""+Properties.get(position).getProperty_id(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), CreatePropertyActivity.class);
        intent.putExtra("update", 1);
        intent.putExtra("company_id", companyId);
        intent.putExtra("property_id", properties.get(position).getProperty_id());
        intent.putExtra("street_name", properties.get(position).getStreet_name());
        intent.putExtra("house_number", properties.get(position).getHouse_number());
        intent.putExtra("post_code", properties.get(position).getPost_code());
        intent.putExtra("yearly_cost", properties.get(position).getYearly_cost());
        startActivity(intent);

    }

    private void requireProperties() {
        // Tag used to cancel the request
        String tag_string_req = "req_require_Properties";

        pDialog.setMessage("Requiring Properties ...");
        showDialog();

        HttpsTrustManager.allowAllSSL();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REQUIRE_PROPERTIES, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Require Response: " + response.toString());
                hideDialog();

                try {
                    JSONArray jArr = new JSONArray(response);
                    for(int i = 0; i < jArr.length(); i ++){
                        JSONObject jObj = jArr.getJSONObject(i);
                        propertiesAdapter.add(new Property(jObj.getInt("property_id"),
                                jObj.getString("street_name"),
                                jObj.getString("house_number"),
                                jObj.getString("post_code"),
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
        Intent intent = new Intent(getApplicationContext(), CreatePropertyActivity.class);
        intent.putExtra("company_id", companyId);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        propertiesAdapter.clear();
        requireProperties();

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
