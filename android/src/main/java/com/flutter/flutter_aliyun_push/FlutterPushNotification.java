package com.flutter.flutter_aliyun_push;

import com.google.gson.Gson;

import java.util.Map;

public class FlutterPushNotification {

    public String title;
    public String summary;
    public Map<String, String> extraMap;



    public String getParamsJSONString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
