import 'dart:async';

import 'package:flutter/services.dart';

class FlutterAliyunPush {
  static const MethodChannel _channel =
      const MethodChannel('aliyun_push');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<String> get initPush async {
    final String version = await _channel.invokeMethod('initPush');
    return version;
  }
}


