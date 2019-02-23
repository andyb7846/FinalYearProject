package com.example.andy.prototype.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.andy.prototype.R;
import com.example.andy.prototype.app.AppConfig;
import com.example.andy.prototype.app.AppController;

import java.util.HashMap;
import java.util.Map;

public class TwitterActivity extends Activity{

    private static final String TAG = TwitterActivity.class.getSimpleName();

    private Button search;
    private EditText keyword;

    private String token = "access-token-for-authed-user";
    private String customerKey = "consumer-key-for-app";

    private String httpHeader =
            String.format("OAuth oauth_consumer_key=\"%s\",", customerKey)
            + "oauth_nonce=\"generated-nonce\","
            + "oauth_signature=\"generated-signature\","
            + "oauth_signature_method=\"HMAC-SHA1\","
            + "oauth_timestamp=\"generated-timestamp\","
            + String.format("oauth_token=\"%s\",", token)
            + "oauth_version=\"1.0\"";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.twitter);

        search = (Button)findViewById(R.id.search);
        keyword = (EditText)findViewById(R.id.keyword);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestTwitters(keyword.getText().toString().trim());
            }
        });
    }

    protected void requestTwitters(String keyword){

        String tag_string_req = "req_twitters";

        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_TWITTER_API + "?q=" + keyword,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Twitter Response: " + response.toString());
            }
        },new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                if(networkResponse != null && networkResponse.data != null){
                    String jsonError = new String(networkResponse.data);
                    Log.e(TAG, jsonError);
                }
                Log.e(TAG, "Get Twitters Error: " + error.getMessage());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("authorization", httpHeader);

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
