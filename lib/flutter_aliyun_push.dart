import 'dart:async';
import 'dart:convert';
import 'package:flutter/services.dart';
import 'push_message.dart';

typedef OnReceiveMessage = Function(PushMessage);
typedef OnReceiveNotification = Function(PushNotification);

class FlutterAliyunPush {
  static const MethodChannel _channel =
      const MethodChannel('aliyun_push');
  static bool registCallback = false;

  static Function onRegistSuccess;
  static Function onRegistError;
  static OnReceiveNotification onReceiveNotification;
  static OnReceiveMessage onReceiveMessage;

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
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
            var param = call.arguments;
            if(param != null) {
              param = PushNotification.fromJson(json.decode(param));
            }
            onReceiveNotification(param);
          }
          break;
        case "onReceiverMessage":
          if(onReceiveMessage != null) {
            var param = call.arguments;
            if(param != null) {
              param = PushMessage.fromJson(json.decode(param));
            }
            onReceiveMessage(param);
          }
          break;
      }
    });
    _channel.invokeMethod('initPush');
  }

  static void  reigistOnRegistSuccess(Function callback) {
    onRegistSuccess = callback;
    registCallHandler();
  }


  static void  reigistOnRegistError(Function callback) {
    onRegistError = callback;
    registCallHandler();
  }

  static void  reigistOnReceiveNotification(OnReceiveNotification callback) {
    onReceiveNotification = callback;
    registCallHandler();
  }

  static void  reigistOnReceiveMessage(OnReceiveMessage callback) {
    onReceiveMessage = callback;
    registCallHandler();
  }
}


