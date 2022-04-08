package com.example.xyz.orderingapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eowise.recyclerview.stickyheaders.StickyHeadersItemDecoration;
import com.example.xyz.orderingapp.R;
import com.example.xyz.orderingapp.adapter.CommentAdapter;
import com.example.xyz.orderingapp.entity.Evaluation;
import com.example.xyz.orderingapp.entity.GoodsListBean;
import com.example.xyz.orderingapp.event.CommentEvent;
import com.example.xyz.orderingapp.event.GoodsListEvent;
import com.example.xyz.orderingapp.myinterface.SimpleItemTouchHelperCallback;
import com.example.xyz.orderingapp.utils.DataUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import com.alibaba.fastjson.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import static android.content.Context.MODE_PRIVATE;


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


        //read to jsonstr
        String tmpstr =readComment();

        commentList = new Evaluation();

        if(tmpstr == null){
            // 初始化数据
            commentList = DataUtils.GsonToBean(DataUtils.getJsontoString(getContext(),"comment.json"), Evaluation.class);

        }else {
            //jsonstr to class entity
            JSONObject jsonObject = JSONObject.parseObject(tmpstr);
            commentList = JSONObject.toJavaObject(jsonObject, Evaluation.class);

        }







        // 设置adapter
        commentAdapter = new CommentAdapter(getContext(),commentList.getData());
        mLinearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        commentListView.setLayoutManager(mLinearLayoutManager);
        commentListView.setAdapter(commentAdapter);

        // 设置滑动删除助手
        SimpleItemTouchHelperCallback callback = new SimpleItemTouchHelperCallback(commentAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(commentListView);


    }

    @Subscribe
    public void addCommentEvent(CommentEvent e){

        commentList.addData(e.getNewComment());
        commentAdapter.notifyDataSetChanged();


    }

    @Override
    public void onDestroy() {
        //设置下次不可删除
        for(Evaluation.Comment i : commentList.getData()){
            i.setNewPosted(false);
        }
        //commentList首先转json字串
        String str = JSONObject.toJSONString(commentList);
        // 写数据
        writeComment(str);



        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    //
    public String readComment(){
        InputStream inputStream = null;
        Reader reader = null;
        BufferedReader bufferedReader = null;
        try {

            File file=new File(getContext().getFilesDir().getPath(), "comments.json");
            inputStream = new FileInputStream(file);
            reader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(reader);
            StringBuilder result = new StringBuilder();
            String temp;
            while ((temp = bufferedReader.readLine()) != null) {
                result.append(temp);

            }
            return result.toString();


        } catch (Exception e) {
            e.printStackTrace();
            Log.v("comment","failed");
            return null;
        }


    }
    public void writeComment(String text){
        File file1=new File(getContext().getFilesDir().getPath(),"comments.json");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file1);
            fileOutputStream.write(text.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }







    
}
