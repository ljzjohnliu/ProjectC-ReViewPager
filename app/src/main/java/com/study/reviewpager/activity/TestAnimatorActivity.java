package com.study.reviewpager.activity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;
import com.study.reviewpager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestAnimatorActivity extends AppCompatActivity {

    @BindView(R.id.test_img)
    SimpleDraweeView testImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_animator);
        ButterKnife.bind(this);

        testAnimator();
    }

    private void testAnimator() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 400);
        animator.setDuration(1000);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int) animation.getAnimatedValue();
                Log.d("ljzxxx", "curValue:" + curValue);
                testImg.layout(curValue, curValue, curValue + testImg.getWidth(), curValue + testImg.getHeight());
            }
        });
        animator.start();
    }

}
