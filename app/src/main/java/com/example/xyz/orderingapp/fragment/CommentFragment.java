package com.example.xyz.orderingapp.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eowise.recyclerview.stickyheaders.StickyHeadersItemDecoration;
import com.example.xyz.orderingapp.R;
import com.example.xyz.orderingapp.adapter.CommentAdapter;
import com.example.xyz.orderingapp.entity.Evaluation;
import com.example.xyz.orderingapp.entity.GoodsListBean;
import com.example.xyz.orderingapp.event.GoodsListEvent;
import com.example.xyz.orderingapp.utils.DataUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xhh on 2022/4/3.
 */

public class CommentFragment extends BaseFragment {
    private String keywords;
    private StickyHeadersItemDecoration top;

    private RecyclerView commentListView;
    private CommentAdapter commentAdapter;
    private Evaluation commentList;
    private LinearLayoutManager mLinearLayoutManager;


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(GoodsListEvent event) {


    }

    public static CommentFragment getInstance(String mTitle) {
        CommentFragment tabFragment = null;


        if (tabFragment == null) {
            tabFragment = new CommentFragment();
        }
        tabFragment.keywords = mTitle;
        return tabFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, null);
        initView(view);
        initData();

        return view;
    }

    public void initView(View view){

        commentListView=view.findViewById(R.id.commentRecycleView);

    }
    public void initData(){
        // 初始化数据
        Evaluation tmp = DataUtils.GsonToBean(DataUtils.getJsontoString(getContext(),"comment.json"), Evaluation.class);

        commentList = new Evaluation();
        commentList=tmp;


        // 设置adapter
        commentAdapter = new CommentAdapter(getContext(),commentList.getData());
        mLinearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        commentListView.setLayoutManager(mLinearLayoutManager);
        commentListView.setAdapter(commentAdapter);


    }









    
}
