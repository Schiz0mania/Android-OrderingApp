package com.example.xyz.orderingapp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xyz on 2022/3/23.
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

            private String name; // 类别名
            private int buyNum; // 单个类别购买数量
            private List<GoodsitemEntity> goodsitem; // 每个类别对应的商品lsit


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

                private int id; // 后续数据适配的id

                private String name; // 菜名
                private int price; // 价格
                private String introduce; //介绍


                private boolean moreStandard; //后续选规格参数，是否能选规格
                private String[] specifications; // 规格数组，例子：["微辣","中辣","重辣"]
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
