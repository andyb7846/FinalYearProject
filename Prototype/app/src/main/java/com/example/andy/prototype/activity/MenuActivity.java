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

public class MenuActivity extends Activity{

    private Button btn_menu;
    private Button username;

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        btn_menu = findViewById(R.id.menu);
        username = findViewById(R.id.username);

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
        username.setText(strUsername);

        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,
                        MainActivity.class);
                startActivity(intent);
            }
        });

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //PopupMenu popup = new PopupMenu(MenuActivity.this, btn_menu);
                PopupMenu popup = new PopupMenu(MenuActivity.this, v);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent = null;
                        String title = (String)item.getTitle();
                        switch (title){
                            case "Accounts":
                                intent = new Intent(MenuActivity.this, CreateCompanyActivity.class);
                                break;
                            case "Statistics":
                                intent = new Intent(MenuActivity.this, CreateCompanyActivity.class);
                                break;
                            case "News":
                                intent = new Intent(MenuActivity.this, CreateCompanyActivity.class);
                                break;
                            case "Hashtags":
                                intent = new Intent(MenuActivity.this, CreateCompanyActivity.class);
                                break;
                            case "Settings":
                                intent = new Intent(MenuActivity.this, CreateCompanyActivity.class);
                                break;
                            default:
                        }

                        startActivity(intent);
                        return true;

                        /*
                        Toast.makeText(MenuActivity.this,
                                "You Clicked : " + item.getTitle(),
                                Toast.LENGTH_LONG
                        ).show();
                        */
                    }
                });

                popup.show();
            }
        });
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
