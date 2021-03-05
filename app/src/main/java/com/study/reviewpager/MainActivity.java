package com.study.reviewpager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.study.reviewpager.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private List<String> avatarList = new ArrayList<>();
    private ViewPagerAdapter mViewPagerAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecyclerView = findViewById(R.id.recyclerview);

        mockAvatarList();
        mViewPagerAdapter = new ViewPagerAdapter(avatarList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        new LinearSnapHelper().attachToRecyclerView(mRecyclerView);

        mRecyclerView.setAdapter(mViewPagerAdapter);
    }

    private void mockAvatarList() {
        avatarList.add("https://b-ssl.duitang.com/uploads/item/201811/04/20181104223950_vygmz.thumb.700_0.jpeg");
        avatarList.add("https://b-ssl.duitang.com/uploads/item/201807/11/20180711091152_FakCJ.thumb.700_0.jpeg");
        avatarList.add("https://b-ssl.duitang.com/uploads/item/201811/04/20181104223952_zfhli.thumb.700_0.jpeg");
        avatarList.add("https://b-ssl.duitang.com/uploads/item/201810/30/20181030153225_mixve.thumb.700_0.jpg");
        avatarList.add("https://b-ssl.duitang.com/uploads/item/201807/08/20180708095827_SYPL3.thumb.700_0.jpeg");
        avatarList.add("https://b-ssl.duitang.com/uploads/item/201811/01/20181101093301_u2NKu.thumb.700_0.jpeg");
        avatarList.add("https://b-ssl.duitang.com/uploads/item/201811/04/20181104223950_vygmz.thumb.700_0.jpeg");
        avatarList.add("https://b-ssl.duitang.com/uploads/item/201807/11/20180711091152_FakCJ.thumb.700_0.jpeg");
        avatarList.add("https://b-ssl.duitang.com/uploads/item/201811/04/20181104223952_zfhli.thumb.700_0.jpeg");
    }
}