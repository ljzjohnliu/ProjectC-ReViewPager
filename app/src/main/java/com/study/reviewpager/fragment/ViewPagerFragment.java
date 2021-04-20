package com.study.reviewpager.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.study.reviewpager.R;
import com.study.reviewpager.adapter.ImageCardAdapter;
import com.study.reviewpager.utils.BitmapUtils;
import com.study.reviewpager.utils.FastBlur;
import com.study.reviewpager.utils.GalleryLayoutManager;
import com.study.reviewpager.widget.CurveTransformer;
import com.study.reviewpager.widget.FlingRecycleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPagerFragment extends DialogFragment {
    @BindView(R.id.pager_bg)
    ImageView mPagerBg;
    @BindView(R.id.pager_recycle_view)
    FlingRecycleView mPagerRecycleView;
    List<Integer> mResId;

    public static ViewPagerFragment newInstance() {
        Bundle args = new Bundle();
        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Generate by live templates.
     * Use FragmentManager to find this Fragment's instance by tag
     */
    public static ViewPagerFragment findFragment(FragmentManager manager) {
        return (ViewPagerFragment) manager.findFragmentByTag(ViewPagerFragment.class.getSimpleName());
    }

    List<ImageCardAdapter.CardItem> mCardItems;

    {
        mResId = new ArrayList<Integer>(4);
        mResId.add(R.drawable.img1);
        mResId.add(R.drawable.img2);
        mResId.add(R.drawable.img3);
        mResId.add(R.drawable.img4);
        mCardItems = new ArrayList<ImageCardAdapter.CardItem>(50);
        ImageCardAdapter.CardItem cardItem;
        for (int i = 0; i < 50; i++) {
            cardItem = new ImageCardAdapter.CardItem(mResId.get(i % mResId.size()), "item:" + i);
            mCardItems.add(cardItem);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.fragment_view_pager, null, false);
        ButterKnife.bind(this, rootView);
        getDialog().setCancelable(true);
        getDialog().setCanceledOnTouchOutside(true);
        final Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;//DensityUtil.dip2px(getActivity(), 360);//WindowManager.LayoutParams.WRAP_CONTENT;
        Log.d("xxx", "onCreateView: wlp.height = " + wlp.height);
        window.setAttributes(wlp);

        initData();
        return rootView;
    }

    protected void initData() {
        mPagerRecycleView.setFlingAble(true);
        GalleryLayoutManager layoutManager = new GalleryLayoutManager(GalleryLayoutManager.HORIZONTAL);
        layoutManager.attach(mPagerRecycleView, 30);
//        layoutManager.attach(mPagerRecycleView);
        layoutManager.setOnItemSelectedListener(new GalleryLayoutManager.OnItemSelectedListener() {
            @Override
            public void onItemSelected(RecyclerView recyclerView, View item, int position) {
                Bitmap bmp = BitmapUtils.decodeSampledBitmapFromResource(getResources(), mResId.get(position % mResId.size()), 100, 100);
                mPagerBg.setImageBitmap(FastBlur.doBlur(bmp, 20, false));
            }
        });
        layoutManager.setItemTransformer(new CurveTransformer());
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        ImageCardAdapter imageAdapter = new ImageCardAdapter(mCardItems, (int) (displayMetrics.widthPixels * 0.7f), (int) (displayMetrics.heightPixels * 0.8f));
        imageAdapter.setOnItemClickListener(new ImageCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), "click" + mCardItems.get(position).mName, Toast.LENGTH_SHORT).show();
                mPagerRecycleView.smoothScrollToPosition(position);
            }
        });
        mPagerRecycleView.setAdapter(imageAdapter);
    }


}
