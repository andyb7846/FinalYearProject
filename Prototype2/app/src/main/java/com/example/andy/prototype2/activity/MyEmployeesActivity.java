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
import com.example.andy.prototype2.adapter.EmployeesAdapter;
import com.example.andy.prototype2.app.AppConfig;
import com.example.andy.prototype2.app.AppController;
import com.example.andy.prototype2.app.HttpsTrustManager;
import com.example.andy.prototype2.helper.SQLiteHandler;
import com.example.andy.prototype2.model.Employee;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyEmployeesActivity extends RootActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final String TAG = MyEmployeesActivity.class.getSimpleName();

    private SQLiteHandler db;

    private Button btnUsername;
    private Button btnCreateNewEmployee;
    private ListView listEmployees;

    private int companyId;

    private ProgressDialog pDialog;

    EmployeesAdapter employeesAdapter;
    ArrayList<Employee> employees = new ArrayList<Employee>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_employees);

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

        btnCreateNewEmployee = findViewById(R.id.btn_create_new_employee);
        listEmployees = findViewById(R.id.list_employees);

        btnCreateNewEmployee.setOnClickListener(this);

        employeesAdapter = new EmployeesAdapter(this, employees);
        listEmployees.setAdapter(employeesAdapter);
        listEmployees.setOnItemClickListener(this);

        /*
        EmployeesAdapter.add(new Employee("Employee1.CO", 120, 30, 30, 30));
        EmployeesAdapter.add(new Employee("Employee2.CO", 120, 30, 30, 30));
        EmployeesAdapter.add(new Employee("Employee3.CO", 120, 30, 30, 30));
        EmployeesAdapter.add(new Employee("Employee4.CO", 120, 30, 30, 30));
        EmployeesAdapter.add(new Employee("Employee5.CO", 120, 30, 30, 30));
        EmployeesAdapter.add(new Employee("Employee6.CO", 120, 30, 30, 30));
        EmployeesAdapter.add(new Employee("Employee7.CO", 120, 30, 30, 30));
        */

        requireEmployees();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(MyEmployeesActivity.this, ""+Employees.get(position).getEmployee_id(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), CreateEmployeeActivity.class);
        intent.putExtra("update", 1);
        intent.putExtra("company_id", companyId);
        intent.putExtra("employee_id", employees.get(position).getEmployee_id());
        intent.putExtra("forename", employees.get(position).getForename());
        intent.putExtra("surname", employees.get(position).getSurname());
        intent.putExtra("title", employees.get(position).getTitle());
        intent.putExtra("salary", employees.get(position).getSalary());
        startActivity(intent);

    }

    private void requireEmployees() {
        // Tag used to cancel the request
        String tag_string_req = "req_require_Employees";

        pDialog.setMessage("Requiring Employees ...");
        showDialog();

        HttpsTrustManager.allowAllSSL();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REQUIRE_EMPLOYEES, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Require Response: " + response.toString());
                hideDialog();

                try {
                    JSONArray jArr = new JSONArray(response);
                    for(int i = 0; i < jArr.length(); i ++){
                        JSONObject jObj = jArr.getJSONObject(i);
                        employeesAdapter.add(new Employee(jObj.getInt("employee_id"),
                                                            jObj.getString("surname"),
                                                            jObj.getString("forename"),
                                                            jObj.getString("title"),
                                                            jObj.getInt("salary"),
                                                            jObj.getInt("tax")));
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
        Intent intent = new Intent(getApplicationContext(), CreateEmployeeActivity.class);
        intent.putExtra("company_id", companyId);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        employeesAdapter.clear();
        requireEmployees();

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
