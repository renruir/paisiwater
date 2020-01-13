package com.paisiwater.service.mqtt;

import com.paisiwater.model.WxAccessToken;
import com.paisiwater.service.WeixinService;
import com.paisiwater.service.constant.ServiceConstant;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.security.KeyStore;
import java.text.SimpleDateFormat;

/**
 * Created by renrui on 2016/12/14 0014.
 */

@Service
public class MqttService {

    private static final Logger logger = LoggerFactory.getLogger(MqttService.class);

    private static String clientId = "";

    private static final String topic_jsq = "nodes/paisi_jsq_state/status";

    private static final String topic_query = "nodes/paisi_jsq_state/query";

    private MqttClient mqttClient;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

    @Autowired
    private WeixinService weixinService;

    private WxAccessToken wxAccessToken;

    public boolean start() {
        try {
            logger.info("mqtt open start");
            wxAccessToken = weixinService.getAccessToken(ServiceConstant.appId);
            int rdm = (int) (Math.random() * (9000)) + 1000;//产生1000-9999的随机数
            clientId = "mqtt_for_tjn_jsq_" + String.valueOf(rdm);

//            TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
//            tmf.init((KeyStore)null);
//            TrustManager[] trustManagers = tmf.getTrustManagers();
//
//            SSLContext ctx = SSLContext.getInstance("TLS");
//            ctx.init(null, trustManagers, null);

            mqttClient = new MqttClient(ServiceConstant.MQTT_HOST, clientId);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName(ServiceConstant.username);
            connOpts.setPassword(ServiceConstant.password.toCharArray());
//            connOpts.setSocketFactory(ctx.getSocketFactory());

            mqttClient.connect(connOpts);
            mqttClient.subscribe(topic_jsq, WaterPurifierMqttListener.getInstance(weixinService));
            logger.info("mqtt start success");

//            byte[] head = new byte[]{(byte) 0x7A, (byte) 0x7A, (byte) 0x24, (byte) 0xe6, (byte) 0x13, (byte) 0x00, (byte) 0x00, (byte) 0xc0, (byte) 0x01};
//            mqttClient.publish("test/test", new MqttMessage(head));

            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public static int getDecValue(byte hBit, byte lBit) {
        if (((hBit & 0xff) == 0xff) && ((lBit & 0xff) == 0xff)) {
            return -1;
        }
        int high = (hBit & 0xff) << 8;
        int low = lBit & 0xff;
        return high + low;
    }

    public static int getDecValue(byte hBit, byte mBit, byte lBit) {
        if (((hBit & 0xff) == 0xff) && ((mBit & 0xff) == 0xff) && ((lBit & 0xff) == 0xff)) {
            return -1;
        }
        int high = (hBit & 0xff) << 16;
        int middle = (mBit & 0xff) << 8;
        int low = lBit & 0xff;
        return high + middle + low;
    }

//    @Scheduled(cron = "0 45 16 * * ? ")
//    @Scheduled(cron = "0 0 2 * * ? ")
    @Scheduled(cron = "0 0 0/8 * * ?")
    public void queryDevicesStatus() {
//        logger.info("===update devices status and time is " + simpleDateFormat.format(new Date()) + "=====");
        byte[] data = new byte[19];

        byte[] head = new byte[]{(byte) 0x7A, (byte) 0x7A, (byte) 0x24, (byte) 0xe6, (byte) 0x13, (byte) 0x00, (byte) 0x00, (byte) 0xc0, (byte) 0x01};

        byte[] timeStamp = hexStringToBytes(Long.toHexString(System.currentTimeMillis() / 1000));

        System.arraycopy(head, 0, data, 0, head.length);
        System.arraycopy(timeStamp, 0, data, head.length, timeStamp.length);

        data[data.length - 2] = (byte) 0xf1;
        data[data.length - 1] = (byte) 0xf2;

        try {
            MqttMessage msg = new MqttMessage(data);
            mqttClient.publish(topic_query, msg);
        } catch (MqttException e) {
            logger.info(e.getMessage());
        }
    }

//    private void replyDeviceTimeStamp(String deviceId) {
//
////        logger.info("###replyDeviceTimeStamp###");l
//        if (deviceId.isEmpty()) {
//            return;
//        }
//
//        byte[] data = new byte[17];
//
//        byte[] head = new byte[]{(byte) 0x7A, (byte) 0x7A, (byte) 0x24, (byte) 0xe6, (byte) 0x11, (byte) 0x00, (byte) 0x00, (byte) 0xbf, (byte) 0xf0};
//
//        String topic = "nodes/" + deviceId + "/timestamp";
//
//        byte[] timeStamp = hexStringToBytes(Long.toHexString(System.currentTimeMillis() / 1000));
//
//        System.arraycopy(head, 0, data, 0, head.length);
//        System.arraycopy(timeStamp, 0, data, head.length, timeStamp.length);
//
//        data[data.length - 2] = (byte) 0xf1;
//        data[data.length - 1] = (byte) 0xf2;
//
//        try {
//            MqttMessage msg = new MqttMessage(data);
//            if(mqttClient == null){
//                start();
//            }
//            mqttClient.publish(topic, msg);
//        } catch (MqttException e) {
//            logger.info(e.getMessage());
//        }
//    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    public static String byte2hex(byte[] buffer) {
        String h = "";
        for (int i = 0; i < buffer.length; i++) {
            String temp = Integer.toHexString(buffer[i] & 0xFF);
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            h = h + temp;
        }
        return h;
    }

    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }


    //test
    public static void main(String[] args) {
        MqttService mqttService = new MqttService();
        mqttService.start();
//        mqttService.replyDeviceTimeStamp("00e04c87000055f_1");
    }
}
