package com.example.andy.prototype.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.example.andy.prototype.MainActivity;
import com.example.andy.prototype.R;
import com.example.andy.prototype.helper.SQLiteHandler;
import com.example.andy.prototype.helper.SessionManager;

import java.util.HashMap;

public class MenuActivity extends RootActivity{

    private Button btnMenu;
    private Button btnUsername;

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
