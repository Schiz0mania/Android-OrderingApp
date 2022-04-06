package com.example.xyz.orderingapp.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.xyz.orderingapp.MainActivity;
import com.example.xyz.orderingapp.R;
import com.example.xyz.orderingapp.entity.GoodsListBean;
import com.example.xyz.orderingapp.event.GoodsListEvent;
import com.example.xyz.orderingapp.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xhh on 2022/4/2.
 */

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder>{
    private List<GoodsListBean.DataEntity.GoodscategoryEntity.GoodsitemEntity> dataList;
    private Context mContext;

    private List<Integer> goodsNum;//-----单个商品的购买个数
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

    public BillAdapter(Context context, List<GoodsListBean.DataEntity.GoodscategoryEntity.GoodsitemEntity> items,List<Integer> goodsNum
           ) {
        this.mContext = context;
        this.dataList = items;
        this.goodsNum = goodsNum;
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

        int id[] = {R.drawable.nnnzb,
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
        holder.goodsCategoryName1.setText(dataList.get(position).getName());

        //获取单价
        holder.tvGoodsPrice1.setText("¥"+dataList.get(position).getPrice());

        //获取单个购买量
        holder.quantity1.setText("×"+goodsNum.get(position));
        //获取规格
        holder.ismorestanderd1.setText(dataList.get(position).getcIndex());
        //加载图片
        Glide
                .with(mContext)
                .load(id[position])
                .centerCrop()
                .placeholder(R.mipmap.icon_logo_image_default)
                .crossFade()
                .into(holder.ivGoodsImage1);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        public final ImageView ivGoodsImage1;
        public final TextView goodsCategoryName1;
        public final TextView quantity1;
        public final LinearLayout goodsInfo1;
        public final TextView tvGoodsPrice1;
        public final TextView ismorestanderd1;
        public final View root;



        public ViewHolder(View root) {
            super(root);
            ivGoodsImage1 = (ImageView) root.findViewById(R.id.ivGoodsImage1);
            goodsCategoryName1 = (TextView) root.findViewById(R.id.goodsCategoryName1);
            quantity1 = (TextView) root.findViewById(R.id.quantity1);
            goodsInfo1 = (LinearLayout) root.findViewById(R.id.goodsInfo1);
            tvGoodsPrice1 = (TextView) root.findViewById(R.id.tvGoodsPrice1);
            ismorestanderd1 = (TextView) root.findViewById(R.id.ismorestanderd1);
            this.root = root;
        }
    }
}