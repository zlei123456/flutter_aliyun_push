package com.flutter.flutter_aliyun_push;

import android.content.Context;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;

import java.util.Map;

import io.flutter.Log;

public class FlutterMessageReceiver extends MessageReceiver {
    // 消息接收部分的LOG_TAG
    public static final String REC_TAG = "receiver";
    @Override
    public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        // TODO 处理推送通知
        Log.e("FlutterMessageReceiver", "Receive notification, title: " + title + ", summary: " + summary + ", extraMap: " + extraMap);
        FlutterPushNotification message = new FlutterPushNotification();
        message.title = title;
        message.summary = summary;
        message.extraMap = extraMap;
        FlutterAliyunPushPlugin.sendPushNotification(context,message);
    }
    @Override
    public void onMessage(Context context, CPushMessage cPushMessage) {
        Log.e("FlutterMessageReceiver", "onMessage, messageId: " + cPushMessage.getMessageId() + ", title: " + cPushMessage.getTitle() + ", content:" + cPushMessage.getContent());

        FlutterPushMessage message = new FlutterPushMessage();
        message.title = cPushMessage.getTitle();
        message.appId = cPushMessage.getAppId();
        message.messageId = cPushMessage.getMessageId();
        message.content = cPushMessage.getContent();
        message.traceInfo = cPushMessage.getTraceInfo();
        FlutterAliyunPushPlugin.sendPushMessage(context,message);

    }
    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        Log.e("FlutterMessageReceiver", "onNotificationOpened, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
    }
    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
        Log.e("FlutterMessageReceiver", "onNotificationClickedWithNoAction, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
    }
    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
        Log.e("FlutterMessageReceiver", "onNotificationReceivedInApp, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap + ", openType:" + openType + ", openActivity:" + openActivity + ", openUrl:" + openUrl);
    }
    @Override
    protected void onNotificationRemoved(Context context, String messageId) {
        Log.e("FlutterMessageReceiver", "onNotificationRemoved");
    }
}