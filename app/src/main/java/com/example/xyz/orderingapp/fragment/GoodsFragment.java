package com.example.xyz.orderingapp.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eowise.recyclerview.stickyheaders.OnHeaderClickListener;
import com.eowise.recyclerview.stickyheaders.StickyHeadersBuilder;
import com.eowise.recyclerview.stickyheaders.StickyHeadersItemDecoration;
import com.example.xyz.orderingapp.R;
import com.example.xyz.orderingapp.adapter.BigramHeaderAdapter;
import com.example.xyz.orderingapp.adapter.PersonAdapter;
import com.example.xyz.orderingapp.adapter.RecycleGoodsCategoryListAdapter;
import com.example.xyz.orderingapp.entity.GoodsListBean;
import com.example.xyz.orderingapp.event.GoodsListEvent;
import com.example.xyz.orderingapp.utils.DataUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xhh on 2022/4/2.
 */

public class GoodsFragment extends BaseFragment implements PersonAdapter.OnShopCartGoodsChangeListener, OnHeaderClickListener {


    private RecyclerView mGoodsCateGoryList;
    private RecycleGoodsCategoryListAdapter mGoodsCategoryListAdapter;
    //商品类别列表
    private List<GoodsListBean.DataEntity.GoodscategoryEntity> goodscategoryEntities=new ArrayList<>();
    //商品列表
    private List<GoodsListBean.DataEntity.GoodscategoryEntity.GoodsitemEntity> goodsitemEntities=new ArrayList<>();

    //存储含有标题的第一个含有商品类别名称的条目的下标 - 即每个类别的第一个商品的下标
    private List<Integer> titlePois = new ArrayList<>();
    //上一个标题的小标
    private int lastTitlePoi;
    private RecyclerView recyclerView;
    private PersonAdapter personAdapter;
    private StickyHeadersItemDecoration top;
    private BigramHeaderAdapter headerAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_goods, container, false);
        initView(view);
        initData();
        return view;
    }




    private void initData() {

        GoodsListBean dataList = DataUtils.GsonToBean(DataUtils.getJsontoString(getContext(),"goods.json"), GoodsListBean.class);

        int i = 0;
        int j = 0;
        boolean isFirst;
        for (GoodsListBean.DataEntity dataItem : dataList.getData()) {// i  - 针对商品不同类别
            goodscategoryEntities.add(dataItem.getGoodscategory());
            isFirst = true;
            for (GoodsListBean.DataEntity.GoodscategoryEntity.GoodsitemEntity goodsitemEntity :
                    dataItem.getGoodscategory().getGoodsitem()
                    ) {             //j   ---- 针对同一类别内的商品
                if (isFirst) {
                    titlePois.add(j);
                    isFirst = false;
                }
                j++;
                goodsitemEntity.setId(i);
                goodsitemEntities.add(goodsitemEntity);
            }
            i++;
        }

        mGoodsCategoryListAdapter = new RecycleGoodsCategoryListAdapter(goodscategoryEntities, getActivity());
        mGoodsCateGoryList.setLayoutManager(new LinearLayoutManager(getContext()));
        mGoodsCateGoryList.setAdapter(mGoodsCategoryListAdapter);
        mGoodsCategoryListAdapter.setOnItemClickListener(new RecycleGoodsCategoryListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ((LinearLayoutManager)recyclerView.getLayoutManager()).scrollToPositionWithOffset(titlePois.get(position),0);
                // 快速定位且能置顶就置顶
                mGoodsCategoryListAdapter.setCheckPosition(position);
            }
        });

        mLinearLayoutManager =new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        personAdapter = new PersonAdapter(getActivity(),goodsitemEntities,goodscategoryEntities);
        personAdapter.setmActivity(getActivity());
        headerAdapter=new BigramHeaderAdapter(getActivity(),goodsitemEntities,goodscategoryEntities);
        top = new StickyHeadersBuilder()
                .setAdapter(personAdapter)
                .setRecyclerView(recyclerView)
                .setStickyHeadersAdapter(headerAdapter)
                .setOnHeaderClickListener(this)
                .build();
        recyclerView.addItemDecoration(top);
        recyclerView.setAdapter(personAdapter);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                for (int i=0;i<titlePois.size();i++){
                    if(mLinearLayoutManager.findFirstVisibleItemPosition()>= titlePois.get(i)){
                        mGoodsCategoryListAdapter.setCheckPosition(i);
                    }
                }

            }
        });



    }


    private void initView(View view) {
        mGoodsCateGoryList = (RecyclerView)view.findViewById(R.id.goods_category_list);
        recyclerView = (RecyclerView) view.findViewById(R.id.bill_recycleView);
    }


    @Override
    public void onNumChange() {

    }

    @Override
    public void onHeaderClick(View header, long headerId) {
        TextView text = (TextView)header.findViewById(R.id.tvGoodsItemTitle);
        Toast.makeText(getActivity(), "Click on " + text.getText(), Toast.LENGTH_SHORT).show();
    }

    /**
     * 处理滑动 是两个ListView联动
     */
    private class MyOnGoodsScrollListener implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (!(lastTitlePoi == goodsitemEntities
                    .get(firstVisibleItem)
                    .getId())) {
                lastTitlePoi = goodsitemEntities
                        .get(firstVisibleItem)
                        .getId();
                mGoodsCategoryListAdapter.setCheckPosition(goodsitemEntities
                        .get(firstVisibleItem)
                        .getId());
                mGoodsCategoryListAdapter.notifyDataSetChanged();
            }
        }
    }


    /**
     * 添加 或者  删除  商品发送的消息处理
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(GoodsListEvent event) {
        if(event.buyNums.length>0){
            for (int i=0;i<event.buyNums.length;i++){
                goodscategoryEntities.get(i).setBuyNum(event.buyNums[i]);
            }
            mGoodsCategoryListAdapter.changeData(goodscategoryEntities);
        }

    }


}
