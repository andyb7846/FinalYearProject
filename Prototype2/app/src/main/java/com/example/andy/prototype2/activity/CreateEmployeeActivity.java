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

public class CreateEmployeeActivity extends RootActivity{

    private static final String TAG = CreateEmployeeActivity.class.getSimpleName();

    private SQLiteHandler db;
    private SessionManager session;

    private EditText textForname, textSurname, textTitle, textTaxId, textGovTaxCode, textSalary;
    private Button btnCreate;

    private ProgressDialog pDialog;

    private int companyId;
    private int update = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_employee);

        companyId = getIntent().getIntExtra("company_id", 0);
        update = getIntent().getIntExtra("update", 0);

        textForname = (EditText) findViewById(R.id.text_forename);
        textSurname = (EditText) findViewById(R.id.text_surname);
        textTitle = (EditText) findViewById(R.id.text_title);
        textTaxId = (EditText) findViewById(R.id.text_tax_id);
        textGovTaxCode = (EditText) findViewById(R.id.text_gov_tax_code);
        textSalary = (EditText) findViewById(R.id.text_salary);

        btnCreate = (Button) findViewById(R.id.btn_create);

        db = new SQLiteHandler(getApplicationContext());

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        if(update == 1){
            textForname.setText(getIntent().getStringExtra("forename"));
            textSurname.setText(getIntent().getStringExtra("surname"));
            textTitle.setText(getIntent().getStringExtra("title"));
            textTaxId.setText(getIntent().getStringExtra("tax_id"));
            textGovTaxCode.setText(getIntent().getStringExtra("gov_tax_code"));
            textSalary.setText("" + getIntent().getIntExtra("salary", 0));

            btnCreate.setText("UPDATE");
        }

        // Create Company button Click Event
        btnCreate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String strForname = textForname.getText().toString().trim();
                String strSurname = textSurname.getText().toString().trim();
                String strTitle = textTitle.getText().toString().trim();
                String strTaxId = textTaxId.getText().toString().trim();
                String strGovTaxId = textGovTaxCode.getText().toString().trim();
                String strSalary = textSalary.getText().toString().trim();

                // Check for empty data in the form
                if (!strForname.isEmpty() &&
                        !strSurname.isEmpty() &&
                        !strTitle.isEmpty() &&
                        !strTaxId.isEmpty() &&
                        !strGovTaxId.isEmpty() &&
                        !strSalary.isEmpty()) {
                    // create company
                    checkCreate(strForname,
                                strSurname,
                                strTitle,
                                strTaxId,
                                strGovTaxId,
                                strSalary);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the company name!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });
    }

    private void checkCreate(final String forname,
                             final String surname,
                             final String title,
                             final String taxId,
                             final String govTaxCode,
                             final String salary) {
        // Tag used to cancel the request
        String tag_string_req = "req_create_employee";

        String url;
        if(update == 1) {
            pDialog.setMessage("Updating employee ...");
            url = AppConfig.URL_UPDATE_EMPLOYEE;
        }
        else {
            pDialog.setMessage("Creating employee ...");
            url = AppConfig.URL_CREATE_EMPLOYEE;
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
                                    "Update employee successfully", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),
                                    "Create employee successfully", Toast.LENGTH_LONG).show();
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

                params.put("forename", forname);
                params.put("surname", surname);
                params.put("title", title);
                params.put("tax_id", taxId);
                params.put("gov_tax_code", govTaxCode);
                params.put("salary", salary);

                HashMap<String, String> user = db.getUserDetails();
                String uniqueId = user.get("uid");
                params.put("unique_id", uniqueId);
                System.out.println("-----------------------unique id is : " + uniqueId);
                params.put("company_id", Integer.toString(companyId));
                System.out.println("-----------------------company id is : " + companyId);

                if(update == 1){
                    params.put("employee_id", Integer.toString(getIntent().getIntExtra("employee_id", 0)));
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
