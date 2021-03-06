package com.example.andy.prototype2.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.example.andy.prototype2.MainActivity;
import com.example.andy.prototype2.R;

//Root Activity for initializing some functions
public class RootActivity extends Activity{

    public void setMenuBar(View view){
        switch(view.getId()){
            case R.id.btn_username:
                Intent intent = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_menu:
                PopupMenu popup = new PopupMenu(getApplicationContext(), view);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent = null;
                        String title = (String)item.getTitle();
                        switch (title){
                            case "Accounts":
                                intent = new Intent(getApplicationContext(), MyCompaniesActivity.class);
                                break;
                            case "Statistics":
                                intent = new Intent(getApplicationContext(), StatisticsMainActivity.class);
                                break;
                            case "Settings":
                                intent = new Intent(getApplicationContext(), MyCompaniesActivity.class);
                                break;
                            default:
                        }

                        startActivity(intent);
                        return true;
                    }
                });

                popup.show();
                break;

            default:
                throw new RuntimeException("Unknown Button ID");
        }
    }

}
