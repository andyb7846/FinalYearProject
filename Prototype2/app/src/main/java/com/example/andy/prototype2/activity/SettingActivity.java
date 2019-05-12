package com.example.andy.prototype2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.andy.prototype2.helper.SQLiteHandler;
import com.example.andy.prototype2.helper.SessionManager;
import com.example.andy.prototype2.R;

import java.util.HashMap;

public class SettingActivity extends RootActivity {

    private Button btnMenu;
    private Button btnUsername;

    private SQLiteHandler db;
    private SessionManager session;
    private SeekBar seekBar;
    private TextView textFontSize;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        btnMenu = findViewById(R.id.btn_menu);
        btnUsername = findViewById(R.id.btn_username);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        // Ensures user is logged in when they are inside the MainActivity
        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String strUsername = user.get("username");

        // Displaying the user details on the screen
        btnUsername.setText(strUsername);

        textFontSize = findViewById(R.id.textFontSize);
        textFontSize.setTextSize(30);

        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(100);
        seekBar.setProgress(0);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBar.setProgress(progress/20*20);
                textFontSize.setTextSize(30 + progress/20);
                //System.out.println("Text font is:" + (progress)/20);
                //System.out.println("Text Font is : " + ((int)textFontSize.getTextSize() + (progress-40)/20));
                //System.out.println("Text Font is : " + textFontSize.getTextSize());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
