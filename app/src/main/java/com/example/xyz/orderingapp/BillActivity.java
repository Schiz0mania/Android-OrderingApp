package com.example.xyz.orderingapp;

import java.util.Random;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xyz.orderingapp.adapter.BillAdapter;
import com.example.xyz.orderingapp.adapter.TabFragmentAdapter;
import com.example.xyz.orderingapp.event.MessageEvent;
import com.example.xyz.orderingapp.fragment.BillFragment;
import com.example.xyz.orderingapp.fragment.CommentFragment;
import com.example.xyz.orderingapp.fragment.GoodsFragment;
import com.example.xyz.orderingapp.utils.AnimationUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xyz.orderingapp.adapter.ImageAdapter;
import com.example.xyz.orderingapp.adapter.TabFragmentAdapter;
import com.example.xyz.orderingapp.event.MessageEvent;

import com.example.xyz.orderingapp.fragment.GoodsFragment;
import com.example.xyz.orderingapp.utils.AnimationUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.xyz.orderingapp.adapter.ImageAdapter;
import com.example.xyz.orderingapp.adapter.TabFragmentAdapter;
import com.example.xyz.orderingapp.event.MessageEvent;

import com.example.xyz.orderingapp.fragment.GoodsFragment;
import com.example.xyz.orderingapp.utils.AnimationUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


public class BillActivity extends BaseActivity {

    private ViewPager viewPager;
    private TextView shopCartNum;
    private TextView totalPrice;
    private TextView noShop;
    private RelativeLayout shopCartMain;
    private Button checkoutBtn;
    private TabFragmentAdapter adapter;
    private List<Fragment> mFragments=new ArrayList<>();
    //tab名的列表
    private List<String> mTitles=new ArrayList<>();
    private int discount;
    private boolean clicktime=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);//注册
        setContentView(R.layout.bill_main);
        initView();
        setViewPager();
        dicount();
    }

    private void initView() {

        shopCartNum = (TextView) findViewById(R.id.shopCartNum1);
        totalPrice = (TextView) findViewById(R.id.totalPrice1);
        noShop = (TextView) findViewById(R.id.noShop1);
        checkoutBtn = (Button) findViewById(R.id.CheckOut1);
        viewPager = (ViewPager) findViewById(R.id.vp1);
    }

    private void setViewPager() {

        BillFragment billFragment = new BillFragment();
       mFragments.add(billFragment);
        mTitles.add("账单");
        adapter=new TabFragmentAdapter(getSupportFragmentManager(),mFragments,mTitles);
        viewPager.setAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEvent(MessageEvent event) {

        Log.v("BillActivity","商品此时总价为"+event.totalprice);
        if (event != null) {
            if (event.totalnum > 0) {
                shopCartNum.setText(String.valueOf(event.totalnum));
                shopCartNum.setVisibility(View.VISIBLE);
                totalPrice.setVisibility(View.VISIBLE);
                noShop.setVisibility(View.GONE);
            } else {
                shopCartNum.setVisibility(View.GONE);
                totalPrice.setVisibility(View.GONE);
                noShop.setVisibility(View.VISIBLE);
            }
            totalPrice.setText("¥" + (event.totalprice));
            discount = event.totalprice;
        }
    }

    public void dicount(){

           checkoutBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (clicktime)
                {
                    clicktime=false;
                    Random random = new Random();
                final int b = random.nextInt(9) % (5) + 5;//5-9折
                AlertDialog.Builder builder = new AlertDialog.Builder(BillActivity.this);
                builder.setTitle("随机优惠");
                String s = "恭喜获得" + b + "折卷";
                builder.setMessage(s);

                builder.setPositiveButton("使用", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(BillActivity.this, "使用优惠成功", Toast.LENGTH_SHORT).show();
                        totalPrice.setText("¥" + (Double.valueOf(discount * b) / 10));
                        checkoutBtn.setText("已提交");
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(BillActivity.this, "未使用优惠", Toast.LENGTH_SHORT).show();
                        checkoutBtn.setText("已提交");
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
                else
                    {
                        Toast.makeText(BillActivity.this, "请勿重复提交", Toast.LENGTH_SHORT).show();
                }
        }
        }
        );

}
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
