package com.example.xyz.orderingapp;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by xyz on 2022/3/19.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();

    }

    protected void setStatusBar() {

    }

}
