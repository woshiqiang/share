package com.hbck.share;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

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
    }
}