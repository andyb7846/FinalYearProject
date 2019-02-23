package com.example.andy.prototype.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences

    /*
    Shared preferences are a value stored in the android.
    The Pref is used to record if you logged in or not.
    This is so I can tab out/close the application and have
    my progress saved.

    Logged in Pref = True
    Logged in Pref = False
     */
    SharedPreferences pref;

    Editor editor;
    Context _context;

    // Shared pref mode
    // Part of Tutorial. I think this is to do with allowing other Android Applications to use your prefererences.
    // TODO Look this up!

    int PRIVATE_MODE = 0;

    // Shared preferences file name. Defining pref attributes.
    private static final String PREF_NAME = "AndroidHiveLogin";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    // Method to initialise the pref to get the preferences from the Android data to the application.
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    // Method to assign the boolean to logged in.
    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        // commit changes
        editor.commit();

        /* This is a professional practise to
        output the information to the log file
        in order to debug when things go wrong.
         */
        Log.d(TAG, "User login session modified!");
    }

    // Method to check if the user is logged in.
    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }
}