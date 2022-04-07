package com.example.xyz.orderingapp;

import java.util.Random;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.xyz.orderingapp.adapter.BillAdapter;
import com.example.xyz.orderingapp.entity.GoodsListBean;
import com.example.xyz.orderingapp.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import org.greenrobot.eventbus.ThreadMode;

import android.widget.Toast;


public class BillActivity extends BaseActivity {

    private BillAdapter billAdapter;
    //商品
    private List<GoodsListBean.DataEntity.GoodscategoryEntity.GoodsitemEntity> commoditylist = new ArrayList<>();
    private TextView shopCartNum;
    private TextView totalPrice;
    private TextView noShop;
    private Button checkoutBtn;
    private int discount;
    private boolean clicktime=true;
    private RecyclerView recyclerView;
    private MessageEvent event;
    private LinearLayoutManager mLinearLayoutManager;
    private List<Integer> getGoosNum=new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billmain);
        initView();
        EventBus.getDefault().register(this);//注册
       initData();
        Dicount();

    }

    private void initView() {

        shopCartNum = (TextView) findViewById(R.id.shopCartNum1);
        totalPrice = (TextView) findViewById(R.id.totalPrice1);
        noShop = (TextView) findViewById(R.id.noShop1);
        checkoutBtn = (Button) findViewById(R.id.CheckOut1);
        recyclerView = (RecyclerView) findViewById(R.id.bill_recycleView);
    }

    private void initData() {

        mLinearLayoutManager =new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        billAdapter = new BillAdapter(this,commoditylist,getGoosNum);
        billAdapter.setmActivity(this);
        recyclerView.setAdapter(billAdapter);
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void MessageEvent(MessageEvent event) {

        Log.v("BillActivity","商品总价为"+event.totalprice);
        if (event != null) {
            if (event.totalnum > 0) {
                shopCartNum.setText(String.valueOf(event.totalnum));
                shopCartNum.setVisibility(View.VISIBLE);
                totalPrice.setVisibility(View.VISIBLE);
                noShop.setVisibility(View.GONE);
            } else {
                shopCartNum.setVisibility(View.GONE);
                totalPrice.setVisibility(View.GONE);
                noShop.setVisibility(View.VISIBLE);
            }
            totalPrice.setText("¥" + (event.totalprice));
            discount = event.totalprice;
            this.event = event;
            for(int i=0;i<event.goodsNum.length;i++)    //log
            {
                Log.v("BillActivity",event.goods.get(i).getName()+" 商品数量 "+event.goodsNum[i]);
            }
            commoditylist=event.goods;
            for(int i =commoditylist.size()-1;i>=0;i--)
            {
                if (event.goodsNum[i] == 0)         //该商品销售量=0，删
                    commoditylist.remove(i);
                else
                    getGoosNum.add(event.goodsNum[i]);//否则逆序保存销售量

            }
            Collections.reverse(getGoosNum);//倒置list
        }
    }

    public void Dicount(){

           checkoutBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (clicktime)
                {
                    clicktime=false;
                    Random random = new Random();
                final int b = random.nextInt(9) % (5) + 5;//5-9折
                AlertDialog.Builder builder = new AlertDialog.Builder(BillActivity.this);
                builder.setTitle("随机优惠");
                String s = "恭喜获得" + b + "折卷";
                builder.setMessage(s);

                builder.setPositiveButton("使用优惠", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(BillActivity.this, "使用优惠成功", Toast.LENGTH_SHORT).show();
                        totalPrice.setText("¥" + (Double.valueOf(discount * b) / 10));
                        checkoutBtn.setText("已提交");
                    }
                });
                builder.setNegativeButton("直接提交", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(BillActivity.this, "未使用优惠", Toast.LENGTH_SHORT).show();
                        checkoutBtn.setText("已提交");
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
                else
                    {
                        Toast.makeText(BillActivity.this, "请勿重复提交", Toast.LENGTH_SHORT).show();
                }
        }
        }
        );

}
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
