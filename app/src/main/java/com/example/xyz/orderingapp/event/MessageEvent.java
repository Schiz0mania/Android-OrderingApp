package com.example.xyz.orderingapp.event;

import com.example.xyz.orderingapp.entity.GoodsListBean;

import java.util.List;

/**
 * Created by xhh on 2022/4/2.
 */

public class MessageEvent {
    public int  num;
    public int  price;
    public List<GoodsListBean.DataEntity.GoodscategoryEntity.GoodsitemEntity> goods;

    public MessageEvent(int totalNum, int price,List<GoodsListBean.DataEntity.GoodscategoryEntity.GoodsitemEntity> goods) {
        this.num = totalNum;
        this.price = price;
        this.goods = goods;
    }
}
