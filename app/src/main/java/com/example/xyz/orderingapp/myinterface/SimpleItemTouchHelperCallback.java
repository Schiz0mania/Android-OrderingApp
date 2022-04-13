package com.example.xyz.orderingapp.myinterface;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.xyz.orderingapp.adapter.CommentAdapter;

/**
 * Created by xyz on 2022/4/6.
 */

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    CommentAdapter commentAdapter;
    public SimpleItemTouchHelperCallback(CommentAdapter commentAdapter){

    this.commentAdapter=commentAdapter;

    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = 0;
        int swipeFlags = ItemTouchHelper.LEFT;   //只允许从右向左侧滑
        return makeMovementFlags(dragFlags,swipeFlags);
    }
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //onItemDissmiss是接口方法
        commentAdapter.onItemDissmiss(viewHolder.getAdapterPosition());
    }
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {


        return true;
    }


}
