package com.example.xyz.orderingapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by xyz on 2022/3/21.
 */

public class BaseFragment extends Fragment {
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }
}
