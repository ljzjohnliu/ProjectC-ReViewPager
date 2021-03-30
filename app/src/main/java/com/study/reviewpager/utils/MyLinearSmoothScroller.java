package com.study.reviewpager.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

public class MyLinearSmoothScroller extends LinearSmoothScroller {

    public MyLinearSmoothScroller(Context context) {
        super(context);
    }

    @Override
    public void onTargetFound(View targetView, RecyclerView.State state, Action action) {
        // 计算距离
        int distance = distanceToCenter(
                getLayoutManager(),
                targetView,
                getOrientationHelper(getLayoutManager())
        );
        // 计算动画时间
        int time = calculateTimeForDeceleration(distance);
        Log.d("ljz", "onTargetFound: time = " + time);
        if (time > 0) {
            // 这里仅实现了水平或者垂直一种方向上的矫正，两者同时的情况暂不考虑
            Log.d("ljz", "onTargetFound: canScrollVertically = " + getLayoutManager().canScrollVertically());
            if (getLayoutManager().canScrollVertically())
                action.update(0, distance, time, mDecelerateInterpolator);
            else
                action.update(distance, 0, time, mDecelerateInterpolator);
        }
    }

    /**
     * 计算 targetView 中心点到 RecyclerView 中心点的距离
     */
    private int distanceToCenter(RecyclerView.LayoutManager layoutManager, View targetView, OrientationHelper helper) {
        int childCenter = helper.getDecoratedStart(targetView) + helper.getDecoratedMeasurement(targetView) / 2;
        int containerCenter;
        if (layoutManager.getClipToPadding()) {
            containerCenter = helper.getStartAfterPadding() + helper.getTotalSpace() / 2;
        } else {
            containerCenter = helper.getEnd() / 2;
        }
        return childCenter - containerCenter;
    }


    /**
     * 不同方向上的距离使用不同的 OrientationHelper
     */
    private OrientationHelper getOrientationHelper(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager.canScrollVertically()) {
            return OrientationHelper.createVerticalHelper(layoutManager);
        } else if (layoutManager.canScrollHorizontally()) {
            return OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return null;
    }
}
