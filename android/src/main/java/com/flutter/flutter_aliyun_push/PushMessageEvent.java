package com.flutter.flutter_aliyun_push;

public class PushMessageEvent {

    public static final String EVENT_onPushRegistSuccess = "onPushRegistSuccess";
    public static final String EVENT_onPushRegistError = "onPushRegistError";
    public static final String EVENT_onReceiverNotification = "onReceiverNotification";
    public static final String EVENT_onReceiverMessage = "onReceiverMessage";

    public String eventName;
    public Object params;

     public  PushMessageEvent(String eventName,Object params) {
         this.eventName = eventName;
         this.params = params;
     }
}
