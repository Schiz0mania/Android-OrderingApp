package com.example.xyz.orderingapp.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xhh on 2022/4/3.
 */

public class ImageAdapter extends PagerAdapter{
    private List<ImageView> images;
    private Context context;

    public ImageAdapter(Context context, ArrayList<ImageView> images){
        this.context=context;
        this.images=images;
    }


    /**
     * 获取当前窗体界面数
     * @return
     */
    @Override
    public int getCount() {
        return images.size();
    }

    /**
     * 判断是否由对象生成界面 /是否是同一张图片  判断出去的view是否等于进来的view 如果为true直接复用
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    /**
     * 从ViewGroup中移出当前View
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView(images.get(position));
    }

    /**
     * 返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position){
        View view=images.get(position);
        final int pos=position;
        view.setOnClickListener(new View.OnClickListener() {//在instantiateItem方法里添加点击监听事件
            @Override
            public void onClick(View v) {
                Toast.makeText(context, String.valueOf(pos), Toast.LENGTH_SHORT).show();
            }
        });
        ViewPager viewPager= (ViewPager) container;
        viewPager.addView(view);
        return images.get(position);
    }

}
