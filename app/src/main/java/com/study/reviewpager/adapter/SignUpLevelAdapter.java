package com.study.reviewpager.adapter;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.study.reviewpager.R;
import com.study.reviewpager.bean.LevelInfo;

import java.util.List;

public class SignUpLevelAdapter extends RecyclerView.Adapter<SignUpLevelAdapter.ViewHolder> {

    private List<LevelInfo> mData;

    private OnItemClickListener onItemClickListener;
    private OnBidStatClickListener onBidStatClickListener;

    public SignUpLevelAdapter(List<LevelInfo> data) {
        this.mData = data;
    }

    public List<LevelInfo> getData() {
        return mData;
    }

    public void refreshData(List<LevelInfo> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void resetDatas() {
        mData.clear();
    }

    public void updateList(List<LevelInfo> newDatas) {
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
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public void setOnBidStatClickListener(OnBidStatClickListener listener) {
        this.onBidStatClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.signup_level_item_layout, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.setIsRecyclable(false);
        Log.d("xxx", "onBindViewHolder: position = " + position + ", size = " + mData.size());
        if (position == 0 || position == mData.size() - 1) {
            return;
        }
        holder.firtSortTv.setText(mData.get(position).getFirstName());
        holder.secondSortTv.setText(mData.get(position).getSecondName());

        switch (mData.get(position).getLevel()) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                holder.picImg.setImageResource(R.mipmap.level_trainee_1_bg);
                break;
            case 7:
            case 8:
            case 9:
            case 10:
                holder.picImg.setImageResource(R.mipmap.level_trainee_2_bg);
                break;
            case 11:
            case 12:
            case 13:
                holder.picImg.setImageResource(R.mipmap.level_trainee_3_bg);
                break;
            case 14:
            case 15:
            case 16:
                holder.picImg.setImageResource(R.mipmap.level_xuanxiu_bg);
                break;
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
                holder.picImg.setImageResource(R.mipmap.level_first_star_bg);
                break;
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
                holder.picImg.setImageResource(R.mipmap.level_popular_artists_bg);
                break;
            case 28:
            case 29:
            case 30:
                holder.picImg.setImageResource(R.mipmap.level_traffic_star_bg);
                break;
            case 31:
            case 32:
            case 33:
            case 34:
                holder.picImg.setImageResource(R.mipmap.level_top_star_bg);
                break;
            case 35:
            case 36:
            case 37:
                holder.picImg.setImageResource(R.mipmap.level_global_superstar_bg);
                break;
        }

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

        ImageView picImg;
        TextView firtSortTv;
        TextView secondSortTv;

        public ViewHolder(View itemView) {
            super(itemView);
            picImg = itemView.findViewById(R.id.level_bg);
            firtSortTv = itemView.findViewById(R.id.first_sort);
            secondSortTv = itemView.findViewById(R.id.second_sort);
        }
    }
}
