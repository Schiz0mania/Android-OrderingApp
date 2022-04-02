package com.example.xyz.orderingapp.event;

/**
 * Created by xhh on 2022/4/2.
 */

public class GoodsListEvent {
    public int[] buyNums;

    public GoodsListEvent(int[] buyNums) {
        this.buyNums =buyNums;
    }
}
