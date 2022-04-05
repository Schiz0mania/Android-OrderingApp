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
import com.example.xyz.orderingapp.event.GoodsListEvent;

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
    private FloatingActionButton addComment ;
    private RecyclerView commentListView;
    private CommentAdapter commentAdapter;
    private List<Evaluation> commentList;
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
        addComment=view.findViewById(R.id.fabAdd);
    }
    public void initData(){
        // 初始化数据
        commentList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {

            Evaluation bean = new Evaluation();

            bean.setName("unknowuser" + i);
            bean.setComments("好吃欸！！！！");
            commentList.add(bean);
        }
        // 设置adapter
        commentAdapter = new CommentAdapter(getContext(),commentList);
        mLinearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        commentListView.setLayoutManager(mLinearLayoutManager);
        commentListView.setAdapter(commentAdapter);

    }

public  void showDialog(final Context context){
    LayoutInflater factory = LayoutInflater.from(context);
    final View textEntryView = factory.inflate(R.layout.comment_commit, null);
    final EditText editTextName = (EditText) textEntryView.findViewById(R.id.editTextName);
    final EditText editTextComment = (EditText)textEntryView.findViewById(R.id.editTextComment);
    AlertDialog.Builder ad1 = new AlertDialog.Builder(context);
    ad1.setTitle("增加评价");
    ad1.setIcon(android.R.drawable.ic_dialog_info);
    ad1.setView(textEntryView);
    ad1.setPositiveButton("是", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int i) {

            Toast.makeText(context,"woshishabi",Toast.LENGTH_SHORT);

        }
    });
    ad1.setNegativeButton("否", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int i) {

        }
    });
    ad1.show();// 显示对话框
}







    
}
