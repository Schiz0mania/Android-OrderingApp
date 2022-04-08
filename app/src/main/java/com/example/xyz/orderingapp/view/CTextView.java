package com.example.xyz.orderingapp.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import static android.graphics.ColorSpace.Model.RGB;

public class CTextView extends ViewGroup {
    private Context context;

    public CTextView(Context context) {
        super(context);
        this.context = context;
    }

    public CTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public void setText(String text, int duration) {
        int time = 0;
        if(text != null && !text.isEmpty()) {
            char[] characters = text.toCharArray();
            for(char c : characters) {
                final TextView t = new TextView(context);
                //遍历传入的字符串的每个字符，设置动画
                t.setText(String.valueOf(c));
                t.setTextSize(40);
                t.setTextColor(Color.BLUE);
                Handler h = new Handler();
                //每隔duration播放下一个TextView的动画
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addView(t);
                    }
                }, time);

                time += duration;

            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = measureWidth(widthMeasureSpec);
        int measureHeight = measureHeight(heightMeasureSpec);
        // 计算自定义的ViewGroup中所有子控件的大小
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        // 设置自定义的控件MyViewGroup的大小
        setMeasuredDimension(measureWidth, measureHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        // 遍历所有子视图
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);

            // 获取在onMeasure中计算的视图尺寸
            int measureHeight = childView.getMeasuredHeight();
            int measuredWidth = childView.getMeasuredWidth();

            //将他们横向排列
            childView.layout(childLeft, 0, childLeft + measuredWidth, measureHeight);

            childLeft += measuredWidth;
        }
    }

    private int measureWidth(int pWidthMeasureSpec) {
        int result = 0;
        int widthMode = MeasureSpec.getMode(pWidthMeasureSpec);// 得到模式
        int widthSize = MeasureSpec.getSize(pWidthMeasureSpec);// 得到尺寸

        switch (widthMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = widthSize;
                break;
        }
        return result;
    }

    private int measureHeight(int pHeightMeasureSpec) {
        int result = 0;

        int heightMode = MeasureSpec.getMode(pHeightMeasureSpec);
        int heightSize = MeasureSpec.getSize(pHeightMeasureSpec);

        switch (heightMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = heightSize;
                break;
        }
        return result;
    }
}