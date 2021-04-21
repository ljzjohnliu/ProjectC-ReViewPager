package com.study.reviewpager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.study.reviewpager.R;
import com.study.reviewpager.adapter.SignUpLevelAdapter;
import com.study.reviewpager.adapter.ViewPagerAdapter;
import com.study.reviewpager.bean.LevelInfo;
import com.study.reviewpager.fragment.ViewPagerFragment;
import com.study.reviewpager.utils.HorizontalItemDecoration;
import com.study.reviewpager.utils.MyLinearSmoothScroller;
import com.study.reviewpager.utils.ViewPagerSnapHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.pic_recyclerview)
    RecyclerView picRecyclerView;
    @BindView(R.id.level_list)
    RecyclerView levelRecyclerView;

    private List<String> picList = new ArrayList<>();
    private ViewPagerAdapter picViewPagerAdapter;

    private SignUpLevelAdapter mLevelAdapter;
    private final List<LevelInfo> mLevelDatas = new ArrayList<>();
    private int mPosition = 0;
    private boolean isScrol = false;

    MyLinearSmoothScroller scroller;

    @OnClick({R.id.sign_reduce, R.id.sign_add, R.id.test_btn, R.id.test_btn2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test_btn:
//                showFragment();
                startActivity(new Intent(this, JustCoverFlowActivity.class));
                break;
            case R.id.test_btn2:
//                startActivity(new Intent(this, JustCoverFlowActivity.class));
                startActivity(new Intent(this, TestAnimatorActivity.class));
                break;
            case R.id.sign_reduce:
                mPosition--;
                if (mPosition <= 0) {
                    mPosition = 1;
                    return;
                }
                Log.d(TAG, "onClick: sign_reduce mPosition = " + mPosition);
//                levelRecyclerView.smoothScrollToPosition(mPosition);
//                ((LinearLayoutManager)levelRecyclerView.getLayoutManager()).scrollToPositionWithOffset(mPosition,1);
                if (scroller == null) {
                    scroller = new MyLinearSmoothScroller(this);
                }
                scroller.setTargetPosition(mPosition);
                ((LinearLayoutManager) levelRecyclerView.getLayoutManager()).startSmoothScroll(scroller);
                break;
            case R.id.sign_add:
                if (mPosition < mLevelDatas.size() - 2) {
                    mPosition++;
                }
                Log.d(TAG, "onClick: sign_add mPosition = " + mPosition);
//                levelRecyclerView.smoothScrollToPosition(mPosition);
//                ((LinearLayoutManager)levelRecyclerView.getLayoutManager()).scrollToPositionWithOffset(mPosition,1);
                if (scroller == null) {
                    scroller = new MyLinearSmoothScroller(this);
                }
                scroller.setTargetPosition(mPosition);
                ((LinearLayoutManager) levelRecyclerView.getLayoutManager()).startSmoothScroll(scroller);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mockAvatarList();
        picViewPagerAdapter = new ViewPagerAdapter(picList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        picRecyclerView.setLayoutManager(linearLayoutManager);

//        new LinearSnapHelper().attachToRecyclerView(mRecyclerView);
        ViewPagerSnapHelper snapHelper = new ViewPagerSnapHelper();
        picRecyclerView.setOnFlingListener(null);
        snapHelper.attachToRecyclerView(picRecyclerView);

        picRecyclerView.setAdapter(picViewPagerAdapter);
        picRecyclerView.smoothScrollToPosition(1);

        mockLevelData();

        LinearLayoutManager levelLayoutManager = new LinearLayoutManager(this);
        levelLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        levelRecyclerView.setLayoutManager(levelLayoutManager);
        levelRecyclerView.addItemDecoration(new HorizontalItemDecoration(10, this));
//        levelRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration(10, this));
        mLevelAdapter = new SignUpLevelAdapter(mLevelDatas);
        levelRecyclerView.setAdapter(mLevelAdapter);

        new LinearSnapHelper().attachToRecyclerView(levelRecyclerView);

        levelRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (recyclerView.getChildCount() > 0) {
                    try {
                        int currentPosition = ((RecyclerView.LayoutParams) recyclerView.getChildAt(0).getLayoutParams()).getViewAdapterPosition();
                        Log.d(TAG, "onScrollStateChanged: newState = " + newState + ", currentPosition = " + currentPosition);
                        if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                            isScrol = true;
                        }
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            if (isScrol) {
                                Log.d(TAG, "onScrollStateChanged: currentPosition = " + currentPosition);
                                if (currentPosition < 1) {
                                    mPosition = 1;
                                    Log.d(TAG, "onScrollStateChanged: 111 mPosition = " + mPosition);
//                                    levelRecyclerView.smoothScrollToPosition(mPosition);
                                    if (scroller == null) {
                                        scroller = new MyLinearSmoothScroller(MainActivity.this);
                                    }
                                    //设置目标位置
                                    scroller.setTargetPosition(mPosition);
                                    //开始平滑滚动
                                    ((LinearLayoutManager) levelRecyclerView.getLayoutManager()).startSmoothScroll(scroller);
                                    isScrol = false;
                                } else if (currentPosition >= mLevelDatas.size() - 2) {
                                    mPosition = mLevelDatas.size() - 2;
                                    Log.d(TAG, "onScrollStateChanged: 222 mPosition = " + mPosition);
//                                    levelRecyclerView.smoothScrollToPosition(mPosition);
                                    if (scroller == null) {
                                        scroller = new MyLinearSmoothScroller(MainActivity.this);
                                    }
                                    //设置目标位置
                                    scroller.setTargetPosition(mPosition);
                                    //开始平滑滚动
                                    ((LinearLayoutManager) levelRecyclerView.getLayoutManager()).startSmoothScroll(scroller);
                                    isScrol = false;
                                } else {
                                    mPosition = currentPosition + 1;
                                    isScrol = false;
                                    Log.d(TAG, "onScrollStateChanged: 333 mPosition = " + mPosition);
//                                    levelRecyclerView.smoothScrollToPosition(mPosition);
                                    if (scroller == null) {
                                        scroller = new MyLinearSmoothScroller(MainActivity.this);
                                    }
                                    //设置目标位置
                                    scroller.setTargetPosition(mPosition);
                                    //开始平滑滚动
                                    ((LinearLayoutManager) levelRecyclerView.getLayoutManager()).startSmoothScroll(scroller);
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.d(TAG, "onScrollStateChanged: e = " + e);
                    }
                }

            }
        });

        mPosition = 22;
        Log.d(TAG, "updateUI: mPosition = " + mPosition);

//        levelRecyclerView.smoothScrollToPosition(mPosition);
        ((LinearLayoutManager) levelRecyclerView.getLayoutManager()).scrollToPositionWithOffset(mPosition, 0);//不带动画的定位
        ((LinearLayoutManager) levelRecyclerView.getLayoutManager()).setStackFromEnd(true);

        if (scroller == null) {
            scroller = new MyLinearSmoothScroller(this);
        }
        //设置目标位置
        scroller.setTargetPosition(mPosition);
        //开始平滑滚动
        ((LinearLayoutManager) levelRecyclerView.getLayoutManager()).startSmoothScroll(scroller);
    }

    private void mockAvatarList() {
        picList.add("https://b-ssl.duitang.com/uploads/item/201811/04/20181104223950_vygmz.thumb.700_0.jpeg");
        picList.add("https://b-ssl.duitang.com/uploads/item/201807/11/20180711091152_FakCJ.thumb.700_0.jpeg");
        picList.add("https://b-ssl.duitang.com/uploads/item/201811/04/20181104223952_zfhli.thumb.700_0.jpeg");
        picList.add("https://b-ssl.duitang.com/uploads/item/201810/30/20181030153225_mixve.thumb.700_0.jpg");
        picList.add("https://b-ssl.duitang.com/uploads/item/201807/08/20180708095827_SYPL3.thumb.700_0.jpeg");
        picList.add("https://b-ssl.duitang.com/uploads/item/201811/01/20181101093301_u2NKu.thumb.700_0.jpeg");
        picList.add("https://b-ssl.duitang.com/uploads/item/201811/04/20181104223950_vygmz.thumb.700_0.jpeg");
        picList.add("https://b-ssl.duitang.com/uploads/item/201807/11/20180711091152_FakCJ.thumb.700_0.jpeg");
        picList.add("https://b-ssl.duitang.com/uploads/item/201811/04/20181104223952_zfhli.thumb.700_0.jpeg");
    }

    private void mockLevelData() {
        mLevelDatas.add(new LevelInfo());
        mLevelDatas.add(new LevelInfo(1, "冷板凳学员", ""));
        mLevelDatas.add(new LevelInfo(2, "初级练习生", "一年级"));
        mLevelDatas.add(new LevelInfo(3, "初级练习生", "二年级"));
        mLevelDatas.add(new LevelInfo(4, "初级练习生", "三年级"));
        mLevelDatas.add(new LevelInfo(5, "初级练习生", "四年级"));
        mLevelDatas.add(new LevelInfo(6, "初级练习生", "五年级"));

        mLevelDatas.add(new LevelInfo(7, "中级练习生", "一年级"));
        mLevelDatas.add(new LevelInfo(8, "中级练习生", "二年级"));
        mLevelDatas.add(new LevelInfo(9, "中级练习生", "三年级"));
        mLevelDatas.add(new LevelInfo(10, "中级练习生", "四年级"));

        mLevelDatas.add(new LevelInfo(11, "高级练习生", "一年级"));
        mLevelDatas.add(new LevelInfo(12, "高级练习生", "二年级"));
        mLevelDatas.add(new LevelInfo(13, "高级练习生", "三年级"));

        mLevelDatas.add(new LevelInfo(14, "选秀赛", "初级生"));
        mLevelDatas.add(new LevelInfo(15, "选秀赛", "晋级生"));
        mLevelDatas.add(new LevelInfo(16, "选秀赛", "人气生"));

        mLevelDatas.add(new LevelInfo(17, "初入星路", "C位出道"));
        mLevelDatas.add(new LevelInfo(18, "初入星路", "清纯担当"));
        mLevelDatas.add(new LevelInfo(19, "初入星路", "Rap担当"));
        mLevelDatas.add(new LevelInfo(20, "初入星路", "身材担当"));
        mLevelDatas.add(new LevelInfo(21, "初入星路", "主唱担当"));
        mLevelDatas.add(new LevelInfo(22, "初入星路", "队长担当"));

        mLevelDatas.add(new LevelInfo(23, "当红艺人", "MTV主演"));
        mLevelDatas.add(new LevelInfo(24, "当红艺人", "专辑发行"));
        mLevelDatas.add(new LevelInfo(25, "当红艺人", "访谈节目"));
        mLevelDatas.add(new LevelInfo(26, "当红艺人", "全球巡演"));
        mLevelDatas.add(new LevelInfo(27, "当红艺人", "真人秀"));

        mLevelDatas.add(new LevelInfo(28, "流量明星", "最佳新人奖"));
        mLevelDatas.add(new LevelInfo(29, "流量明星", "最佳着装奖"));
        mLevelDatas.add(new LevelInfo(30, "流量明星", "最佳人气奖"));

        mLevelDatas.add(new LevelInfo(31, "顶级明星", "最佳女配角"));
        mLevelDatas.add(new LevelInfo(32, "顶级明星", "最佳女主角"));
        mLevelDatas.add(new LevelInfo(33, "顶级明星", "Bazza封面"));
        mLevelDatas.add(new LevelInfo(34, "顶级明星", "Channel代言"));

        mLevelDatas.add(new LevelInfo(35, "全球巨星", "年度影后"));
        mLevelDatas.add(new LevelInfo(36, "全球巨星", "嘎纳影后"));
        mLevelDatas.add(new LevelInfo(37, "全球巨星", "奥斯卡影后"));
        mLevelDatas.add(new LevelInfo());
    }

    private void showFragment() {
        ViewPagerFragment fragment = ViewPagerFragment.newInstance();
        fragment.show(getSupportFragmentManager(), "ViewPagerFragment");
    }
}