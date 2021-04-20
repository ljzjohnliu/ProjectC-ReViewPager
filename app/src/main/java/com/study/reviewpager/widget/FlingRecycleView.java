package com.study.reviewpager.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class FlingRecycleView extends RecyclerView {
    private boolean mIsFlingAble = true;

    public void setFlingAble(boolean flingAble) {
        mIsFlingAble = flingAble;
    }

    public FlingRecycleView(Context context) {
        super(context);
    }

    public FlingRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FlingRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        return mIsFlingAble ? super.fling(velocityX, velocityY) : false;
    }
}
