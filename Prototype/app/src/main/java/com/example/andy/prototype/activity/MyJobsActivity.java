package com.example.andy.prototype.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.andy.prototype.R;

public class MyJobsActivity extends RootActivity {

    private static final String TAG = MyJobsActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_jobs);
    }
}
