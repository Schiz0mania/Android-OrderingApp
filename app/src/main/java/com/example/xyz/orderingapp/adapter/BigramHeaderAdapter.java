package com.example.xyz.orderingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eowise.recyclerview.stickyheaders.StickyHeadersAdapter;
import com.example.xyz.orderingapp.R;
import com.example.xyz.orderingapp.entity.GoodsListBean;

import java.util.List;

/**
 * Created by xhh on 2022/4/2.
 */



public class BigramHeaderAdapter implements StickyHeadersAdapter<BigramHeaderAdapter.ViewHolder> {
    private Context mContext;
    private List<GoodsListBean.DataEntity.GoodscategoryEntity.GoodsitemEntity> dataList;
    private List<GoodsListBean.DataEntity.GoodscategoryEntity> goodscategoryEntities;
    public BigramHeaderAdapter(Context context, List<GoodsListBean.DataEntity.GoodscategoryEntity.GoodsitemEntity> items
            , List<GoodsListBean.DataEntity.GoodscategoryEntity> goodscategoryEntities) {
        this.mContext = context;
        this.dataList = items;
        this.goodscategoryEntities = goodscategoryEntities;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_goods_list, parent, false);
        return new BigramHeaderAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BigramHeaderAdapter.ViewHolder headerViewHolder, int position) {
        headerViewHolder.tvGoodsItemTitle.setText(goodscategoryEntities.get(
                dataList.get(position).getId()).getName());
    }

    @Override
    public long getHeaderId(int position) {
        return dataList.get(position).getId();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvGoodsItemTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvGoodsItemTitle = (TextView) itemView.findViewById(R.id.tvGoodsItemTitle);
        }
    }


}
