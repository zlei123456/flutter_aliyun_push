package com.flutter.flutter_aliyun_push_example;



import com.flutter.flutter_aliyun_push.FlutterAliyunPushPlugin;

import io.flutter.Log;
import io.flutter.app.FlutterApplication;

public class AppApplication extends FlutterApplication  {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyApplication","onCreate");

        FlutterAliyunPushPlugin.initPush(this);
    }



}
