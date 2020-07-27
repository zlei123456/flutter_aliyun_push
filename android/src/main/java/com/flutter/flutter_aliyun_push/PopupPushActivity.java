package com.flutter.flutter_aliyun_push;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.sdk.android.push.AndroidPopupActivity;

import java.util.Map;

import io.flutter.Log;


public class PopupPushActivity extends AndroidPopupActivity {
    static final String TAG = "PopupPushActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setGravity(Gravity.LEFT|Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.height = 1;
        params.width = 1;
        window.setAttributes(params);

    }
    /**
     * 实现通知打开回调方法，获取通知相关信息
     * @param title     标题
     * @param summary   内容
     * @param extMap    额外参数
     */
    @Override
    protected void onSysNoticeOpened(String title, String summary, Map<String, String> extMap) {
        Log.d(TAG,"OnMiPushSysNoticeOpened, title: " + title + ", content: " + summary + ", extMap: " + extMap);

        if(FlutterAliyunPushPlugin.isPluginAttached) {
            finish();
        }

        FlutterPushNotification notification = new FlutterPushNotification();
        notification.title = title;
        notification.summary = summary;
        notification.extraMap = extMap;
        FlutterAliyunPushPlugin.sendPushNotification(getApplicationContext(),notification);
        if(FlutterAliyunPushPlugin.isPluginAttached) {
            return;
        }

        Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
        startActivity(intent);
        finish();
    }


}