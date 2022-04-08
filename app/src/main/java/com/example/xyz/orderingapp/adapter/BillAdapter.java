package com.example.xyz.orderingapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xyz.orderingapp.R;
import com.example.xyz.orderingapp.entity.GoodsListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xhh on 2022/4/2.
 */

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder>{
    private List<GoodsListBean.DataEntity.GoodscategoryEntity.GoodsitemEntity> dataList;
    private Context mContext;

    private List<Integer> goodsNum;//-----单个商品的购买个数
    private List<Integer> goodsId;
    private int buyNum;
    private int totalPrice;
    private int[] mSectionIndices;
    private int[]  mGoodsCategoryBuyNums;
    private Activity mActivity;
    private TextView shopCart;
    private ImageView buyImg;
    private List<GoodsListBean.DataEntity.GoodscategoryEntity> goodscategoryEntities;
    private String[] mSectionLetters;
    private List<GoodsListBean.DataEntity.GoodscategoryEntity.GoodsitemEntity> selectGoods=new ArrayList<>(); // 保存用户购买的商品类个例

    public BillAdapter(Context context, List<GoodsListBean.DataEntity.GoodscategoryEntity.GoodsitemEntity> items,List<Integer> goodsNum,List<Integer> goodsId
           ) {
        this.mContext = context;
        this.dataList = items;
        this.goodsNum = goodsNum;
        this.goodsId = goodsId;
        setHasStableIds(true);
    }

    @Override
    public BillAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bill, viewGroup, false);
        return new BillAdapter.ViewHolder(itemView);
    }


    public void setmActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }


    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public long getItemId(int position) {
        return dataList.get(position).hashCode();
    }

    @Override
    public void onBindViewHolder(final BillAdapter.ViewHolder holder, final int position) {

        final int id[] = {R.drawable.nnnzb,
                R.drawable.ksj,
                R.drawable.krbf,
                R.drawable.zjklt,
                R.drawable.lbhg,
                R.drawable.xhlcjd,
                R.drawable.scxlh,
                R.drawable.dsx,
                R.drawable.xhb,
                R.drawable.zj,
                R.drawable.fqym,
                R.drawable.st,
                R.drawable.xb,
                R.drawable.adn,
                R.drawable.cole,
                R.drawable.md,
                R.drawable.yb,
                R.drawable.hpj,
                R.drawable.psdt,
                R.drawable.llqc,
                R.drawable.bd,
                R.drawable.mymms
        };
        //获取品名
        holder.GoodsName.setText(dataList.get(position).getName());

        //获取单价
        holder.GoodsPrice.setText("¥"+dataList.get(position).getPrice());

        //获取单个购买量
        holder.Quantity.setText("×"+goodsNum.get(position));
        //获取规格
        if(dataList.get(position).getcIndex()!=888)
        {
            holder.Specification.setText(dataList.get(position).getSpecifications()[dataList.get(position).getcIndex()]);
        }
        else
        {
            holder.Specification.setVisibility(View.GONE);
        }
        Glide
                .with(mContext)
                .load(id[goodsId.get(position)])
                .centerCrop()
                .placeholder(R.mipmap.icon_logo_image_default)
                .crossFade()
                .into(holder.GoodsImage);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        public final ImageView GoodsImage;
        public final TextView GoodsName;
        public final TextView Quantity;
        public final LinearLayout GoodsInfo;
        public final TextView GoodsPrice;
        public final TextView Specification;
        public final View root;



        public ViewHolder(View root) {
            super(root);
            GoodsImage = (ImageView) root.findViewById(R.id.GoodsImage);
            GoodsName = (TextView) root.findViewById(R.id.GoodsName);
            Quantity = (TextView) root.findViewById(R.id.Quantity);
            GoodsInfo = (LinearLayout) root.findViewById(R.id.GoodsInfo);
            GoodsPrice = (TextView) root.findViewById(R.id.GoodsPrice);
            Specification = (TextView) root.findViewById(R.id.Specification);
            this.root = root;
        }
    }
}