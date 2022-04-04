package com.example.xyz.orderingapp.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xhh on 2022/4/2.
 */

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder>{
    private List<GoodsListBean.DataEntity.GoodscategoryEntity.GoodsitemEntity> dataList;
    private Context mContext;

    private int[] goodsNum;//-----单个商品的购买个数

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


    public PersonAdapter(Context context, List<GoodsListBean.DataEntity.GoodscategoryEntity.GoodsitemEntity> items
            , List<GoodsListBean.DataEntity.GoodscategoryEntity> goodscategoryEntities) {
        this.mContext = context;
        this.dataList = items;
        this.goodscategoryEntities = goodscategoryEntities;
        initGoodsNum();
        mSectionIndices = getSectionIndices();
        mSectionLetters = getSectionLetters();
        mGoodsCategoryBuyNums = getBuyNums();
        setHasStableIds(true);
    }

    public void setShopCart(TextView shopCart) {
        this.shopCart = shopCart;
    }

    public void setmActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }
    /**
     * 初始化各个商品的购买数量
     */
    private void initGoodsNum() {
        int leng = dataList.size();
        goodsNum = new int[leng];
        for (int i = 0; i < leng; i++) {
            goodsNum[i] = 0;
        }

    }
    /**
     * 开始动画
     * @param view
     */
    private void startAnim(View view) {
        buyImg = new ImageView(mActivity);
        buyImg.setBackgroundResource(R.mipmap.icon_goods_add_item);// 设置buyImg的图片
        int[] loc = new int[2];
        view.getLocationInWindow(loc);
        int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
        view.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
        ((MainActivity)mActivity).setAnim(buyImg, startLocation);// 开始执行动画
    }

    /**
     * 判断商品是否有添加到购物车中
     * @param i  条目下标
     * @param vh ViewHolder
     */
    private void isSelected(int i, ViewHolder vh,boolean isVis) {
        if (i == 0) {
            vh.tvGoodsSelectNum.setVisibility(View.GONE);
            vh.ivGoodsMinus.setVisibility(View.GONE);
            vh.scaleBtn.setVisibility(View.GONE);
        } else {
            vh.tvGoodsSelectNum.setVisibility(View.VISIBLE);
            vh.tvGoodsSelectNum.setText(i + "");
            vh.ivGoodsMinus.setVisibility(View.VISIBLE);
            if(isVis == true){
                vh.scaleBtn.setVisibility(View.VISIBLE);
            }

        }
    }



    /**
     * 存放每个组里的添加购物车的数量
     * @return
     */
    public int[] getBuyNums() {
        int[] letters = new int[goodscategoryEntities.size()];
        for (int i = 0; i < goodscategoryEntities.size(); i++) {
            letters[i] = goodscategoryEntities.get(i).getBuyNum();
        }
        return letters;
    }
    /**
     * 存放每个分组的第一条的ID
     *
     * @return
     */
    private int[] getSectionIndices() {
        ArrayList<Integer> sectionIndices = new ArrayList<Integer>();
        int lastFirstPoi = -1;
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).getId() != lastFirstPoi) {
                lastFirstPoi = dataList.get(i).getId();
                sectionIndices.add(i);
            }
        }
        int[] sections = new int[sectionIndices.size()];
        for (int i = 0; i < sectionIndices.size(); i++) {
            sections[i] = sectionIndices.get(i);
        }
        return sections;
    }
    /**
     * 填充每一个分组要展现的数据
     *
     * @return
     */
    private String[] getSectionLetters() {
        String[] letters = new String[mSectionIndices.length];
        for (int i = 0; i < mSectionIndices.length; i++) {
            letters[i] = goodscategoryEntities.get(i).getName();
        }
        return letters;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_goods_list, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public long getItemId(int position) {
        return dataList.get(position).hashCode();
    }

    public void clear() {
        mSectionIndices = new int[0];
        mSectionLetters = new String[0];
        notifyDataSetChanged();
    }

    public void restore() {
        mSectionIndices = getSectionIndices();
        mSectionLetters = getSectionLetters();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

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
        //设置名
        holder.goodsCategoryName.setText(dataList.get(position).getName());
        //设置说明
        holder.tvGoodsDescription.setText(dataList.get(position).getIntroduce());
        //设置价格
        holder.tvGoodsPrice.setText("¥"+dataList.get(position).getPrice());

        Glide
                .with(mContext)
                .load(id[position])
                .centerCrop()
                .placeholder(R.mipmap.icon_logo_image_default)
                .crossFade()
                .into(holder.ivGoodsImage);

        holder.ivGoodsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(mActivity);
                ImageView image=new ImageView(mContext);
                image.setImageResource(id[position]);
                dialog.setContentView(image);
                dialog.getWindow().setBackgroundDrawableResource(R.color.white);
                dialog.show();
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();

                    }
                });
            }
        });



        //通过判别对应位置的数量是否大于0来显示隐藏数量
        isSelected(goodsNum[position], holder,dataList.get(position).isMoreStandard());

        //加号按钮点击
        holder.ivGoodsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsNum[position]++;
                selectGoods.add(dataList.get(position));
                mGoodsCategoryBuyNums[dataList.get(position).getId()]++;
                buyNum++;
                totalPrice+=dataList.get(position).getPrice();
                if (goodsNum[position]<=1) {
                    holder.ivGoodsMinus.setAnimation(getShowAnimation());
                    holder.tvGoodsSelectNum.setAnimation(getShowAnimation());
                    holder.ivGoodsMinus.setVisibility(View.VISIBLE);
                    holder.tvGoodsSelectNum.setVisibility(View.VISIBLE);
                    if(dataList.get(position).isMoreStandard() == true)
                        holder.scaleBtn.setVisibility(View.VISIBLE);
                    else
                        holder.scaleBtn.setVisibility(View.GONE);
                }
                startAnim(holder.ivGoodsAdd);

                changeShopCart(position,1);

                if(mOnGoodsNunChangeListener!=null)
                    mOnGoodsNunChangeListener.onNumChange();
                isSelected(goodsNum[position], holder,dataList.get(position).isMoreStandard());


            }
        });
        //减号点击按钮点击
        holder.ivGoodsMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goodsNum[position] > 0) {
                    goodsNum[position]--;
                    selectGoods.remove(dataList.get(position));
                    mGoodsCategoryBuyNums[dataList.get(position).getId()]--;
                    isSelected(goodsNum[position], holder,dataList.get(position).isMoreStandard());

                    buyNum--;
                    totalPrice-=dataList.get(position).getPrice();


                    if (goodsNum[position] <=0) {
                        holder.ivGoodsMinus.setAnimation(getHiddenAnimation());
                        holder.tvGoodsSelectNum.setAnimation(getHiddenAnimation());
                        holder.ivGoodsMinus.setVisibility(View.GONE);
                        holder.tvGoodsSelectNum.setVisibility(View.GONE);
                        holder.scaleBtn.setVisibility((View.GONE));
                    }
                    changeShopCart(position,-1);//----------------------------------------------------------
                    if(mOnGoodsNunChangeListener!=null)
                        mOnGoodsNunChangeListener.onNumChange();
                }
            }
        });

        // 选规格按钮事件处理
        holder.scaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("选规格");
                int i= dataList.get(position).getcIndex();
                if(i < 0) i =0;
                // 若有选择历史，则展示上一次选择的规格
                builder.setSingleChoiceItems(dataList.get(position).getSpecifications(), i, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dataList.get(position).setcIndex(i);  //  保存选择的规格值，
                        Toast.makeText(mContext,"你选择了"+dataList.get(position).getSpecifications()[i],Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                        changeShopCart(position,0);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();


            }}

        );
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    /**
     * 显示减号的动画
     * @return
     */
    private Animation getShowAnimation(){
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0,720,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF,2f
                ,TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(0,1);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }


    /**
     * 隐藏减号的动画
     * @return
     */
    private Animation getHiddenAnimation(){
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0,720,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,4f
                ,TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(1,0);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }


    /**
     * 修改购物车状态
     */
    private void changeShopCart(int index,int type) {
        EventBus.getDefault().post(new MessageEvent(buyNum,totalPrice,dataList,index,type,goodsNum));
        EventBus.getDefault().post(new GoodsListEvent(mGoodsCategoryBuyNums));
        if(shopCart==null)return;
        if (buyNum > 0) {
            shopCart.setVisibility(View.VISIBLE);
            shopCart.setText(buyNum + "");
        } else {
            shopCart.setVisibility(View.GONE);
        }

    }
    private OnShopCartGoodsChangeListener mOnGoodsNunChangeListener = null;

    public void setOnShopCartGoodsChangeListener(OnShopCartGoodsChangeListener e){
        mOnGoodsNunChangeListener = e;
    }

    public interface OnShopCartGoodsChangeListener {
        public void onNumChange();
    }




    public static class ViewHolder extends RecyclerView.ViewHolder{

        public final ImageView ivGoodsImage;
        public final TextView goodsCategoryName;
        public final TextView tvGoodsDescription;
        public final LinearLayout goodsInfo;
        public final TextView tvGoodsPrice;
        public final TextView tvGoodsIntegral;
        public final ImageView ivGoodsMinus;
        public final TextView tvGoodsSelectNum;
        public final ImageView ivGoodsAdd;
        public final View root;
        public final Button scaleBtn;


        public ViewHolder(View root) {
            super(root);
            ivGoodsImage = (ImageView) root.findViewById(R.id.ivGoodsImage);
            goodsCategoryName = (TextView) root.findViewById(R.id.goodsCategoryName);
            tvGoodsDescription = (TextView) root.findViewById(R.id.tvGoodsDescription);
            goodsInfo = (LinearLayout) root.findViewById(R.id.goodsInfo);
            tvGoodsPrice = (TextView) root.findViewById(R.id.tvGoodsPrice);
            tvGoodsIntegral = (TextView) root.findViewById(R.id.tvGoodsIntegral);
            ivGoodsMinus = (ImageView) root.findViewById(R.id.ivGoodsMinus);
            tvGoodsSelectNum = (TextView) root.findViewById(R.id.tvGoodsSelectNum);
            ivGoodsAdd = (ImageView) root.findViewById(R.id.ivGoodsAdd);
            scaleBtn= (Button)root.findViewById(R.id.selectBtn);

            this.root = root;
        }
    }

}
