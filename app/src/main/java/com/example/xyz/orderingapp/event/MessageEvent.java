package com.example.xyz.orderingapp.event;

import com.example.xyz.orderingapp.entity.GoodsListBean;

import java.util.List;

/**
 * Created by xyz on 2022/3/27.
 */


/*
* PersonAdapter完成购物车修改后发给MainActivity修改对应购物车布局
*
* */
public class MessageEvent {
    public int  totalnum;
    public int  totalprice;
    public int[] goodsNum;
    public int index;
    public int eventtype;

    public List<GoodsListBean.DataEntity.GoodscategoryEntity.GoodsitemEntity> goods;

    public MessageEvent(int totalNum, int totalprice,List<GoodsListBean.DataEntity.GoodscategoryEntity.GoodsitemEntity> goods,int index,int eventtype,int[] goodsNum) {
        //index - 修改的商品下标
        // eventtype :1 为增加+,-1为减少
        this.goodsNum=goodsNum;
        this.index=index;
        this.eventtype=eventtype;
        this.totalnum = totalNum;
        this.totalprice = totalprice;
        this.goods = goods;
    }
}
