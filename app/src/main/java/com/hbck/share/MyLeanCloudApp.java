package com.hbck.share;

import android.app.Application;
import android.graphics.Color;

import com.avos.avoscloud.AVOSCloud;
import com.hbck.share.util.GlideImageLoader;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.PauseOnScrollListener;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * https://leancloud.cn/docs/sdk_setup-android.html#hash7247859
 */
public class MyLeanCloudApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"oWeMqwdpcfalQiOU8geMnUt2-gzGzoHsz","bsqsdNoCy7dIs0HpTt4cm1jN");
        // 放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        AVOSCloud.setDebugLogEnabled(true);


        //        https://github.com/pengjianbo/GalleryFinal/blob/master/app/src/main/java/cn/finalteam/galleryfinal/sample/MainActivity.java
        //设置主题
        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarBgColor(getResources().getColor(R.color.colorPrimary))
                .setTitleBarTextColor(Color.WHITE)
                .setTitleBarIconColor(Color.WHITE)
                .setFabNornalColor(getResources().getColor(R.color.colorPrimary))
                .setFabPressedColor(Color.BLUE)
                .setCheckNornalColor(Color.WHITE)
                .setCheckSelectedColor(getResources().getColor(R.color.colorPrimary))
//                .setIconBack(R.mipmap.ic_action_previous_item)
//                .setIconRotate(R.mipmap.ic_action_repeat)
//                .setIconCrop(R.mipmap.ic_action_crop)
//                .setIconCamera(R.mipmap.ic_action_camera)
                .build();
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();
        CoreConfig coreConfig = new CoreConfig.Builder(this, new GlideImageLoader(), theme)
                .setFunctionConfig(functionConfig)
                .setPauseOnScrollListener(new PauseOnScrollListener(false, true) {
                    @Override
                    public void resume() {

                    }

                    @Override
                    public void pause() {

                    }
                })
                .build();
        GalleryFinal.init(coreConfig);
    }
}