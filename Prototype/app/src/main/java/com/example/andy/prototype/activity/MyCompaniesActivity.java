package com.example.andy.prototype.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.andy.prototype.R;

public class MyCompaniesActivity extends RootActivity{
    private static final String TAG = MyCompaniesActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_companies);
    }
}
