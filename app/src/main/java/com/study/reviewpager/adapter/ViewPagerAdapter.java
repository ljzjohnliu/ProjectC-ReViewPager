package com.study.reviewpager.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.study.reviewpager.R;

import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

    private List<String> mData;

    private ViewPagerAdapter.OnItemClickListener onItemClickListener;
    private ViewPagerAdapter.OnBidStatClickListener onBidStatClickListener;

    public ViewPagerAdapter(List<String> data) {
        this.mData = data;
    }

    public List<String> getData() {
        return mData;
    }

    public void refreshData(List<String> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void resetDatas() {
        mData.clear();
    }

    public void updateList(List<String> newDatas) {
        if (newDatas != null) {
            mData.addAll(newDatas);
        }
        notifyDataSetChanged();
    }

    /**
     * 设置回调监听
     *
     * @param listener
     */
    public void setOnItemClickListener(ViewPagerAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public void setOnBidStatClickListener(ViewPagerAdapter.OnBidStatClickListener listener) {
        this.onBidStatClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_layout, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // 绑定数据
        holder.picImg.setImageURI(mData.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView, pos);
                }
                //表示此事件已经消费，不会触发单击事件
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public interface OnBidStatClickListener {
        void onBidStatClick(View view, int position, int state);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView picImg;

        public ViewHolder(View itemView) {
            super(itemView);
            picImg = itemView.findViewById(R.id.pic);
        }
    }
}
