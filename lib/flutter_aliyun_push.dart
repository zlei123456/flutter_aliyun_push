import 'dart:async';

import 'package:flutter/services.dart';

class FlutterAliyunPush {
  static const MethodChannel _channel =
      const MethodChannel('aliyun_push');
  static bool registCallback = false;

  static Function onRegistSuccess;
  static Function onRegistError;
  static Function onReceiveNotification;
  static Function onReceiveMessage;

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<String> get initPush async {
    final String version = await _channel.invokeMethod('initPush');
    return version;
  }

  /**
   * 注册原生调用dart
   */
  static  void registCallHandler() {
    if(registCallback) {
      return;
    }
    registCallback = true;
    _channel.setMethodCallHandler((call) {
      print("setMethodCallHandler:"+call.method);
      switch(call.method) {
        case "onPushRegistSuccess":
          if(onRegistSuccess != null) {
            onRegistSuccess(call.arguments);
          }
          break;
        case "onPushRegistError":
          if(onRegistError != null) {
            onRegistError(call.arguments);
          }
          break;
        case "onReceiverNotification":
          if(onReceiveNotification != null) {
            onReceiveNotification(call.arguments);
          }
          break;
        case "onReceiverMessage":
          if(onReceiveMessage != null) {
            onReceiveMessage(call.arguments);
          }
          break;
      }
    });
  }

  static void  reigistOnRegistSuccess(Function callback) {
    onRegistSuccess = callback;
    registCallHandler();
  }


  static void  reigistOnRegistError(Function callback) {
    onRegistError = callback;
    registCallHandler();
  }

  static void  reigistOnReceiveNotification(Function callback) {
    onReceiveNotification = callback;
    registCallHandler();
  }

  static void  reigistOnReceiveMessage(Function callback) {
    onReceiveMessage = callback;
    registCallHandler();
  }
}


