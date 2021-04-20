package com.study.reviewpager.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.study.reviewpager.R;
import com.study.reviewpager.adapter.GalleryAdapter;
import com.study.reviewpager.utils.CoverFlowLayoutManger;
import com.study.reviewpager.widget.RecyclerCoverFlow;

import java.util.ArrayList;
import java.util.List;

public class JustCoverFlowActivity extends AppCompatActivity implements GalleryAdapter.onItemClick {

    private RecyclerCoverFlow mList;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_just_coverflow);
        initList();
    }

    private void initList() {
        mList = findViewById(R.id.list);

        list.add("https://h-test-1256989820.cos.ap-shanghai.myqcloud.com/user/100136/IMG_0052.JPG");
        list.add("https://h-test-1256989820.cos.ap-shanghai.myqcloud.com/user/100136/IMG_0053.JPG");
        list.add("https://h-test-1256989820.cos.ap-shanghai.myqcloud.com/user/100136/IMG_0054.JPG");
        list.add("https://h-test-1256989820.cos.ap-shanghai.myqcloud.com/user/100143/IMG_4459.JPG");
        list.add("https://h-test-1256989820.cos.ap-shanghai.myqcloud.com/user/100143/IMG_4460.JPG");
        list.add("https://h-test-1256989820.cos.ap-shanghai.myqcloud.com/user/100143/IMG_4461.JPG");
//        mList.setFlatFlow(true); //平面滚动
        mList.setGreyItem(true); //设置灰度渐变
        mList.setAlphaItem(true); //设置半透渐变
        mList.setLoop(); //循环滚动，注：循环滚动模式暂不支持平滑滚动
        mList.setAdapter(new GalleryAdapter(this, list, false));
        mList.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                ((TextView) findViewById(R.id.index)).setText((position + 1) + "/" + mList.getLayoutManager().getItemCount());
            }
        });
    }

    @Override
    public void clickItem(int pos) {
        mList.smoothScrollToPosition(pos);
    }
}
