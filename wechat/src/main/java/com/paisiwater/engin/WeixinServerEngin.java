package com.paisiwater.engin;

import com.alibaba.fastjson.JSON;
import com.paisi.utils.HttpClientUtil;
import com.paisi.utils.StrUtil;
import com.paisiwater.constant.WeixinConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuhaichao on 2016/11/16.
 */
public class WeixinServerEngin {

    private static final Logger logger = LoggerFactory.getLogger(WeixinServerEngin.class);

    /**
     * 微信设备授权
     *
     * @param deviceId
     * @param mac
     * @param accessToken
     * @return
     */
    public static Map<String, Object> authorizeDevice(String deviceId, String mac, String productId, String accessToken) {
        try {
            String url = "https://api.weixin.qq.com/device/authorize_device?access_token=" + accessToken;

            logger.info("url:" + url);
            List<Map<String, Object>> deviceList = new ArrayList<Map<String, Object>>();

            Map<String, Object> deviceMap = new HashMap<String, Object>();
            deviceMap.put("id", deviceId);
            deviceMap.put("mac", mac);
            deviceMap.put("connect_protocol", "4");
            deviceMap.put("auth_key", "1234567890ABCDEF1234567890ABCDEF");
            deviceMap.put("close_strategy", "1");
            deviceMap.put("conn_strategy", "1");
            deviceMap.put("crypt_method", "0");
            deviceMap.put("auth_ver", "0");
            deviceMap.put("manu_mac_pos", "-1");
            deviceMap.put("ser_mac_pos", "-1");

            deviceList.add(deviceMap);

            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("device_num", "1");
            dataMap.put("device_list", deviceList);
            dataMap.put("op_type", "0");
            dataMap.put("product_id", productId);
            String postData = JSON.toJSONString(dataMap);
            logger.info("authorizeDevice post data:" + postData);
            String returnStr = HttpClientUtil.httpsRequest(url, WeixinConstant.POST, postData);
            logger.info("returnStr:" + returnStr);
            if (returnStr != null && !"".equals(returnStr)) {
                Map<String, Object> returnMap = (Map<String, Object>) JSON.parse(returnStr);
                return returnMap;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public static Map<String, Object> getAuthStat(String deviceId, String accessToken) {
        try {
            String url = "https://api.weixin.qq.com/device/get_stat?access_token=" + accessToken + "&device_id=" + deviceId;
            logger.info("url:" + url);
            String returnStr = HttpClientUtil.httpsRequest(url, WeixinConstant.GET, null);
            logger.info("returnStr:" + returnStr);
            if (returnStr != null && !"".equals(returnStr)) {
                Map<String, Object> returnMap = (Map<String, Object>) JSON.parse(returnStr);
                return returnMap;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 获取微信AccessToken
     *
     * @param appId
     * @param appSecret
     * @return
     */
    public static Map<String, Object> getWxAccessToken(String appId, String appSecret) {
        try {
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret;
            String returnStr = HttpClientUtil.httpsRequest(url, WeixinConstant.GET, null);
            logger.info("token result: " + returnStr);
            if (returnStr != null && !"".equals(returnStr)) {
                Map<String, Object> returnMap = (Map<String, Object>) JSON.parse(returnStr);
                return returnMap;
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return null;
    }

    /**
     * 获取JsApiTicket
     *
     * @param accessToken
     * @return
     */
    public static Map<String, Object> getWxJsApiTicket(String accessToken) {
        try {
            String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi";
            String returnStr = HttpClientUtil.httpsRequest(url, WeixinConstant.GET, null);
            if (returnStr != null && !"".equals(returnStr)) {
                Map<String, Object> returnMap = (Map<String, Object>) JSON.parse(returnStr);
                return returnMap;
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return null;
    }

    /**
     * 获取微信用户信息
     *
     * @param openid
     * @param accessToken
     * @return
     */
    public static Map<String, Object> getWxUserInfo(String openid, String accessToken) {
        try {
            String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken + "&openid=" + openid + "&lang=zh_CN";
            String returnStr = HttpClientUtil.httpsRequest(url, "GET", null);
            if (returnStr != null && !"".equals(returnStr)) {
                Map<String, Object> returnMap = (Map<String, Object>) JSON.parse(returnStr);
                returnMap.put("tagid_list", "");
                return returnMap;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public static String createWxMenu(String accessToken, String menuData) {
        try {
            String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken;
            String returnStr = HttpClientUtil.httpsRequest(url, "POST", menuData);
            return returnStr;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public static Map<Object, Object> getOauthUserId(String code, String appId, String appSecret) {
        try {
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + appSecret + "&code=" + code + "&grant_type=authorization_code";
            String result = HttpClientUtil.httpsRequest(url, "GET", null);
            if (result != null && !"".equals(result)) {
                Map<Object, Object> map = (Map<Object, Object>) JSON.parse(result);
                return map;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;

    }


    public static Map<String, String> sendTemplateMsg(String token, String info) {
        try {
            String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + token;
            String result = HttpClientUtil.httpsRequest(url, "POST", info);
            logger.info("template msg: " + result);
            if (result != null && !"".equals(result)) {
                Map<String, String> map = (Map<String, String>) JSON.parse(result);
                return map;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }


    public static Map<String, String> sendCustommsg(String token, String msg) {
        logger.info("reply msg: " + msg);
        try {
            String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token;
            String result = HttpClientUtil.httpsRequest(url, "POST", msg);
            if (result != null && !"".equals(result)) {
                Map<String, String> map = (Map<String, String>) JSON.parse(result);
                return map;
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public static String getMaterials(String token, String info) {
        logger.info("=======getMaterials========");
        try {
            String url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=" + token;
            String result = HttpClientUtil.httpsRequest(url, "POST", info);
            logger.info("result: " + result);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return "";
    }

    public static Map<String, String> getWxPageAccessToken(String appId, String appSecret, String wxCode) {
        try {
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + appSecret + "&code=" + wxCode + "&grant_type=authorization_code";
            String result = HttpClientUtil.httpsRequest(url, "POST", null);
            logger.info("Wx page Auth accessToken=" + result);
            if (result != null && !"".equals(result)) {
                Map<String, String> map = (Map<String, String>) JSON.parse(result);
                return map;
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return null;
    }

    public static boolean isValidAccess(String access, String openId) {
        try {
            String url = "https://api.weixin.qq.com/sns/auth?access_token=" + access + "&openid=" + openId;
            String result = HttpClientUtil.httpsRequest(url, "POST", null);
            if (result != null && !"".equals(result)) {
                Map<String, String> map = (Map<String, String>) JSON.parse(result);
                if ("0".equals(StrUtil.objectToString(map.get("errcode")))) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public static Map<String, String> refreshWxPageAccessToken(String appId) {
        try {
            String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + appId + "&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
            String result = HttpClientUtil.httpsRequest(url, "POST", null);
            if (result != null && !"".equals(result)) {
                Map<String, String> map = (Map<String, String>) JSON.parse(result);
                return map;
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return null;
    }

    public static Map<String, Object> getWxPageAuthInfo(String accessToken, String openId) {
        try {
            String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId + "&lang=zh_CN";
            String result = HttpClientUtil.httpsRequest(url, "GET", null);
            if (result != null && !"".equals(result)) {
                Map<String, Object> map = (Map<String, Object>) JSON.parse(result);
                return map;
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return null;
    }
}
