package com.paisiwater.service;

import com.paisiwater.service.constant.ServiceConstant;
import com.paisiwater.service.mqtt.WaterPurifierMqttListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class TestMqtt {
    private static MqttClient mqttClient;
    private static String clientId = "1111";
    private static final String topic_jsq = "nodes/paisi_jsq_state/status";

    private static final String topic_query = "nodes/paisi_jsq_state/query";
    public static void main(String[] args) {

        try {
            mqttClient = new MqttClient("ws://wx.mypraise.cn:61623", clientId);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName("admin");
            connOpts.setPassword("password".toCharArray());
//            connOpts.setSocketFactory(ctx.getSocketFactory());

            mqttClient.connect(connOpts);
//            mqttClient.subscribe(topic_jsq, WaterPurifierMqttListener.getInstance(weixinService));
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }
}
