package com.paisiwater.service.msg;

import com.paisi.utils.EmojiFilter;
import com.paisi.utils.StrUtil;
import com.paisiwater.common.MessageSet;
import com.paisiwater.engin.WeixinServerEngin;
import com.paisiwater.execute.msg.WeixinMsgExecute;
import com.paisiwater.model.WxAccessToken;
import com.paisiwater.model.WxAppInfo;
import com.paisiwater.model.WxBindInfo;
import com.paisiwater.model.WxUserInfo;
import com.paisiwater.service.WeixinService;
import com.paisiwater.service.constant.ServiceConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by yuhaichao on 2016/11/15.
 */
@Component("waterMsgExecuteImpl")
public class WaterMsgExecuteImpl implements WeixinMsgExecute {

    @Autowired
    private WeixinService weixinService;

    private static final Logger logger = LoggerFactory.getLogger(WaterMsgExecuteImpl.class);

    public String subscribeMsg(Map<String, String> requestMap) {
        try {
            String fromUserName = requestMap.get("FromUserName");
            String toUserName = requestMap.get("ToUserName");
            logger.info("subcribeMsg fromUserName:" + fromUserName + ",toUserName:" + toUserName);
            WxAppInfo wxAppInfo = new WxAppInfo();
            wxAppInfo.setGhId(toUserName);
            WxAppInfo wxAppInfoReponse = weixinService.getWxAppInfo(wxAppInfo);

            try {
                WxAccessToken wxAccessToken = weixinService.getAccessToken(wxAppInfoReponse.getAppId());
                Map<String, Object> userInfoMap = WeixinServerEngin.getWxUserInfo(fromUserName, wxAccessToken.getAccessToken());
                if (userInfoMap != null && userInfoMap.get("nickname") != null) {
                    WxUserInfo wxUserInfo = new WxUserInfo();
                    wxUserInfo.setOpenid(StrUtil.objectToString(userInfoMap.get("openid")));
                    wxUserInfo.setUnionid(StrUtil.objectToString(userInfoMap.get("unionid")));
                    wxUserInfo.setGhId(toUserName);
                    wxUserInfo.setCity(StrUtil.objectToString(userInfoMap.get("city")));
                    wxUserInfo.setCountry(StrUtil.objectToString(userInfoMap.get("country")));
                    wxUserInfo.setHeadimgurl(StrUtil.objectToString(userInfoMap.get("headimgurl")));
                    wxUserInfo.setNickname(EmojiFilter.filterEmoji(StrUtil.objectToString(userInfoMap.get("nickname"))));
                    wxUserInfo.setProvince(StrUtil.objectToString(userInfoMap.get("province")));
                    wxUserInfo.setSex(StrUtil.objectToString(userInfoMap.get("sex")));
                    weixinService.saveWxUserInfo(wxUserInfo);
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("userInfo error:" + e);
            }
            return MessageSet.sendTextMessage(fromUserName, toUserName, wxAppInfoReponse.getGzMsg());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "";
    }

    public String unsubscribeMsg(Map<String, String> requestMap) {
        try {
            String fromUserName = requestMap.get("FromUserName");
            String toUserName = requestMap.get("ToUserName");
            logger.info("####unsubscribeMsg#### fromUserName:" + fromUserName + ", toUsername:" + toUserName);
            WxAppInfo wxAppInfo = new WxAppInfo();
            wxAppInfo.setGhId(toUserName);
            WxAppInfo wxAppInfoReponse = weixinService.getWxAppInfo(wxAppInfo);
            String appId = wxAppInfoReponse.getAppId();
            WxBindInfo wxBindInfo = new WxBindInfo();
            wxBindInfo.setAppId(appId);
            wxBindInfo.setOpenid(fromUserName);
            weixinService.unbindUserAllDevice(wxBindInfo);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "";
    }

    public String bindMsg(Map<String, String> requestMap) {
        try {
            String fromUserName = requestMap.get("FromUserName");
            String toUserName = requestMap.get("ToUserName");
            String deviceIdStr = requestMap.get("DeviceID");
            String[] deviceArr = deviceIdStr.split("_");
            logger.info("####bindMsg#### deviceId:" + deviceIdStr);
            if (deviceArr.length == 2) {
                WxAppInfo wxAppInfo = new WxAppInfo();
                wxAppInfo.setGhId(toUserName);
                WxAppInfo wxAppInfoReponse = weixinService.getWxAppInfo(wxAppInfo);
                String appId = wxAppInfoReponse.getAppId();
                String deviceId = deviceIdStr;
                String deviceType = deviceArr[1];
                WxBindInfo wxBindInfo = new WxBindInfo();
                wxBindInfo.setAppId(appId);
                wxBindInfo.setDeviceId(deviceId);
                wxBindInfo.setDeviceType(deviceType);
                wxBindInfo.setOpenid(fromUserName);
                wxBindInfo.setDeviceName(deviceId);
                wxBindInfo.setStatu(ServiceConstant.BIND_STATU);
                weixinService.saveWxBindInfo(wxBindInfo);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "";
    }

    public String unbindMsg(Map<String, String> requestMap) {
        try {
            String fromUserName = requestMap.get("FromUserName");
            String deviceIdStr = requestMap.get("DeviceID");
            String[] deviceArr = deviceIdStr.split("_");
            logger.info("deviceArr : " + deviceArr);
            logger.info("####unbindMsg#### , deviceID:" + deviceIdStr + ", openId:" + fromUserName);
            if (deviceArr.length == 2) {
                logger.info("=========updateWxBindStat========");
                String deviceId = deviceArr[0];
                WxBindInfo wxBindInfo = new WxBindInfo();
//                wxBindInfo.setDeviceId(deviceId);
                wxBindInfo.setDeviceId(deviceIdStr);
                wxBindInfo.setOpenid(fromUserName);
                wxBindInfo.setStatu(ServiceConstant.UNBIND_STATU);
                weixinService.updateWxBindStat(wxBindInfo);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "";
    }
}
