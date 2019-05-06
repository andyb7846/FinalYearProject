package com.example.andy.prototype2.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.andy.prototype2.app.AppConfig;
import com.example.andy.prototype2.app.AppController;
import com.example.andy.prototype2.app.HttpsTrustManager;
import com.example.andy.prototype2.helper.SQLiteHandler;
import com.example.andy.prototype2.model.Company;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import com.example.andy.prototype2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.andy.prototype2.app.AppController.TAG;

public class VehicleStatisticsActivity extends RootActivity {

    private static final String TAG = MyCompaniesActivity.class.getSimpleName();
    private SQLiteHandler db;
    private ProgressDialog pDialog;
    private Button btnUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_vehicle);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        String strUsername = user.get("username");
        String uniqueId = user.get("uid");
        btnUsername = findViewById(R.id.btn_username);
        btnUsername.setText(strUsername);

        String response = getIntent().getStringExtra("response");

        try {
            JSONArray jArr = new JSONArray(response);
            drawVehicleChart(jArr);
        }
        catch (JSONException e) {
            // JSON error
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private void drawVehicleChart(JSONArray jArr){

        PieChart pieChart = findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(true);
        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();

        try {
            for (int i = 0; i < jArr.length(); i++) {
                JSONObject jObj = jArr.getJSONObject(i);
                yvalues.add(new PieEntry(jObj.getInt("vehicles"), jObj.getString("name")));
            }

            PieDataSet dataSet = new PieDataSet(yvalues, "Vehicles");
            PieData data = new PieData(dataSet);

            data.setValueFormatter(new PercentFormatter());
            pieChart.setData(data);
            Description description = new Description();
            description.setText("Pie Chart");
            pieChart.setDescription(description);
            pieChart.setDrawHoleEnabled(true);
            pieChart.setTransparentCircleRadius(58f);
            pieChart.setHoleRadius(58f);
            dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
            data.setValueTextSize(13f);
            data.setValueTextColor(Color.DKGRAY);

        }catch (JSONException e) {
            // JSON error
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
