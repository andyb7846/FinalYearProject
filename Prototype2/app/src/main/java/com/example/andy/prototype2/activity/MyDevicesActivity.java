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
import com.example.andy.prototype2.adapter.DevicesAdapter;
import com.example.andy.prototype2.app.AppConfig;
import com.example.andy.prototype2.app.AppController;
import com.example.andy.prototype2.app.HttpsTrustManager;
import com.example.andy.prototype2.helper.SQLiteHandler;
import com.example.andy.prototype2.model.Device;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyDevicesActivity extends RootActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final String TAG = MyDevicesActivity.class.getSimpleName();

    private SQLiteHandler db;

    private Button btnUsername;
    private Button btnCreateNewDevice;
    private ListView listDevices;

    private int companyId;

    private ProgressDialog pDialog;

    DevicesAdapter devicesAdapter;
    ArrayList<Device> devices = new ArrayList<Device>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_devices);

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

        btnCreateNewDevice = findViewById(R.id.btn_create_new_device);
        listDevices = findViewById(R.id.list_devices);

        btnCreateNewDevice.setOnClickListener(this);

        devicesAdapter = new DevicesAdapter(this, devices);
        listDevices.setAdapter(devicesAdapter);
        listDevices.setOnItemClickListener(this);


        requireDevices();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(MyDevicesActivity.this, ""+Devices.get(position).getDevice_id(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), CreateDeviceActivity.class);
        intent.putExtra("update", 1);
        intent.putExtra("company_id", companyId);
        intent.putExtra("device_id", devices.get(position).getDevice_id());
        intent.putExtra("brand", devices.get(position).getBrand());
        intent.putExtra("model", devices.get(position).getModel());
        intent.putExtra("yearly_cost", devices.get(position).getYearly_cost());
        startActivity(intent);

    }

    private void requireDevices() {
        // Tag used to cancel the request
        String tag_string_req = "req_require_Devices";

        pDialog.setMessage("Requiring Devices ...");
        showDialog();

        HttpsTrustManager.allowAllSSL();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REQUIRE_DEVICES, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Require Response: " + response.toString());
                hideDialog();

                try {
                    JSONArray jArr = new JSONArray(response);
                    for(int i = 0; i < jArr.length(); i ++){
                        JSONObject jObj = jArr.getJSONObject(i);
                        devicesAdapter.add(new Device(jObj.getInt("device_id"),
                                jObj.getString("brand"),
                                jObj.getString("model"),
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
        Intent intent = new Intent(getApplicationContext(), CreateDeviceActivity.class);
        intent.putExtra("company_id", companyId);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        devicesAdapter.clear();
        requireDevices();

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
