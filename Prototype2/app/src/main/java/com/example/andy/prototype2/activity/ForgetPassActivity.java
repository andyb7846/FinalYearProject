package com.example.andy.prototype2.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.andy.prototype2.R;
import com.example.andy.prototype2.app.AppConfig;
import com.example.andy.prototype2.app.AppController;
import com.example.andy.prototype2.app.HttpsTrustManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.andy.prototype2.app.AppController.TAG;

public class ForgetPassActivity extends Activity implements View.OnClickListener {

    private Button btnSend;
    private TextView textEmail;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);

        btnSend = (Button) findViewById(R.id.btn_send);
        textEmail = (TextView) findViewById(R.id.text_email);

        btnSend.setOnClickListener(this);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


    }

    @Override
    public void onClick(View v) {

        final String strEmail = textEmail.getText().toString().trim();
        if (!strEmail.isEmpty()) {
            // Tag used to cancel the request
            String tag_string_req = "req_email";

            pDialog.setMessage("Sending email ...");
            showDialog();

            HttpsTrustManager.allowAllSSL();
            StringRequest strReq = new StringRequest(Request.Method.POST,
                    AppConfig.URL_FORGET_PASS, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Response: " + response.toString());
                    hideDialog();

                    try {

                        JSONObject jObj = new JSONObject(response);
                        int id = jObj.getInt("id");

                        if (id == 0) {
                            // user exist
                            Toast.makeText(getApplicationContext(),
                                    "Please check your email for temporary password", Toast.LENGTH_LONG).show();

                        } else {
                            // Error in login. Get the error message
                            String errorMsg = jObj.getString("message");
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
                    Log.e(TAG, "Sending Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                    hideDialog();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", strEmail);

                    return params;
                }

            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        }
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
