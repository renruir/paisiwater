package com.paisiwater.service.mqtt;

import com.alibaba.fastjson.JSON;
import com.paisiwater.api.model.BaseTemplateStruct;
import com.paisiwater.api.model.DeviceDataStat;
import com.paisiwater.api.model.DeviceInfo;
import com.paisiwater.api.model.ModelMsg;
import com.paisiwater.device.model.FilterUseInfo;
import com.paisiwater.device.model.WaterModel;
import com.paisiwater.model.WxBindInfo;
import com.paisiwater.service.MqttServiceInterface;
import com.paisiwater.service.WeixinService;
import com.paisiwater.service.constant.ServiceConstant;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by renrui on 2017/8/16/0016.
 */
public class WaterPurifierMqttListener implements IMqttMessageListener, MqttServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(WaterPurifierMqttListener.class);

    private static final int DEVICE_STATE_PACKAGE_LENGTH = 50;//状态包的长度

    private static final int FAULT_PACKAGE_LENGTH = 34;//故障提醒包长度

    private static final int FILTER_STATE_PACKAGE_LENGTH = 38;//滤芯寿命提醒包长度

    private static final int DEVICEID_LENGTH = 18;

    private static final int FILTER_COUNT = 6;

    private static int index = 8;

    private String recDeviceId = "";

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm");

    private MqttClient mqttClient;

    private DeviceInfo deviceInfo;

    private WeixinService weixinService;

    private static WaterPurifierMqttListener waterPurifierMqttListener;

    public static WaterPurifierMqttListener getInstance(WeixinService service) {
        if(waterPurifierMqttListener == null){
            waterPurifierMqttListener = new WaterPurifierMqttListener(service);
        }
        return waterPurifierMqttListener;
    }

    private WaterPurifierMqttListener(WeixinService service) {
        weixinService = service;
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        try {
            byte[] recMsg = mqttMessage.getPayload();
            if (recMsg == null || recMsg.length == 0 || recMsg.length < FAULT_PACKAGE_LENGTH) {
                return;
            }
            byte[] realMsg = releasePackage(recMsg);
            if (realMsg.length == DEVICE_STATE_PACKAGE_LENGTH) {
                processStatePackage(realMsg);
            } else if (realMsg.length == FAULT_PACKAGE_LENGTH) {
                processFaultPackage(realMsg);
            } else if (realMsg.length == FILTER_STATE_PACKAGE_LENGTH) {
                processFilterPackage(realMsg);
            } else {
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public byte[] releasePackage(byte[] data) {
        byte[] validData;
        if (data.length == DEVICE_STATE_PACKAGE_LENGTH || data.length == FAULT_PACKAGE_LENGTH || data.length == FILTER_STATE_PACKAGE_LENGTH) {
            return data;
        } else if (data.length < FAULT_PACKAGE_LENGTH) {
            data = new byte[]{0x00, 0x00};
            return data;
        } else {
            String d = MqttService.byte2hex(data);
            String tempData[] = d.split("(?=7a7a)");
            for (String a : tempData) {
                if ((a.startsWith("7a7a")) && ((a.length() == FAULT_PACKAGE_LENGTH * 2)) ||
                        (a.length() == FILTER_STATE_PACKAGE_LENGTH * 2) || (a.length() == DEVICE_STATE_PACKAGE_LENGTH * 2)) {
                    validData = MqttService.hexStringToBytes(a);
                    return validData;
                }
            }
        }
        return data;
    }

    private void processStatePackage(byte[] data) {
        byte[] deviceId = new byte[DEVICEID_LENGTH];
        for (int i = 0; i < DEVICEID_LENGTH; i++) {
            deviceId[i] = data[i + (index + 18)];
        }
        try {
            recDeviceId = new String(deviceId, "UTF-8");
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        if (recDeviceId.isEmpty()) {
            return;
        }
//        replyDeviceTimeStamp(recDeviceId);
        setDeviceDataStat(data, recDeviceId);
    }

    private void getBindInfo() {
    }


    private void processFilterPackage(byte[] data) {
        byte[] deviceId = new byte[DEVICEID_LENGTH];
        for (int i = 0; i < DEVICEID_LENGTH; i++) {
            deviceId[i] = data[i + (index + 7)];
        }
        try {
            recDeviceId = new String(deviceId, "UTF-8");
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        logger.info("device id: " + recDeviceId);
        if (recDeviceId.isEmpty()) {
            return;
        }

        deviceInfo = weixinService.getDeviceInfo(recDeviceId);
        String model = deviceInfo.getModel();
        List<String> openIDArray = weixinService.getBindUserOpenId(recDeviceId);

        FilterUseInfo filterUseInfo = new FilterUseInfo();
        filterUseInfo.setDevice_id(recDeviceId);
        filterUseInfo.setOpenid(openIDArray.get(0));
        filterUseInfo.setModel(deviceInfo.getModel());

        WaterModel waterModel = new WaterModel();
        waterModel.setModel(model);

        int filterState[] = new int[FILTER_COUNT];
        for (int i = 0; i < FILTER_COUNT; i++) {
            filterState[i] = data[i + (index + 1)] & 0xff;
        }
        filterUseInfo.setFilter_1_name(ServiceConstant.filterInfo[0]);
        filterUseInfo.setFilter_2_name(ServiceConstant.filterInfo[1]);
        filterUseInfo.setFilter_3_name(ServiceConstant.filterInfo[2]);
        filterUseInfo.setFilter_4_name(ServiceConstant.filterInfo[3]);
        filterUseInfo.setFilter_1_unused(String.valueOf(filterState[0]));
        filterUseInfo.setFilter_2_unused(String.valueOf(filterState[1]));
        filterUseInfo.setFilter_3_unused(String.valueOf(filterState[2]));
        filterUseInfo.setFilter_4_unused(String.valueOf(filterState[3]));
        try {
            weixinService.saveFilterUseInfo(filterUseInfo);
        } catch (Exception e){
            e.printStackTrace();
        }

        waterModel.setFilterName(ServiceConstant.filterInfo);
        waterModel.setFilterSurplus(filterState);
        sendFilterNotify(waterModel);
    }

    private void sendFilterNotify(WaterModel waterModel) {
        for (int i = 0; i < waterModel.getFilterName().length; i++) {
            if (waterModel.getFilterSurplus()[i] < 6) {
                try {
                    List<String> openIDArray = weixinService.getBindUserOpenId(recDeviceId);
                    String installTime = weixinService.getRegisterTime(recDeviceId);
                    String used = (100 - waterModel.getFilterSurplus()[i]) + "%";
                    String replaceModel = waterModel.getFilterName()[i];

                    Map<String, BaseTemplateStruct> map = new HashMap<String, BaseTemplateStruct>();

                    map.put("first", new BaseTemplateStruct("净水器滤芯更换提醒！", "#0d0c0c"));
                    map.put("keyword1", new BaseTemplateStruct("特洁恩智能净水器", "#0d0c0c"));
                    map.put("keyword2", new BaseTemplateStruct(installTime, "#0d0c0c"));
                    map.put("keyword3", new BaseTemplateStruct(used, "#f40505"));
                    map.put("keyword4", new BaseTemplateStruct(replaceModel, "#f40505"));
                    map.put("remark", new BaseTemplateStruct("您的净水器滤芯将达到最大使用期限，请及时更换！", "#0d0c0c"));
                    for (int j = 0; j < openIDArray.size(); j++) {
                        packageModelMsg(ServiceConstant.MODEL_MSG_FLAG_FILTER, openIDArray.get(j), map);
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }


    private void processFaultPackage(byte[] data) {
        byte[] deviceId = new byte[DEVICEID_LENGTH];
        for (int i = 0; i < DEVICEID_LENGTH; i++) {
            deviceId[i] = data[i + (index + 3)];
        }
        try {
            recDeviceId = new String(deviceId, "UTF-8");
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        if (recDeviceId.isEmpty()) {
            return;
        }
        String deviceFault = "";
        switch (data[index + 1]) {
            case 0x01:
                deviceFault = ServiceConstant.DEVICE_FAULT_1;
                break;
            case 0x02:
                deviceFault = ServiceConstant.DEVICE_FAULT_2;
                break;
            case 0x03:
                deviceFault = ServiceConstant.DEVICE_FAULT_3;
                break;
            case 0x04:
                deviceFault = ServiceConstant.DEVICE_FAULT_VALVE_3;
                break;
            case 0x05:
                deviceFault = ServiceConstant.DEVICE_FAULT_VALVE_4;
                break;
            case 0x06:
                deviceFault = ServiceConstant.DEVICE_FAULT_VALVE_5;
                break;
            case 0x09:
                deviceFault = ServiceConstant.DEVICE_FAULT_9;
                break;
            case 0x10:
                deviceFault = ServiceConstant.DEVICE_FAULT_10;
                break;
            case 0x11:
                deviceFault = ServiceConstant.DEVICE_FAULT_11;
                break;
            default:
                deviceFault = ServiceConstant.DEVICE_DEFAULT_FAULT;
                break;
        }
        try {
            List<String> openIDArray = weixinService.getBindUserOpenId(recDeviceId);
            Map<String, BaseTemplateStruct> map = new HashMap<String, BaseTemplateStruct>();
            map.put("first", new BaseTemplateStruct("您的净水器出现故障！", "#0d0c0c"));
            map.put("keyword1", new BaseTemplateStruct(deviceFault, "#f40505"));
            map.put("keyword2", new BaseTemplateStruct(simpleDateFormat.format(new Date()), "#0d0c0c"));
            map.put("remark", new BaseTemplateStruct("请及时处理或者拨打客户电话保修", "#0d0c0c"));
            for (int i = 0; i < openIDArray.size(); i++) {
                packageModelMsg(ServiceConstant.MODEL_MSG_FLAG_FAULT, openIDArray.get(i), map);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private DeviceDataStat setDeviceDataStat(byte[] recMsg, String deviceId) {
        if (deviceId.isEmpty()) {
            return null;
        }
        try {
            DeviceDataStat mDeviceDataStat = new DeviceDataStat();
            mDeviceDataStat.setYsTds(MqttService.getDecValue(recMsg[index + 1], recMsg[index + 2]));
            mDeviceDataStat.setCsTds(MqttService.getDecValue(recMsg[index + 3], recMsg[index + 4]));
            mDeviceDataStat.setJsTotal(MqttService.getDecValue(recMsg[index + 5], recMsg[index + 6], recMsg[index + 7]));
            mDeviceDataStat.setJhsSize(MqttService.getDecValue(recMsg[index + 8], recMsg[index + 9], recMsg[index + 10]));
            mDeviceDataStat.setCjsSize(MqttService.getDecValue(recMsg[index + 11], recMsg[index + 12], recMsg[index + 13]));
            mDeviceDataStat.setYsSize(MqttService.getDecValue(recMsg[index + 14], recMsg[index + 15], recMsg[index + 16]));
            mDeviceDataStat.setZsTime(((recMsg[index + 17] & 0xff) == 0x11) ? 0 : 1);
            mDeviceDataStat.setDeviceId(deviceId);

            if (mDeviceDataStat.getZsTime() == 0) {
                //TO DO
            } else {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date utilDate = new Date();
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("deviceId", mDeviceDataStat.getDeviceId());
                map.put("sqlDate", sqlDate);

                weixinService.delDeviceDataStat(map);
                weixinService.saveDeviceDataStat(mDeviceDataStat);
            }

            return mDeviceDataStat;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    @Override
//    public void replyDeviceTimeStamp(String deviceId) {
//        logger.info("###replyDeviceTimeStamp###");
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
//        byte[] timeStamp = MqttService.hexStringToBytes(Long.toHexString(System.currentTimeMillis() / 1000));
//
//        System.arraycopy(head, 0, data, 0, head.length);
//        System.arraycopy(timeStamp, 0, data, head.length, timeStamp.length);
//
//        data[data.length - 2] = (byte) 0xf1;
//        data[data.length - 1] = (byte) 0xf2;
//
//        try {
//            MqttMessage msg = new MqttMessage(data);
//            if (mqttClient == null) {
//                initMqttClient();
//            }
//            mqttClient.publish(topic, msg);
//        } catch (MqttException e) {
//
//        }
//    }

    @Override
    public void packageModelMsg(int flag, String openID, Map<String, BaseTemplateStruct> data) {
        ModelMsg modelMsg = new ModelMsg();
        if (flag == ServiceConstant.MODEL_MSG_FLAG_FILTER) {
            modelMsg.setTemplate_id(ServiceConstant.FILTER_NOTICE_MODEL_ID);
        } else if (flag == ServiceConstant.MODEL_MSG_FLAG_FAULT) {
            modelMsg.setTemplate_id(ServiceConstant.FAULT_NOTICE_MODEL_ID);
        }
        modelMsg.setTouser(openID);
        modelMsg.setUrl("http://weixin.tejien.com/web/wechat/index.html");
        modelMsg.setTopcolor("#FF0000");
        modelMsg.setData(data);
        weixinService.sendTemplateMsg(JSON.toJSONString(modelMsg));
    }

    public void initMqttClient() {
        try {
            int rdm = (int) (Math.random() * (9000)) + 1000;//产生1000-9999的随机数
            String clientId = "mqtt_For_Nj_jsq_JavaServer_" + String.valueOf(rdm);

            mqttClient = new MqttClient(ServiceConstant.MQTT_HOST, clientId, new MemoryPersistence());
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            mqttClient.connect(connOpts);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }
}
