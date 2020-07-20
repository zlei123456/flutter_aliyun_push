package com.flutter.flutter_aliyun_push;

import android.content.Context;

import androidx.annotation.NonNull;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;

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

  private static FlutterAliyunPushPlugin instance;
  public static final String TAG = "AliyunPushPlugin";
  public static final String CHANNEL_NAME="aliyun_push";
  private Context context;
  private Object initializationLock = new Object();
  private MethodChannel aliyunPushPluginChannel;

  public FlutterAliyunPushPlugin() {}

  public static void registerWith(PluginRegistry.Registrar registrar) {
    if (instance == null) {
      instance = new FlutterAliyunPushPlugin();
    }
    instance.onAttachedToEngine(registrar.context(), registrar.messenger());
  }

  public static void initPush(Context context) {
    Log.i(TAG, "start initPush");
    PushServiceFactory.init(context);
    CloudPushService pushService = PushServiceFactory.getCloudPushService();
    pushService.register(context, new CommonCallback() {
      @Override
      public void onSuccess(String response) {
        Log.d(FlutterAliyunPushPlugin.TAG, "init cloudchannel success");
      }
      @Override
      public void onFailed(String errorCode, String errorMessage) {
        Log.d(FlutterAliyunPushPlugin.TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
      }
    });

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
