package com.example.xyz.orderingapp;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xyz.orderingapp.adapter.TabFragmentAdapter;
import com.example.xyz.orderingapp.event.MessageEvent;
import com.example.xyz.orderingapp.fragment.GoodsFragment;
import com.example.xyz.orderingapp.utils.AnimationUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout; // 滑动展示
    private AppBarLayout appBarLayout;
    private TabLayout slidingTabLayout;
    //fragment列表
    private List<Fragment> mFragments=new ArrayList<>();
    //tab名的列表
    private List<String> mTitles=new ArrayList<>();

    private ViewPager viewPager;
    private TabFragmentAdapter adapter;
    private TextView shopCartNum;
    private TextView totalPrice;
    private TextView noShop;
    private RelativeLayout shopCartMain;
    private ViewGroup anim_mask_layout;//动画层
    private Button checkoutBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);//注册

        setContentView(R.layout.activity_main);
        setCollsapsing();
        initView();
        setViewPager();

    }

    private void initView() {
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        slidingTabLayout = (TabLayout) findViewById(R.id.slidinglayout);
        viewPager = (ViewPager) findViewById(R.id.vp);
        checkoutBtn = (Button) findViewById(R.id.CheckOut);
        shopCartMain=(RelativeLayout)findViewById(R.id.shopCartMain);
        shopCartNum=(TextView)findViewById(R.id.shopCartNum);
        totalPrice=(TextView)findViewById(R.id.totalPrice);
        noShop=(TextView)findViewById(R.id.noShop);


    }

    private void setViewPager() {

        GoodsFragment goodsFragment=new GoodsFragment();

        mFragments.add(goodsFragment);


        mTitles.add("商品");


        adapter=new TabFragmentAdapter(getSupportFragmentManager(),mFragments,mTitles);
        viewPager.setAdapter(adapter);
        slidingTabLayout.setupWithViewPager(viewPager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        shopCartMain.startAnimation(
                                AnimationUtil.createInAnimation(MainActivity.this, shopCartMain.getMeasuredHeight()));
                        break;
                    case 1:
                        shopCartMain.startAnimation(
                                AnimationUtil.createOutAnimation(MainActivity.this, shopCartMain.getMeasuredHeight()));
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    private void setCollsapsing() {
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setContentScrim(getResources().getDrawable(R.mipmap.background));
    }


    @Override protected void setStatusBar() {
        super.setStatusBar();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }






    /**
     * 添加 或者  删除  商品发送的消息处理
     * @param event
     */
    @Subscribe()
    public void onMessageEvent(MessageEvent event) {

        if(event!=null){

            if(event.eventtype == 1){
                Log.v("MainActivity","商品"+event.goods.get(event.index).getName() + "增加1"+",此时总量为"+event.goodsNum[event.index]);
            }else if(event.eventtype == -1){
                Log.v("MainActivity","商品"+event.goods.get(event.index).getName() + "减少1"+",此时总量为"+event.goodsNum[event.index]);
            }else{
                Log.v("MainActivity","商品"+event.goods.get(event.index).getName() + "选取规格"+event.goods.get(event.index).getSpecifications()[event.goods.get(event.index).getcIndex()]);
            }

            if(event.totalnum>0){
                shopCartNum.setText(String.valueOf(event.totalnum));
                shopCartNum.setVisibility(View.VISIBLE);
                totalPrice.setVisibility(View.VISIBLE);
                noShop.setVisibility(View.GONE);
            }else{
                shopCartNum.setVisibility(View.GONE);
                totalPrice.setVisibility(View.GONE);
                noShop.setVisibility(View.VISIBLE);
            }
            totalPrice.setText("¥"+String.valueOf(event.totalprice));


        }

    }


    /**
     * 设置动画（点击添加商品）
     * @param v
     * @param startLocation
     */
    public void setAnim(final View v, int[] startLocation) {

        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(v);//把动画小球添加到动画层
        final View view = addViewToAnimLayout(anim_mask_layout, v, startLocation);
        int[] endLocation = new int[2];// 存储动画结束位置的X、Y坐标
        shopCartNum.getLocationInWindow(endLocation);
        // 计算位移
        int endX = 0 - startLocation[0] + 40;// 动画位移的X坐标
        int endY = endLocation[1] - startLocation[1];// 动画位移的y坐标

        TranslateAnimation translateAnimationX = new TranslateAnimation(0,endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationY.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(400);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }
        });

    }

    /**
     * 初始化动画图层
     * @return
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE-1);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    /**
     * 将View添加到动画图层
     * @param parent
     * @param view
     * @param location
     * @return
     */
    private View addViewToAnimLayout(final ViewGroup parent, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
