package com.example.xyz.orderingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xyz.orderingapp.R;
import com.example.xyz.orderingapp.entity.GoodsListBean;

import java.util.List;

/**
 * Created by xhh on 2022/4/2.
 */

public class RecycleGoodsCategoryListAdapter extends RecyclerView.Adapter<RecycleGoodsCategoryListAdapter.ViewHolder>{

    //当前选中的位置
    private int selectPosition;
    private List<GoodsListBean.DataEntity.GoodscategoryEntity> dataList; // category list
    public Context mContext;
    public RecycleGoodsCategoryListAdapter(List<GoodsListBean.DataEntity.GoodscategoryEntity> dataList, Context context) {
        this.dataList=dataList;
        this.mContext=context;
    }

    public  void  changeData(List<GoodsListBean.DataEntity.GoodscategoryEntity> dataList){
        this.dataList=dataList;
        notifyDataSetChanged();
    }

    @Override
    public RecycleGoodsCategoryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_category_list,
                parent, false);
        return new RecycleGoodsCategoryListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecycleGoodsCategoryListAdapter.ViewHolder holder, final int position) {
        holder.goodsCategoryName.setText(dataList.get(position).getName());
        holder.shopCartNum.setText(String.valueOf(dataList.get(position).getBuyNum()));
        if(dataList.get(position).getBuyNum()>0){
            holder.shopCartNum.setVisibility(View.VISIBLE);

        }else{
            holder.shopCartNum.setVisibility(View.GONE);
        }

        if (selectPosition != -1) {//左侧栏定位的选中与未选中的动画处理
            if (selectPosition == position) {
                holder.root.setBackgroundResource(R.drawable.goods_category_list_bg_select);
            } else {
                holder.root.setBackgroundResource(R.drawable.goods_category_list_bg_normal);
            }
        } else {
            holder.root.setBackgroundResource(R.drawable.goods_category_list_bg_normal);
        }
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnItemClickListener!=null){
                    mOnItemClickListener.onItemClick(view,position);
                }
            }
        });

    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener=mOnItemClickListener;
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }


    /**
     * 设置选中index
     * @param position
     */
    public void setCheckPosition(int position) {
        this.selectPosition = position;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView goodsCategoryName;
        public final TextView shopCartNum;
        public final View root;

        public ViewHolder(View root) {
            super(root);
            goodsCategoryName = (TextView) root.findViewById(R.id.goodsCategoryNameTV);
            shopCartNum = (TextView) root.findViewById(R.id.shopCartNumTV);
            this.root = root;
        }

    }
}
