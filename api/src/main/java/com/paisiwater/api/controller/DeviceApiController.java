package com.paisiwater.api.controller;

import com.alibaba.fastjson.JSON;
import com.paisi.utils.ShaUtil;
import com.paisiwater.api.controller.constant.ApiErrorCode;
import com.paisiwater.api.controller.constant.WeixinConstant;
import com.paisiwater.api.model.*;
import com.paisiwater.execute.msg.WeixinMsgExecute;
import com.paisiwater.handler.BaseDataProcess;
import com.paisiwater.handler.WaterDataProcess;
import com.paisiwater.model.WxBindInfo;
import com.paisiwater.service.WeixinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by renrui on 2016/11/15.
 */
@RestController
@RequestMapping("wechat")
public class DeviceApiController {
    private static final Logger logger = LoggerFactory.getLogger(DeviceApiController.class);

    @Resource(name = "waterMsgExecuteImpl")
    private WeixinMsgExecute weixinMsgExecute;

    @Autowired
    private WeixinService weixinService;

    @RequestMapping("test")
    public String test(String abc) {
        return "test";
    }

    /**
     * 微信接入授权验证
     *
     * @return
     */
    @RequestMapping(value = "weixinToken", method = RequestMethod.GET)
    public String weixinToken(String signature, String timestamp, String nonce, String echostr) {
        try {
            BaseDataProcess dataProcess = new WaterDataProcess();
            return dataProcess.token(signature, timestamp, nonce, echostr, WeixinConstant.TOKEN);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "";
    }

    /**
     * 微信消息处理
     *
     * @return
     */
    @RequestMapping(value = "weixinToken", method = RequestMethod.POST)
    public String weixinToken(@RequestBody String requestMsg) {
        try {
            BaseDataProcess dataProcess = new WaterDataProcess();
            return dataProcess.processPost(requestMsg, weixinMsgExecute);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "";
    }

    @RequestMapping("authorizeDevice")
    public ReturnCode authorizeDevice(@RequestBody AuthDevice authDevice) {
        logger.info("authorizeDevice, deviceId is : " + authDevice.getDeviceId());
        ReturnCode returnCode = new ReturnCode();
        returnCode.setErrorCode(ApiErrorCode.FAIL_CODE);
        try {
            if (authDevice != null) {
                String token = authDevice.getToken();
                logger.info("client token:" + token);
                StringBuilder content = new StringBuilder();
                content.append(authDevice.getDeviceId());
                content.append(authDevice.getMac());
                content.append(authDevice.getProductId());
                content.append(authDevice.getAppId());
                content.append(WeixinConstant.AUTH_KEY);

                String serverToken = ShaUtil.stringSHA1(content.toString());

                if (serverToken != null && serverToken.equals(token)) {
                    boolean auth = weixinService.authorizeDevice(authDevice.getDeviceId(), authDevice.getMac(), authDevice.getProductId(), authDevice.getAppId());

                    if (auth) {
                        returnCode.setErrorCode(ApiErrorCode.SUCCESS_CODE);
                    }
                } else {
                    returnCode.setErrorCode(ApiErrorCode.AUTH_CODE);
                }

            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return returnCode;
    }


    /**
     * 设备信息注册
     *
     * @param deviceInfo
     * @return
     */
    @RequestMapping("deviceRegister")
    public ReturnCodeTime deviceRegister(@RequestBody DeviceInfo deviceInfo, HttpServletRequest request) {
        logger.info("======deviceRegister======");
        ReturnCodeTime returnCode = new ReturnCodeTime();
        returnCode.setErrorCode(ApiErrorCode.FAIL_CODE);
        returnCode.setCurrentTime(System.currentTimeMillis() / 1000);
        String province = null;
        String city = null;
        String region = null;
        try {
            if (deviceInfo != null) {
                String deviceId = deviceInfo.getDeviceId();
                logger.info("Register device Id:" + deviceId);
                String[] deviceIdArr = deviceId.split("_");
                if (deviceIdArr.length != 2) {
                    returnCode.setErrorCode(ApiErrorCode.FAIL_CODE);
                    return returnCode;
                } else {
                    deviceInfo.setDeviceType(deviceIdArr[1]);
                }

                String token = deviceInfo.getToken();
                StringBuilder content = new StringBuilder();
                content.append(deviceInfo.getDeviceId());
                content.append(deviceInfo.getAppId());
                content.append(deviceInfo.getMac());
                content.append(deviceInfo.getSeqNum());
                content.append(deviceInfo.getModel());
                content.append(deviceInfo.getChip());
                content.append(deviceInfo.getVersion());
                content.append(WeixinConstant.AUTH_KEY);

                String serverToken = ShaUtil.stringSHA1(content.toString());
                logger.info("server token: " + serverToken);

                if (serverToken != null && serverToken.equals(token)) {
                    //获取ip和城市信息
                    if (request != null) {
                        String ip = request.getHeader("X-Forwarded-For");
                        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
                            ip = request.getHeader("Proxy-Client-IP");
                        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
                            ip = request.getHeader("WL-Proxy-Client-IP");
                        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
                            ip = request.getHeader("HTTP_CLIENT_IP");
                        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
                            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
                            ip = request.getRemoteAddr();
                        if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip))
                            try {
                                ip = InetAddress.getLocalHost().getHostAddress();
                            } catch (UnknownHostException unknownhostexception) {
                            }
                        logger.info("ip:" + ip);

                        URL url = new URL("http://ip.ws.126.net/ipquery?ip=" + ip);
                        URLConnection conn = url.openConnection();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "GBK"));
                        String line = null;
                        StringBuffer result = new StringBuffer();
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }
                        reader.close();

                        ip = result.toString();

                        if (ip.contains("区")) {
                            region = ip.substring(ip.indexOf("city:") + 6, ip.lastIndexOf(",") - 1);
                            ip = result.substring(result.indexOf("province:"));
                            province = ip.substring(10, ip.lastIndexOf("\""));
                            city = province;
                        } else {
                            city = ip.substring(ip.indexOf("city:") + 6, ip.lastIndexOf(",") - 1);
                            ip = result.substring(result.indexOf("province:"));
                            province = ip.substring(10, ip.lastIndexOf("\""));
                        }

                        deviceInfo.setProvince(province);
                        deviceInfo.setCity(city);
                        if (region != null) {
                            deviceInfo.setRegion(region);
                        }
                    }
                    weixinService.saveDeviceInfo(deviceInfo);
                    logger.info("#####register success######");
                    returnCode.setErrorCode(ApiErrorCode.SUCCESS_CODE);
                } else {
                    returnCode.setErrorCode(ApiErrorCode.AUTH_CODE);
                }

            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return returnCode;

    }

    /**
     * 设备净水数据统计
     *
     * @param deviceDataStat
     * @return
     */
    @RequestMapping("clientDataStat")
    public ReturnCode clientDataStat(@RequestBody DeviceDataStat deviceDataStat) {
        ReturnCode returnCode = new ReturnCode();
        returnCode.setErrorCode(ApiErrorCode.FAIL_CODE);
        try {
            if (deviceDataStat != null) {

                String token = deviceDataStat.getToken();
                logger.info("data stat client token:" + token);
                StringBuilder content = new StringBuilder();
                content.append(deviceDataStat.getDeviceId());
                content.append(deviceDataStat.getYsTds());
                content.append(deviceDataStat.getCsTds());
                content.append(deviceDataStat.getJsTotal());
                content.append(deviceDataStat.getJhsSize());
                content.append(deviceDataStat.getCjsSize());
                content.append(deviceDataStat.getYsSize());
                content.append(deviceDataStat.getZsTime());
                content.append(WeixinConstant.AUTH_KEY);

                String serverToken = ShaUtil.stringSHA1(content.toString());
                logger.info("data stat server token:" + serverToken);

                if (serverToken != null && serverToken.equals(token)) {
                    weixinService.saveDeviceDataStat(deviceDataStat);
                    returnCode.setErrorCode(ApiErrorCode.SUCCESS_CODE);
                } else {
                    returnCode.setErrorCode(ApiErrorCode.AUTH_CODE);
                }

            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return returnCode;

    }

    @RequestMapping("getServerTimestamp")
    public Map<String, Long> getServerTimestamp() {
        logger.info("getServerTimestamp");
        try {
            Map<String, Long> map = new HashMap<String, Long>();
            map.put("currentTimestamp", System.currentTimeMillis() / 1000);
            return map;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @RequestMapping(value = "getDeviceInfo", method = RequestMethod.GET)
    public String getDeviceInfo(@RequestParam String openId) {
        logger.info("getDeviceInfo");
        try {
            if ("".equals(openId) || openId != null) {
                WxBindInfo wxBindInfo = new WxBindInfo();
                wxBindInfo.setOpenid(openId);
                wxBindInfo.setAppId("wx9b5c8824203f4718");

                List<WxBindInfo> bindInfos = weixinService.getWxBindInfo(wxBindInfo);
                List<DeviceAllInfo> deviceAllInfos = new ArrayList<DeviceAllInfo>();
                DeviceInfo deviceInfo;
                for (WxBindInfo bindInfo : bindInfos) {
                    DeviceAllInfo deviceAllInfo = new DeviceAllInfo();
                    deviceAllInfo.setDeviceId(bindInfo.getDeviceId());
                    deviceAllInfo.setDeviceType(bindInfo.getDeviceType());
                    deviceAllInfo.setDeviceName(bindInfo.getDeviceName());
                    deviceInfo = weixinService.getDeviceInfo(bindInfo.getDeviceId());
                    deviceAllInfo.setSeqNum(deviceInfo.getSeqNum());
                    deviceAllInfo.setModel(deviceInfo.getModel());
                    deviceAllInfo.setVersion(deviceInfo.getVersion());
                    deviceAllInfo.setRegisterTime(deviceInfo.getRegisterTime());
                    deviceAllInfo.setCity(deviceInfo.getCity());
                    deviceAllInfo.setProvince(deviceInfo.getProvince());
                    deviceAllInfo.setRegion(deviceInfo.getRegion());
                    deviceAllInfos.add(deviceAllInfo);
                }
                String retStr = JSON.toJSONString(deviceAllInfos);
                logger.info("return str=" + retStr);
                return retStr;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "error";
    }

}
