package com.flutter.flutter_aliyun_push;

import com.google.gson.Gson;

import java.util.Map;

public class FlutterPushMessage {

    public String messageId;
    public String appId;
    public String title;
    public String content;
    public String traceInfo;


    public String getParamsJSONString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
