package com.example.xyz.orderingapp.event;

/**
 * Created by xyz on 2022/3/27.
 */



/*
* PersonAdapter完成购物车修改后发给MainActivity修改对应侧栏分类布局
*
* */
public class GoodsListEvent {// 实现侧栏每个类的购买个数的展示
    public int[] buyNums;

    public GoodsListEvent(int[] buyNums) {
        this.buyNums =buyNums;
    }
}
