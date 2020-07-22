package com.flutter.flutter_aliyun_push;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.NonNull;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.huawei.HuaWeiRegister;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.alibaba.sdk.android.push.register.MeizuRegister;
import com.alibaba.sdk.android.push.register.MiPushRegister;
import com.alibaba.sdk.android.push.register.OppoRegister;
import com.alibaba.sdk.android.push.register.VivoRegister;

import org.json.JSONArray;
import org.json.JSONException;

import io.flutter.Log;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.JSONMethodCodec;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.common.StandardMethodCodec;

public class FlutterAliyunPushPlugin implements FlutterPlugin, MethodChannel.MethodCallHandler {

  public static FlutterAliyunPushPlugin instance;
  public static final String TAG = "AliyunPushPlugin";
  public static final String CHANNEL_NAME="aliyun_push";
  private Context context;
  public static Object initializationLock = new Object();
  private MethodChannel aliyunPushPluginChannel;
  private static String lastPushRegistSuccessMessage;
  private static String lastPushRegistErrorMessage;

  public MethodChannel getAliyunPushPluginChannel() {
    return aliyunPushPluginChannel;
  }

  public FlutterAliyunPushPlugin() {}

  public static void registerWith(PluginRegistry.Registrar registrar) {
    if (instance == null) {
      instance = new FlutterAliyunPushPlugin();
    }
    instance.onAttachedToEngine(registrar.context(), registrar.messenger());
  }

  public static void initPush(Context context) {
    Log.i(TAG, "start initPush");
    if (instance == null) {
      instance = new FlutterAliyunPushPlugin();
    }
    PushServiceFactory.init(context);
    initPushVersion(context);
    CloudPushService pushService = PushServiceFactory.getCloudPushService();
    pushService.register(context, new CommonCallback() {
      @Override
      public void onSuccess(String response) {
        Log.d(FlutterAliyunPushPlugin.TAG, "init cloudchannel success");
        synchronized (initializationLock) {
          if(instance.getAliyunPushPluginChannel() != null) {
            instance.getAliyunPushPluginChannel().invokeMethod("onPushRegistSuccess",response);
          }else {
            Log.d(FlutterAliyunPushPlugin.TAG, "instance.aliyunPushPluginChannel null");
            lastPushRegistSuccessMessage = response;
            lastPushRegistErrorMessage = null;
          }
        }

      }
      @Override
      public void onFailed(String errorCode, String errorMessage) {
        Log.d(FlutterAliyunPushPlugin.TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
        if(instance.getAliyunPushPluginChannel() != null) {
          instance.getAliyunPushPluginChannel().invokeMethod("onPushRegistError",errorMessage);
        }else {
          lastPushRegistErrorMessage = errorMessage;
          lastPushRegistSuccessMessage = null;
        }
      }
    });
  }

  /**
   * 初始化厂商推送
   */
  public static void initThirdPush(Context context) {
    ThirdPushConfig config = new ThirdPushConfig();

    ApplicationInfo appInfo = null;
    try {
      appInfo = context.getPackageManager()
              .getApplicationInfo(context.getPackageName(),
                      PackageManager.GET_META_DATA);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    if(appInfo == null) {
      return;
    }
    config.miPushAppId = appInfo.metaData.getString("com.mi.push.app_id");
    config.miPushAppKey = appInfo.metaData.getString("com.mi.push.api_key");

    config.huaweiPushAppId = appInfo.metaData.getString("com.huawei.hms.client.appid");

    config.vivoPushAppId = appInfo.metaData.getString("com.vivo.push.app_id");
    config.vivoPushAppKey = appInfo.metaData.getString("com.vivo.push.api_key");

    config.oppoPushAppKey = appInfo.metaData.getString("com.oppo.push.api_key");
    config.oppoPushAppSecret = appInfo.metaData.getString("com.oppo.push.app_secret");

    config.meizhuPushAppId = appInfo.metaData.getString("com.meizhu.push.app_id");
    config.meizhuPushAppKey = appInfo.metaData.getString("com.meizhu.push.api_key");

    // 注册方法会自动判断是否支持小米系统推送，如不支持会跳过注册。
    if(config.miPushAppId != null && config.miPushAppKey != null) {
      MiPushRegister.register(context, config.miPushAppId, config.miPushAppKey);
    }

    // 注册方法会自动判断是否支持华为系统推送，如不支持会跳过注册。
    if(config.huaweiPushAppId != null) {
      HuaWeiRegister.register((Application) context);
    }

    // OPPO通道注册
    if(config.oppoPushAppKey != null && config.oppoPushAppSecret != null) {
      OppoRegister.register(context, config.oppoPushAppKey, config.oppoPushAppSecret); // appKey/appSecret在OPPO开发者平台获取
    }

    // 魅族通道注册
    if(config.meizhuPushAppId != null && config.meizhuPushAppKey != null) {
      MeizuRegister.register(context, config.meizhuPushAppId, config.meizhuPushAppKey); // appId/appkey在魅族开发者平台获取
    }

    // VIVO通道注册
    if(config.vivoPushAppId != null && config.vivoPushAppKey != null) {
      VivoRegister.register(context);
    }

  }

  private static void initPushVersion(Context context) {
//    GcmRegister.register(this, "851061211440", "api-8646462459812937352-2848");
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
      // 通知渠道的id
      String id = "1";
      // 用户可以看到的通知渠道的名字.
      CharSequence name = "notification channel";
      // 用户可以看到的通知渠道的描述
      String description = "notification description";
      int importance = NotificationManager.IMPORTANCE_HIGH;
      NotificationChannel mChannel = new NotificationChannel(id, name, importance);
      // 配置通知渠道的属性
      mChannel.setDescription(description);
      // 设置通知出现时的闪灯（如果 android 设备支持的话）
      mChannel.enableLights(true);
      mChannel.setLightColor(Color.RED);
      // 设置通知出现时的震动（如果 android 设备支持的话）
      mChannel.enableVibration(true);
      mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
      //最后在notificationmanager中创建该通知渠道
      mNotificationManager.createNotificationChannel(mChannel);
    }
  }



  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
    onAttachedToEngine(binding.getApplicationContext(), binding.getBinaryMessenger());
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    Log.i(TAG, "onDetachedFromEngine");
    context = null;
    aliyunPushPluginChannel.setMethodCallHandler(null);
    aliyunPushPluginChannel = null;
  }


  public void onAttachedToEngine(Context applicationContext, BinaryMessenger messenger) {
    synchronized (initializationLock) {
      if (aliyunPushPluginChannel != null) {
        return;
      }

      Log.i(TAG, "onAttachedToEngine");
      this.context = applicationContext;

      aliyunPushPluginChannel =
              new MethodChannel(
                      messenger, CHANNEL_NAME, StandardMethodCodec.INSTANCE);

      // Instantiate a new Plugin and connect the primary method channel for
      // Android/Flutter communication.
      aliyunPushPluginChannel.setMethodCallHandler(this);

      if(lastPushRegistSuccessMessage != null) {
        Log.i(TAG, "invokeMethod:"+ "onPushRegistSuccess");
        aliyunPushPluginChannel.invokeMethod("onPushRegistSuccess",lastPushRegistSuccessMessage);
        lastPushRegistSuccessMessage = null;
      }else if(lastPushRegistErrorMessage != null) {
        Log.i(TAG, "invokeMethod:"+ "onPushRegistError");
        aliyunPushPluginChannel.invokeMethod("onPushRegistError",lastPushRegistErrorMessage);
        lastPushRegistErrorMessage = null;
      }
//      aliyunPushPluginChannel.invokeMethod("onPushInit",null);

    }
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
    Log.i(TAG, "onMethodCall:"+call.method);
    String method = call.method;
    Object arguments = call.arguments;
    try {
      if (method.equals("getPlatformVersion")) {
        // This message is sent when the Dart side of this plugin is told to initialize.
        result.success("Android " + android.os.Build.VERSION.RELEASE);
      }else if(method.equals("initPush")) {
//        initPush(this.context);

      }
      else {
        result.notImplemented();
      }
    } catch (Exception e) {
      result.error("error", "AlarmManager error: " + e.getMessage(), null);
    }
  }


}
