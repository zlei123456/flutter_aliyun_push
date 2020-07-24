import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_aliyun_push/flutter_aliyun_push.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {

    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = await FlutterAliyunPush.platformVersion;
//      await FlutterAliyunPush.initPush;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    FlutterAliyunPush.reigistOnRegistSuccess((msg){
      platformVersion = msg;
      setState(() {
        _platformVersion = platformVersion;
      });
    });

    FlutterAliyunPush.reigistOnReceiveNotification((msg){
      platformVersion = msg.title;
      setState(() {
        _platformVersion = platformVersion;
      });
    });

    FlutterAliyunPush.reigistOnReceiveMessage((msg){
      platformVersion = msg.title;
      setState(() {
        _platformVersion = platformVersion;
      });
    });

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('Running on: $_platformVersion\n'),
        ),
      ),
    );
  }
}
