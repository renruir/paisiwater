package com.paisiwater.service;

import com.paisiwater.api.model.BaseTemplateStruct;

import java.util.Map;

/**
 * Created by renrui on 2017/8/16/0016.
 */
public interface MqttServiceInterface {

//    public void replyDeviceTimeStamp(String deviceId);

    public void packageModelMsg(int flag, String openID, Map<String, BaseTemplateStruct> data);
}
