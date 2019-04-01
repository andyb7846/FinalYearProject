package com.example.andy.prototype2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.andy.prototype2.MainActivity;
import com.example.andy.prototype2.R;
import com.example.andy.prototype2.helper.SQLiteHandler;
import com.example.andy.prototype2.helper.SessionManager;

import java.util.HashMap;

public class MenuActivity extends RootActivity implements View.OnClickListener {

    private Button btnMenu;
    private Button btnUsername;

    private Button btnAccount, btnStatics, btnNews, btnHashtag, btnSettings;

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

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

        //setMenuBar(btnMenu, btnUsername);

        btnAccount = findViewById(R.id.account);
        btnStatics = findViewById(R.id.statistics);
        btnNews = findViewById(R.id.news);
        btnHashtag = findViewById(R.id.hashtag);
        btnSettings = findViewById(R.id.settings);

        btnAccount.setOnClickListener(this);
        btnStatics.setOnClickListener(this);
        btnNews.setOnClickListener(this);
        btnHashtag.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.account:
                intent = new Intent(getApplicationContext(), MyCompaniesActivity.class);
                break;

            case R.id.statistics:
                intent = new Intent(getApplicationContext(), MyCompaniesActivity.class);
                break;

            case R.id.news:
                intent = new Intent(getApplicationContext(), MyCompaniesActivity.class);
                break;

            case R.id.hashtag:
                intent = new Intent(getApplicationContext(), MyCompaniesActivity.class);
                break;

            case R.id.settings:
                intent = new Intent(getApplicationContext(), MyCompaniesActivity.class);
                break;
            default:
                return;
        }
        startActivity(intent);
    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
