package com.example.xyz.orderingapp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xhh on 2022/4/2.
 */

public class GoodsListBean implements Serializable {
    private List<DataEntity> data;

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {


        private GoodscategoryEntity goodscategory;

        public GoodscategoryEntity getGoodscategory() {
            return goodscategory;
        }

        public void setGoodscategory(GoodscategoryEntity goodscategory) {
            this.goodscategory = goodscategory;
        }

        public static class GoodscategoryEntity {

            private String name;
            private int buyNum;
            private List<GoodsitemEntity> goodsitem;

            public String getName() {
                return name;
            }

            public int getBuyNum() {
                return buyNum;
            }

            public void setBuyNum(int buyNum) {
                this.buyNum = buyNum;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<GoodsitemEntity> getGoodsitem() {
                return goodsitem;
            }

            public void setGoodsitem(List<GoodsitemEntity> goodsitem) {
                this.goodsitem = goodsitem;
            }

            public static class GoodsitemEntity {
                /**
                 * id：1
                 * name : 苹果
                 * price : 10
                 * introduce : 苹果好吃啊，很甜！
                 * goodsImgUrl : ""
                 * moreStandard : false  后续选规格参数
                 */
                private int id;   // 类别id
                private String name;
                private int price;
                private String introduce;
                private String goodsImgUrl;
                private boolean moreStandard;
                private String[] specifications;
                private int cIndex;  // 对应上面选择的规格值

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getPrice() {
                    return price;
                }

                public void setPrice(int price) {
                    this.price = price;
                }

                public String getIntroduce() {
                    return introduce;
                }

                public void setIntroduce(String introduce) {
                    this.introduce = introduce;
                }

                public String getGoodsImgUrl() {
                    return goodsImgUrl;
                }

                public void setGoodsImgUrl(String goodsImgUrl) {
                    this.goodsImgUrl = goodsImgUrl;
                }

                public boolean isMoreStandard() {
                    return moreStandard;
                }

                public void setMoreStandard(boolean moreStandard) {
                    this.moreStandard = moreStandard;
                }

                public String[] getSpecifications() {
                    return specifications;
                }

                public void setSpecifications(String[] specifications) {
                    this.specifications = specifications;
                }

                public int getcIndex() {
                    return cIndex;
                }

                public void setcIndex(int cIndex) {
                    this.cIndex = cIndex;
                }
            }
        }
    }
}
