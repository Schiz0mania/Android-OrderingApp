package com.example.xyz.orderingapp;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.xyz.orderingapp.adapter.ImageAdapter;
import com.example.xyz.orderingapp.adapter.TabFragmentAdapter;
import com.example.xyz.orderingapp.entity.Evaluation;
import com.example.xyz.orderingapp.event.CommentEvent;
import com.example.xyz.orderingapp.event.MessageEvent;


import com.example.xyz.orderingapp.fragment.CommentFragment;

import com.example.xyz.orderingapp.fragment.GoodsFragment;
import com.example.xyz.orderingapp.utils.AnimationUtil;

import android.content.Intent;
import android.view.View.OnClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout; // 滑动展示
    private AppBarLayout appBarLayout;// 与上一个collapsingbar联动使用
    private TabLayout slidingTabLayout; //管理商品与评论两个frag区

    //fragment列表
    private List<Fragment> mFragments=new ArrayList<>();
    private GoodsFragment  goodsFragment; //商品
    private CommentFragment  commentFragment ;//评论

    //tab名的列表
    private List<String> mTitles=new ArrayList<>();

    private ViewPager viewPager;
    private TabFragmentAdapter adapter;

    // 下方购物车布局部件
    private RelativeLayout shopCartMain;
    private TextView shopCartNum;// 总数 显示在小红球内
    //总价
    private TextView totalPrice;
    private int totalprice =0;
    private TextView noShop;// 与总价一起使用，购物车为空时显示“购物车为空”，否则显示当前总价

    private ViewGroup anim_mask_layout;//动画层

    private Button checkoutBtn;// 结算按钮（提交订单）
    private FloatingActionButton addComment;// 添加评论按钮

    // 完成图片自动轮播与支持手动滑动的部分
    private ViewPager imgVp;
    private ImageAdapter imageAdapter;
    private int resSize;
    private int FIRST_ITEM_INDEX;
    private int LAST_ITEM_INDEX;
    private int currentPos;
    private boolean isAuto;
    private android.os.Handler handler;

    // 购物车PersonAdapter事件，EventBus处理
    private MessageEvent event;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);//注册
        setContentView(R.layout.activity_main);
        setCollsapsing();
        initView();
        initImage();
        setViewPager();
        checkoutBtn.setOnClickListener(new MyButtonListener());
    }

    private void initView() {

        //基础布局
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        slidingTabLayout = (TabLayout) findViewById(R.id.slidinglayout);
        viewPager = (ViewPager) findViewById(R.id.vp);
        checkoutBtn = (Button) findViewById(R.id.CheckOut);
        shopCartMain=(RelativeLayout)findViewById(R.id.shopCartMain);
        shopCartNum=(TextView)findViewById(R.id.shopCartNum);
        totalPrice=(TextView)findViewById(R.id.totalPrice);
        noShop=(TextView)findViewById(R.id.noShop);

        //轮播部分
        imgVp=findViewById(R.id.scrollView);
        handler = new android.os.Handler();
        task.run();

        //添加评价部分
        addComment = findViewById(R.id.fabAdd);
        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(MainActivity.this);

            }
        });


    }




    // 图片轮播

    private  void initImage(){// 初始化数据
        int[] resIds=new int[]{
                R.drawable.ksj,
                R.drawable.krbf,
                R.drawable.zjklt,
                R.drawable.xhlcjd,
                R.drawable.nnnzb};
        ArrayList<ImageView> images=new ArrayList<>();
        initImages(images,resIds);
        resSize=images.size();
        imageAdapter=new ImageAdapter(this,images);
        imgVp.setAdapter(imageAdapter);
        imgVp.setCurrentItem(0);
        FIRST_ITEM_INDEX=1;
        LAST_ITEM_INDEX=images.size()-2;
        isAuto = true;
    }
    private void initImages(ArrayList<ImageView> images,int[] resIds){
        images.add(createImage(resIds[resIds.length-1]));
        for(int i=0;i<resIds.length;i++){
            images.add(createImage(resIds[i]));
        }
        images.add(createImage(resIds[0]));
    }

    private ImageView createImage(int resId){// glide加载图片
        ImageView img=new ImageView(this);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(this)
                .load(resId)
                .dontAnimate()
                .into(img);
        return img;
    }

    private Runnable task = new Runnable() {// 处理轮播与点击延迟操作的runnable
        @Override
        public void run() {

            if (isAuto) {
                // 位置循环
                currentPos = currentPos+1 % resSize ;

                // 正常每隔3秒播放一张图片
                imgVp.setCurrentItem(currentPos,false);
                handler.postDelayed(task, 3000);
                Log.v("test","current index is"+currentPos);
            } else {
                // 如果处于拖拽状态停止自动播放，会每隔5秒检查一次是否可以正常自动播放。
                handler.postDelayed(task, 5000);
            }
        }
    };






    private void setViewPager() {

        goodsFragment=new GoodsFragment();
        commentFragment = new CommentFragment();


        mFragments.add(goodsFragment);
        mFragments.add(commentFragment);
        mTitles.add("商品");
        mTitles.add("评价");

        adapter=new TabFragmentAdapter(getSupportFragmentManager(),mFragments,mTitles);
        viewPager.setAdapter(adapter);
        imgVp.setAdapter(imageAdapter);
        slidingTabLayout.setupWithViewPager(viewPager);

        // 监听 商品页与评价页面的滑动切换（包含动画）
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {//切换的动画逻辑
                switch (position){
                    case 0:
                        addComment.setVisibility(View.GONE);
                        addComment.setClickable(false);
                        shopCartMain.startAnimation(
                                AnimationUtil.createInAnimation(MainActivity.this, shopCartMain.getMeasuredHeight()));

                        addComment.startAnimation(
                                AnimationUtil.createOutAnimation(MainActivity.this,addComment.getMeasuredHeight()*2)
                        );
                        break;
                    case 1:
                        addComment.startAnimation(
                                AnimationUtil.createInAnimation(MainActivity.this,addComment.getMeasuredHeight()*2)
                        );
                        addComment.setVisibility(View.VISIBLE);
                        addComment.setClickable(true);
                        shopCartMain.startAnimation(
                                AnimationUtil.createOutAnimation(MainActivity.this, shopCartMain.getMeasuredHeight()));
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        imgVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {//轮播的手动滑动切换逻辑
            private int position;
            private int oldpos;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                this.position=position;

            }

            @Override
            public void onPageSelected(int position) {
                // 以下实现无限滚动
                if(position>LAST_ITEM_INDEX){
                    currentPos=FIRST_ITEM_INDEX;

                }else if(position<FIRST_ITEM_INDEX){
                    currentPos=LAST_ITEM_INDEX;

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch(state){
                    case 0: //无位移操作
                        isAuto=true;
                        break;
                    case 1://手指在滑动
                        isAuto=false;
                        break;
                    case 2:// 页面在执行滑动，手指已离开
                        isAuto=true;
                        break;
                }
            }
        });



    }

    //添加评论按钮点击后的事件处理，由EventBus发出事件，CommentAdapter响应，完成刷新与数据更新
    public  void showDialog(final Context context){
        LayoutInflater factory = LayoutInflater.from(context);
        final View textEntryView = factory.inflate(R.layout.comment_commit, null);
        final EditText editTextName = (EditText) textEntryView.findViewById(R.id.editTextName);
        final EditText editTextComment = (EditText)textEntryView.findViewById(R.id.editTextComment);
        AlertDialog.Builder ad1 = new AlertDialog.Builder(context);
        ad1.setTitle("增加评价");
        ad1.setView(textEntryView);
        ad1.setPositiveButton("是", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                String cName=editTextName.getText().toString();
                String cComment=editTextComment.getText().toString();
                EventBus.getDefault().post(new CommentEvent(new Evaluation.Comment(cName,cComment)));
                Log.v("addComment",cName+"评价"+cComment);
            }
        });
        ad1.setNegativeButton("否", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {

            }
        });
        ad1.show();// 显示对话框
    }

    //collapsingToolbarLayout初始化
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





     //添加 或者  删除  商品发送的消息处理

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
            this.event=event;
             totalprice =event.totalprice;

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


    class MyButtonListener implements OnClickListener{

        public void onClick(View v) {
            if(totalprice !=0) {
                EventBus.getDefault().postSticky(event);
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, BillActivity.class);
                MainActivity.this.startActivity(intent);
                commentFragment.onDestroy();

            }
            else
            {
                Toast.makeText(MainActivity.this, "购物车为空！", Toast.LENGTH_SHORT).show();
            }
        }
    }



}
