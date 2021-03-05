package com.study.reviewpager;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

public class MyApplication extends Application {
    private final static String TAG = "MyApplication";

    public static Context applicationContext;
    private boolean initialized = false;



    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        init();
    }

    private void initFresco() {
        DiskCacheConfig mainConfig = DiskCacheConfig.newBuilder(applicationContext).build();
        DiskCacheConfig smallConfig = DiskCacheConfig.newBuilder(applicationContext).build();

        ImagePipelineConfig.Builder configBuilder = ImagePipelineConfig.newBuilder(applicationContext)
                .setBitmapsConfig(Bitmap.Config.ARGB_8888)
                .setDownsampleEnabled(true)
                .setMainDiskCacheConfig(mainConfig)
                .setSmallImageDiskCacheConfig(smallConfig);

        ImagePipelineConfig config = configBuilder.build();
        Fresco.initialize(this, config);
    }

    private void init() {
        initFresco();
        initialized = true;
    }
}
