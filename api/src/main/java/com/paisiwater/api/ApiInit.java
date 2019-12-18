package com.paisiwater.api;

import com.paisiwater.service.GeneralScanService;
import com.paisiwater.service.WeixinService;
import com.paisiwater.service.mqtt.MqttService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by renrui on 2018/1/10.
 */
@Component
public class ApiInit {

    private static final Logger logger = LoggerFactory.getLogger(ApiInit.class);

    @Autowired
    private WeixinService weixinService;

    @Autowired
    private MqttService mqttService;

    @Autowired
    private GeneralScanService generalScanService;

    @PostConstruct
    public void init() {
        logger.info("init api ..");
        try {
            mqttService.start();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    @Scheduled(fixedRate = 1000 * 60 * 110)
    public void myFixedrate() {
//        String localStr = "";
//        try {
//            InetAddress localAddr = InetAddress.getLocalHost();
//            localStr = localAddr.getHostAddress();
//            logger.info("localAddr: " + localAddr);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (WeixinConstant.ACCESS_TOKEN_IP.equals(localStr)) {
        try {
            weixinService.initWxAccessToken();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
//        }
    }

    @Scheduled(fixedRate = 1000 * 60 * 112)
    public void updateTicket() {
        String localStr = "";
//        try {
//            InetAddress localAddr = InetAddress.getLocalHost();
//            localStr = localAddr.getHostAddress();
//            logger.info("localAddr: " + localAddr);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (WeixinConstant.ACCESS_TOKEN_IP.equals(localStr)) {
        try {
            weixinService.initWxJsApiTicket();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
//        }
    }

//    @Scheduled(cron = "0/15 * * * * ?")//每15秒
//    @Scheduled(cron = "0 15 10 ? * *")//每天上午10点
    public void updateGeneralNotify() {
        try {
            generalScanService.scanGeneralDeviceInfo();
        } catch (Exception e){
            logger.error(e.getMessage());
        }
    }


}
