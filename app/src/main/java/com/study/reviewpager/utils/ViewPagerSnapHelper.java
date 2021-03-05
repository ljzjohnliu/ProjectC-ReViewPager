package com.study.reviewpager.utils;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * LinearSnapHelper 可以滑动滚动
 * PagerSnapHelper 只能一张张滑动
 */
public class ViewPagerSnapHelper extends /*LinearSnapHelper*/PagerSnapHelper {
    private static final String TAG = "ViewPagerSnapHelper";
    private static final float STAY_SCALE = 0.95f;
    private RecyclerView recyclerView;

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        int position = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
        Log.d(TAG, "findTargetSnapPosition: position = " + position + ", velocityX = " + velocityX + ", velocityY = " + velocityY);
        return position;
    }

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) {
        super.attachToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
        assert recyclerView != null;
        recyclerView.addItemDecoration(new ScalableCardItemDecoration());
        recyclerView.addOnScrollListener(scrollListener);
    }

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            pageScrolled();
        }
    };

    private void pageScrolled() {
        if (recyclerView == null || recyclerView.getChildCount() == 0)
            return;

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        View snapingView = findSnapView(layoutManager);
        int snapingViewPosition = recyclerView.getChildAdapterPosition(snapingView);
        View leftSnapingView = layoutManager.findViewByPosition(snapingViewPosition - 1);
        View rightSnapingView = layoutManager.findViewByPosition(snapingViewPosition + 1);


        float leftSnapingOffset = calculateOffset(recyclerView, leftSnapingView);
        float rightSnapingOffset = calculateOffset(recyclerView, rightSnapingView);
        float currentSnapingOffset = calculateOffset(recyclerView, snapingView);

        if (snapingView != null) {
            snapingView.setScaleX(currentSnapingOffset);
            snapingView.setScaleY(currentSnapingOffset);
        }

        if (leftSnapingView != null) {
            leftSnapingView.setScaleX(leftSnapingOffset);
            leftSnapingView.setScaleY(leftSnapingOffset);
        }

        if (rightSnapingView != null) {
            rightSnapingView.setScaleX(rightSnapingOffset);
            rightSnapingView.setScaleY(rightSnapingOffset);
        }


//        if(snapingView != null && currentSnapingOffset >= 1){
//            OnPageChangeListener listener = pageChangeListenerRef != null ? pageChangeListenerRef.get(): null;
//            if(listener != null)
//                listener.onPageSelected(snapingViewPosition);
//        }

        Log.d(TAG, String.format("left: %f, right: %f, current: %f", leftSnapingOffset, rightSnapingOffset, currentSnapingOffset));
    }

    /**
     * 通过计算{@code view}中间点与{@link RecyclerView}的中间点的距离，算出{@code view}的偏移量。
     *
     * @param view              view
     * @return
     */
    private float calculateOffset(RecyclerView recyclerView, View view) {
        if (view == null)
            return -1;


        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        boolean isVertical = layoutManager.canScrollVertically();
        int viewStart = isVertical ? view.getTop() : view.getLeft();
        int viewEnd = isVertical ? view.getBottom() : view.getRight();
        int centerX = isVertical ? recyclerView.getHeight() / 2 : recyclerView.getWidth() / 2;
        int childCenter = (viewStart + viewEnd) / 2;
        int distance =   Math.abs(childCenter - centerX);

        if (distance > centerX)
            return STAY_SCALE;

        float offset = 1.f - (distance / (float) centerX);
        return (1.f - STAY_SCALE) * offset + STAY_SCALE;
    }

    public static int getPeekWidth(RecyclerView recyclerView, View itemView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        boolean isVertical = layoutManager.canScrollVertically();
        int position = recyclerView.getChildAdapterPosition(itemView);
        //TODO RecyclerView使用wrap_content时，获取的宽度可能会是0。
        int parentWidth = recyclerView.getMeasuredWidth();
        int parentHeight = recyclerView.getMeasuredHeight(); //有时会拿到0

        Log.d(TAG, "getPeekWidth: 111 position = " + position + ", parentWidth = " + parentWidth + ", parentHeight = " + parentHeight);
        parentWidth = parentWidth == 0 ? recyclerView.getWidth() : parentWidth;
        parentHeight = parentHeight == 0 ? recyclerView.getHeight() : parentHeight;
        Log.d(TAG, "getPeekWidth: 222 position = " + position + ", parentWidth = " + parentWidth + ", parentHeight = " + parentHeight);
        int parentEnd = isVertical ? parentHeight : parentWidth;
        int parentCenter = parentEnd / 2;

        int itemSize = isVertical ? itemView.getMeasuredHeight() : itemView.getMeasuredWidth();

        if (itemSize == 0) {

            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
            int widthMeasureSpec =
                    RecyclerView.LayoutManager.getChildMeasureSpec(parentWidth,
                            layoutManager.getWidthMode(),
                            recyclerView.getPaddingLeft() + recyclerView.getPaddingRight(),
                            layoutParams.width, layoutManager.canScrollHorizontally());

            int heightMeasureSpec =
                    RecyclerView.LayoutManager.getChildMeasureSpec(parentHeight,
                            layoutManager.getHeightMode(),
                            recyclerView.getPaddingTop() + recyclerView.getPaddingBottom(),
                            layoutParams.height, layoutManager.canScrollVertically());


            itemView.measure(widthMeasureSpec, heightMeasureSpec);
            itemSize = isVertical ? itemView.getMeasuredHeight() : itemView.getMeasuredWidth();
        }


        /*
            计算ItemDecoration的大小，确保插入的大小正好使view的start + itemSize / 2等于parentCenter。
         */
        int startOffset = parentCenter - itemSize / 2;
        int endOffset = parentEnd - (startOffset + itemSize);

        return position == 0 ? startOffset : endOffset;
    }

    private static class ScalableCardItemDecoration extends RecyclerView.ItemDecoration {


        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            RecyclerView.ViewHolder holder = parent.getChildViewHolder(view);
            int position = holder.getAdapterPosition() == RecyclerView.NO_POSITION ? holder.getOldPosition() : holder.getAdapterPosition();
            RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            int itemCount = layoutManager.getItemCount();

            if(position != 0 && position != itemCount - 1){
                return;
            }

            int peekWidth = getPeekWidth(parent, view);
            boolean isVertical = layoutManager.canScrollVertically();
            //移除item时adapter position为-1。

            if (isVertical) {
                if (position == 0) {
                    outRect.set(0, peekWidth, 0, 0);
                } else if (position == itemCount - 1) {
                    outRect.set(0, 0, 0, peekWidth);
                } else {
                    outRect.set(0, 0, 0, 0);
                }
            } else {
                if (position == 0) {
                    outRect.set(peekWidth, 0, 0, 0);
                } else if (position == itemCount - 1) {
                    outRect.set(0, 0, peekWidth, 0);
                } else {
                    outRect.set(0, 0, 0, 0);
                }
            }
        }
    }
}
