package com.flutter.flutter_aliyun_push;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

public class NotificationUtil {

    /**
     * 显示通知栏
     * @param context 上下文对象
     */
    public static void showNotification(Context context, String title,String description,String data) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // 兼容 8.0 系统
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(context, nm);
        }
        NotificationCompat.Builder builder = createNotificationCompatBuilder(context, title,description,data);

        nm.notify(0, builder.build());
    }

    @NonNull
    public static NotificationCompat.Builder createNotificationCompatBuilder(Context context,  String title,String description,String data) {
        // 通知栏点击接收者
        Intent i = new Intent("android.intent.action.MAIN");
        i.putExtra("data", data);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentIntent(pendingIntent);
        builder.setContentTitle(title);
        builder.setContentText(description);
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_ALL);

        return builder;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public static void createNotificationChannel(Context context, NotificationManager notificationManager) {
        // 通知渠道
        NotificationChannel mChannel = new NotificationChannel("渠道Id", "渠道名称", NotificationManager.IMPORTANCE_HIGH);
        // 开启指示灯，如果设备有的话。
        mChannel.enableLights(true);
        // 开启震动
        mChannel.enableVibration(true);
        //  设置指示灯颜色
        mChannel.setLightColor(Color.RED);
        // 设置是否应在锁定屏幕上显示此频道的通知
        mChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        // 设置是否显示角标
        mChannel.setShowBadge(true);
        //  设置绕过免打扰模式
        mChannel.setBypassDnd(true);
        // 设置震动频率
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400});
        //最后在notificationmanager中创建该通知渠道
        notificationManager.createNotificationChannel(mChannel);
    }
}
