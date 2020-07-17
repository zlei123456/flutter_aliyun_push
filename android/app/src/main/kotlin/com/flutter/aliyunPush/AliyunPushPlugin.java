package com.flutter.aliyunPush;

import android.content.Context;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;

import io.flutter.Log;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.JSONMethodCodec;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

public class AliyunPushPlugin implements FlutterPlugin, MethodChannel.MethodCallHandler {

    private static AliyunPushPlugin instance;
    private final String TAG = "AliyunPushPlugin";
    private Context context;
    private Object initializationLock = new Object();
    private MethodChannel aliyunPushPluginChannel;

    public AliyunPushPlugin() {}

    public static void registerWith(PluginRegistry.Registrar registrar) {
        if (instance == null) {
            instance = new AliyunPushPlugin();
        }
        instance.onAttachedToEngine(registrar.context(), registrar.messenger());
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

            // alarmManagerPluginChannel is the channel responsible for receiving the following messages
            // from the main Flutter app:
            // - "AlarmService.start"
            // - "Alarm.oneShotAt"
            // - "Alarm.periodic"
            // - "Alarm.cancel"
            aliyunPushPluginChannel =
                    new MethodChannel(
                            messenger, "com.flutter.plugins.aliyun_push", JSONMethodCodec.INSTANCE);

            // Instantiate a new Plugin and connect the primary method channel for
            // Android/Flutter communication.
            aliyunPushPluginChannel.setMethodCallHandler(this);
            aliyunPushPluginChannel.invokeMethod("onPushInit",null);

        }
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        String method = call.method;
        Object arguments = call.arguments;
        try {
            if (method.equals("AlarmService.start")) {
                // This message is sent when the Dart side of this plugin is told to initialize.
                long callbackHandle = ((JSONArray) arguments).getLong(0);
                // In response, this (native) side of the plugin needs to spin up a background
                // Dart isolate by using the given callbackHandle, and then setup a background
                // method channel to communicate with the new background isolate. Once completed,
                // this onMethodCall() method will receive messages from both the primary and background
                // method channels.
                result.success(true);
            }else {
                result.notImplemented();
            }
        } catch (JSONException e) {
            result.error("error", "JSON error: " + e.getMessage(), null);
        } catch (Exception e) {
            result.error("error", "AlarmManager error: " + e.getMessage(), null);
        }
    }
}
