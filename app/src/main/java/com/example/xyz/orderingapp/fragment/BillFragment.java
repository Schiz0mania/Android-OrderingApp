
    package com.example.xyz.orderingapp.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eowise.recyclerview.stickyheaders.StickyHeadersBuilder;
import com.eowise.recyclerview.stickyheaders.StickyHeadersItemDecoration;
import com.example.xyz.orderingapp.R;
import com.example.xyz.orderingapp.adapter.BigramHeaderAdapter;
import com.example.xyz.orderingapp.adapter.PersonAdapter;
import com.example.xyz.orderingapp.adapter.RecycleGoodsCategoryListAdapter;
import com.example.xyz.orderingapp.adapter.BillAdapter;
import com.example.xyz.orderingapp.entity.GoodsListBean;
import com.example.xyz.orderingapp.event.MessageEvent;
import com.example.xyz.orderingapp.fragment.BaseFragment;
import com.example.xyz.orderingapp.utils.DataUtils;

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
import com.example.xyz.orderingapp.fragment.EvaluationFragment;
import com.example.xyz.orderingapp.fragment.GoodsFragment;
import com.example.xyz.orderingapp.utils.AnimationUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


    public class BillFragment extends BaseFragment {

        private BillAdapter billAdapter;
        //商品
        private List<GoodsListBean.DataEntity.GoodscategoryEntity.GoodsitemEntity> commoditylist = new ArrayList<>();

        private TextView shopCartNum;
        private TextView totalPrice;
        private TextView noShop;
        private RecyclerView recyclerView;
        private LinearLayoutManager mLinearLayoutManager;
        private MessageEvent event;


        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.header_statusbar, container, false);
            initView(view);
            initData();

            return view;
        }


        private void initData() {

            mLinearLayoutManager =new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(mLinearLayoutManager);

            billAdapter = new BillAdapter(getActivity(),commoditylist,event);
            billAdapter.setmActivity(getActivity());
            recyclerView.setAdapter(billAdapter);
        }

        @Subscribe(sticky = true)
        public void onMessageEvent(MessageEvent event) {
            this.event = event;
            commoditylist=event.goods;
            for(int i =commoditylist.size()-1;i>=0;i--) {
                if (event.goodsNum[i] == 0)         //该商品销售量=0，删
                    commoditylist.remove(i);
            }
        }
        private void initView(View view) {

            recyclerView = (RecyclerView) view.findViewById(R.id.bill_recycleView);//绑定视图区域
        }
    }