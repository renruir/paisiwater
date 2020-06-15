package com.paisiwater.service.constant;

/**
 * Created by yuhaichao on 2016/11/16.
 */
public interface ServiceConstant {
	String UNBIND_STATU = "1";

	String BIND_STATU = "0";

	String WX_DOMAIN = "https://wx.mypraise.cn/";

	String appId = "wxf9c52bdadc627711";

//	final String MQTT_HOST = "ws://conn.doubimeizhi.com:12901";
	//String MQTT_HOST = "ssl://tjnwater.mqtt.iot.bj.baidubce.com:1884";
//	String MQTT_HOST = "ssl://tjnwater.mqtt.iot.gz.baidubce.com:1884";
	String MQTT_HOST = "ws://wx.mypraise.cn:61623";

	//String username = "tjnwater/jindian";
	String username = "admin";

	//String password = "ufO80F5sSyin4ArSurBcPJOeeCwK9eA2IR2pHOX3+nY=";
	String password = "password";

	String QQ_MAP_KEY = "XIQBZ-H2OHF-EXIJR-NIRX5-62OQJ-WUFJI";

	//模板消息model id
	String FILTER_NOTICE_MODEL_ID = "vm57vp42bcICgttmdED-Vm581j6p3cip8h4rQenksrY";

	String FAULT_NOTICE_MODEL_ID = "qHuvejgurEt9733Og1qY5Z1HRPd05ox2AzPKNAstw60";

	String FILTER_RINSE_NOTICE_MODEL_ID = "sCIMXsUwr6kS6xFPkB7m-SN37LTOuRqzrZzbn23nsLk";

	String[] filterInfo = new String[]{"PP精密纤维", "CTO活性炭", "RO膜600GPD", "后置活性炭"};

	int MODEL_MSG_FLAG_FILTER = 7001;
	int MODEL_MSG_FLAG_FAULT = 7002;

	String DEVICE_FAULT_1 = "电磁阀K1故障";
	String DEVICE_FAULT_2 = "电磁阀K2故障";
	String DEVICE_FAULT_3 = "增压泵K3故障";
	String DEVICE_FAULT_VALVE_3 = "电磁阀K3故障";
	String DEVICE_FAULT_VALVE_4 = "电磁阀K4故障";
	String DEVICE_FAULT_VALVE_5 = "电磁阀K5故障";
	String DEVICE_FAULT_9 = "高压开关故障或忘记关水";
	String DEVICE_FAULT_10 = "净水器漏水";
	String DEVICE_FAULT_11 = "逆止阀漏水或水龙头未关紧";
	String DEVICE_DEFAULT_FAULT = "未知故障";

}
