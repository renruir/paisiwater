package com.paisiwater.controller;

import com.alibaba.fastjson.JSON;
import com.paisiwater.api.BaseButton;
import com.paisiwater.api.SubButton;
import com.paisiwater.api.UrlMenu;
import com.paisiwater.api.model.*;
import com.paisi.utils.CookieUtil;
import com.paisi.utils.ShaUtil;
import com.paisi.utils.StrUtil;
import com.paisiwater.constant.WeixinConstant;
import com.paisiwater.engin.WeixinServerEngin;
import com.paisi.utils.HttpClientUtil;
import com.paisiwater.model.*;
import com.paisiwater.service.WeixinService;
import com.paisiwater.service.constant.ServiceConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
@RequestMapping("wechat")
public class WechatWebController {

    private static final Logger logger = LoggerFactory.getLogger(WechatWebController.class);

    private static final String JSQ_GH_ID = "gh_4151d4ce24c0";

    private static final String JSQ_DEVICE_TYPE = "1";

    private static final String JHQ_DEVICE_TYPE = "2";

//    private static final String MQTT_HOST = "conn.doubimeizhi.com";
//
//    private static final String MQTT_PORT = "12901";

    //private static final String MQTT_HOST = "tjnwater.mqtt.iot.bj.baidubce.com";
    private static final String MQTT_HOST = "wx.mypraise.cn";

    private static final String MQTT_PORT = "61623";

    private static final Integer OneDay_seconds = 24 * 3600 * 1000;

    private static final Integer TwoWeek_seconds = 2 * 7 * OneDay_seconds;

    private static final String noncestr = "Gde5feWzYTdfine6z0wh6hYzfW";

    @Autowired
    private WeixinService weixinService;

    @RequestMapping(value = "createJsqMenu")
    @ResponseBody
    public String createJsqMenu() {
        try {
            String appId = "wxf9c52bdadc627711";
            WxAccessToken wxAccessToken = weixinService.getAccessToken(appId);
            BaseButton button = new BaseButton();

            UrlMenu menu1000 = new UrlMenu();
            menu1000.setName("关于派斯");
            menu1000.setType("view");
            menu1000.setUrl("https://mp.weixin.qq.com/s/Oc5xd_wM9y5BMb0Z6drAFQ");

            UrlMenu menu1001 = new UrlMenu();
            menu1001.setName("京东旗舰店");
            menu1001.setType("view");
            menu1001.setUrl("https://shop.m.jd.com/?shopId=36858&utm_source=iosapp&utm_medium=appshare&utm_campaign=t_335139774&utm_term=CopyURL&ad_od=share");

            UrlMenu menu1002 = new UrlMenu();
            menu1002.setName("有盐软水安装操作");
            menu1002.setType("view");
            menu1002.setUrl("http://mp.weixin.qq.com/s?__biz=MzIzMTc0OTcyMw==&mid=100000018&idx=1&sn=081c05f6711b67b6947bcc6fe1c5a915&chksm=689e20265fe9a930d64d325f4dca28ee90440df4d5f0e1e54ae3a6aad402f44bbdf36583d86e&scene=18#wechat_redirect");

            List<Object> list10 = new ArrayList<Object>();
            list10.add(menu1000);
            list10.add(menu1001);
            list10.add(menu1002);

            SubButton subButton10 = new SubButton();
            //subButton10.setName("结缘特洁恩");
            subButton10.setName("关于派斯");
            subButton10.setSub_button(list10);

            UrlMenu menu2002 = new UrlMenu();
            menu2002.setName("全自动前置过滤器");
            menu2002.setType("view");
            menu2002.setUrl("https://e.eqxiu.com/s/BIo3A2VM");

            UrlMenu menu2003 = new UrlMenu();
            menu2003.setName("RO-500双出水机");
            menu2003.setType("view");
            menu2003.setUrl("https://x.eqxiu.com/s/X8b0W1uN");

            UrlMenu menu2004 = new UrlMenu();
            menu2004.setName("派斯全屋净水器");
            menu2004.setType("view");
            menu2004.setUrl("http://h5.eqxiu.com/ls/ECts8HcZ");
            //menu2004.setUrl("https://h5.youzan.com/v2/showcase/mpnews?alias=25jhkqop");

            UrlMenu menu2005 = new UrlMenu();
            menu2005.setName("金滤媒厨下净水器");
            menu2005.setType("view");
            menu2005.setUrl("http://h5.eqxiu.com/ls/olC7Spdg");

            UrlMenu menu2006 = new UrlMenu();
            menu2006.setName("SSP阻垢软水机");
            menu2006.setType("view");
            menu2006.setUrl("http://h5.eqxiu.com/ls/mQXjjCeI");

            List<Object> list20 = new ArrayList<Object>();
            list20.add(menu2002);
            list20.add(menu2003);
            list20.add(menu2004);
            list20.add(menu2005);
            list20.add(menu2006);

            SubButton subButton20 = new SubButton();
            //subButton20.setName("官方商城");
            subButton20.setName("派斯产品");
            subButton20.setSub_button(list20);

            UrlMenu menu3001 = new UrlMenu();
            //menu3001.setName("会员中心");
            menu3001.setName("会员绑定");
            menu3001.setType("view");
            menu3001.setUrl("http://wxcrm.me/wxcrm-mobile/wx013a0b0fac979a5e/index.html#/member/center");
            //menu3001.setUrl("http://vipportal.ecp100.com/61459fc0-af81-11e5-b6b1-00163e02034c/login?returnurl=http%253a%252f%252fvipportal.ecp100.com%252f61459fc0-af81-11e5-b6b1-00163e02034c%252fhome");

            UrlMenu menu3002 = new UrlMenu();
            menu3002.setName("订单查询");
            menu3002.setType("view");
            menu3002.setUrl("http://weixin.tjnwater.com/web/order/search_by_order.html?v=1.5");

            UrlMenu menu3003 = new UrlMenu();
            menu3003.setName("安装维修");
            menu3003.setType("view");
            menu3003.setUrl("http://weixin.tjnwater.com/web/order/install_repair.html?v=1.5");

            UrlMenu menu3004 = new UrlMenu();
            menu3004.setName("常见问题");
            menu3004.setType("view");
            menu3004.setUrl("http://mp.weixin.qq.com/mp/homepage?__biz=MzA3NzQzOTkzMg==&hid=10&sn=e75356a2dd7e1bf81c461101a83fe8bc#wechat_redirect");

            UrlMenu menu3005 = new UrlMenu();
            menu3005.setName("我的设备");
            menu3005.setType("view");
            menu3005.setUrl("http://wx.mypraise.cn/api/wechat/index.html");

            List<Object> list30 = new ArrayList<Object>();
//            list30.add(menu3001);
//            list30.add(menu3002);
//            list30.add(menu3003);
//            list30.add(menu3004);
            list30.add(menu3005);

            SubButton subButton30 = new SubButton();
            //subButton30.setName("自助服务");
            subButton30.setName("客户服务");
            subButton30.setSub_button(list30);


            List<Object> buttonList = new ArrayList<Object>();
            buttonList.add(subButton10);
            buttonList.add(subButton20);
            buttonList.add(subButton30);

            button.setButton(buttonList);

            String menuData = JSON.toJSONString(button);
            String returnStr = WeixinServerEngin.createWxMenu(wxAccessToken.getAccessToken(), menuData);
            return returnStr;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @RequestMapping(value = "index.html")
    public String index(HttpServletRequest request, HttpServletResponse response, String code, Model model) {
        try {
            WxAppInfo wxAppInfo = new WxAppInfo();
            wxAppInfo.setGhId(JSQ_GH_ID);
            wxAppInfo = weixinService.getWxAppInfo(wxAppInfo);
            String appId = wxAppInfo.getAppId();
            String appSecret = wxAppInfo.getAppSecret();
            logger.info("appId："+appId);
            String cookieUid = CookieUtil.getCookie(appId + "_uid", request);
            logger.info("cookieUid = " + cookieUid);
            if (cookieUid != null && !"".equals(cookieUid)) {
                CookieUtil.setCookie(appId + "_uid", cookieUid, response);
            } else if (code != null && !"".equals(code)) {
                Map<Object, Object> openidMap = WeixinServerEngin.getOauthUserId(code, appId, appSecret);
                if (openidMap != null) {
                    cookieUid = StrUtil.objectToString(openidMap.get("openid"));

                    if (cookieUid != null && !"".equals(cookieUid)) {
                        CookieUtil.setCookie(appId + "_uid", cookieUid, response);
                    }
                }
            } else {
                String homeUrl = ServiceConstant.WX_DOMAIN + "api/wechat/index.html";
                try {
                    homeUrl = URLEncoder.encode(homeUrl, "utf-8");
                } catch (UnsupportedEncodingException e) {
                }
                String homeMenuUrl = WeixinConstant.OAUTH_URL;
                homeMenuUrl = homeMenuUrl.replace("${appid}", appId);
                homeMenuUrl = homeMenuUrl.replace("${redirect_uri}", homeUrl);
                logger.info("redirect url: " + homeMenuUrl);
                return "redirect:" + homeMenuUrl;
            }

            if (StrUtil.strIsNotNull(cookieUid)) {
                Map<String, String> serverInfo = new HashMap<String, String>();
                serverInfo.put("host", MQTT_HOST);
                serverInfo.put("port", MQTT_PORT);
                serverInfo.put("appId", appId);
//                serverInfo.put("openId", cookieUid);
                model.addAttribute("serverInfo", serverInfo);
                return "index";
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @RequestMapping(value = "test_index.html")
    public String testIndex(HttpServletRequest request, HttpServletResponse response, String code, Model model) {
        try {
            WxAppInfo wxAppInfo = new WxAppInfo();
            wxAppInfo.setGhId(JSQ_GH_ID);
            wxAppInfo = weixinService.getWxAppInfo(wxAppInfo);
            String appId = wxAppInfo.getAppId();
            String appSecret = wxAppInfo.getAppSecret();
            String cookieUid = "oW5agwLRWH0kW-zuuOt0cbnQwhOs";
            logger.info("cookieUid: " + cookieUid);
            if (cookieUid != null && !"".equals(cookieUid)) {
                CookieUtil.setCookie(appId + "_uid", cookieUid, response);
            } else if (code != null && !"".equals(code)) {
                Map<Object, Object> openidMap = WeixinServerEngin.getOauthUserId(code, appId, appSecret);
                if (openidMap != null) {
                    cookieUid = StrUtil.objectToString(openidMap.get("openid"));

                    if (cookieUid != null && !"".equals(cookieUid)) {
                        CookieUtil.setCookie(appId + "_uid", cookieUid, response);
                    }
                }
            }
            if (StrUtil.strIsNotNull(cookieUid)) {
                Map<String, String> serverInfo = new HashMap<String, String>();
                serverInfo.put("host", MQTT_HOST);
                serverInfo.put("port", MQTT_PORT);
                serverInfo.put("appId", appId);
//                serverInfo.put("openId", cookieUid);
                model.addAttribute("serverInfo", serverInfo);
                return "index";
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }


    @RequestMapping(value = "my_devices.html")
    public String myDevices(HttpServletRequest request, HttpServletResponse response, String code, Model model) {
        try {
            DeviceVersionUpdate updateDeviceInfo = new DeviceVersionUpdate();
            String deviceId = request.getParameter("deviceId");
            String deviceType = request.getParameter("type");
            String update = request.getParameter("update");
            if (!"".equals(update) && update != null) {
                updateDeviceInfo.setVersion(request.getParameter("version"));
                updateDeviceInfo.setDeviceType(deviceType);
                updateDeviceInfo.setDownloadUrl(request.getParameter("url"));
                updateDeviceInfo.setPkgSize(request.getParameter("size"));
                updateDeviceInfo.setMd5(request.getParameter("md5"));

            }
            WxAppInfo wxAppInfo = new WxAppInfo();
            wxAppInfo.setGhId(JSQ_GH_ID);
            wxAppInfo = weixinService.getWxAppInfo(wxAppInfo);
            String appId = wxAppInfo.getAppId();
            String appSecret = wxAppInfo.getAppSecret();
            String cookieUid = CookieUtil.getCookie(appId + "_uid", request);
            WxJsApiTicket wxTicket = weixinService.getJsApiTicket(appId);
            logger.info("my_devices cookieUid: " + cookieUid);
            if (cookieUid != null && !"".equals(cookieUid)) {
                CookieUtil.setCookie(appId + "_uid", cookieUid, response);
            } else if (code != null && !"".equals(code)) {
                Map<Object, Object> openidMap = WeixinServerEngin.getOauthUserId(code, appId, appSecret);
                if (openidMap != null) {
                    cookieUid = StrUtil.objectToString(openidMap.get("openid"));

                    if (cookieUid != null && !"".equals(cookieUid)) {
                        CookieUtil.setCookie(appId + "_uid", cookieUid, response);
                    }
                }
            } else {
                String homeUrl = ServiceConstant.WX_DOMAIN + "web/wechat/index.html";
                try {
                    homeUrl = URLEncoder.encode(homeUrl, "utf-8");
                } catch (UnsupportedEncodingException e) {
                }
                String homeMenuUrl = WeixinConstant.OAUTH_URL;
                homeMenuUrl = homeMenuUrl.replace("${appid}", appId);
                homeMenuUrl = homeMenuUrl.replace("${redirect_uri}", homeUrl);
//                logger.info("redirect url: " + homeMenuUrl);
                return "redirect:" + homeMenuUrl;
            }

            if (StrUtil.strIsNotNull(cookieUid)) {
                logger.info("cookieUid: " + cookieUid);
                WxBindInfo wxBindInfo = new WxBindInfo();
                wxBindInfo.setOpenid(cookieUid);
                wxBindInfo.setAppId(appId);
                wxBindInfo.setDeviceId(deviceId);
                wxBindInfo.setDeviceType(deviceType);

                wxBindInfo = weixinService.getWxBindInfoByDevice(wxBindInfo);
                logger.info("wxbindInfo: type= " + wxBindInfo.getDeviceType() + ", deviceid=" + wxBindInfo.getDeviceId() +
                        ", deviceName=" + wxBindInfo.getDeviceName());
                DeviceInfo deviceInfo;

                List<FilterInfo> filterInfos = null;

                if (wxBindInfo != null && StrUtil.strIsNotNull(wxBindInfo.getDeviceId())) {
                    deviceInfo = weixinService.getDeviceInfo(wxBindInfo.getDeviceId());
                    logger.info("device model: " + deviceInfo.getModel());
                    if ("1".equals(wxBindInfo.getDeviceType())) {
                        if (deviceInfo != null) {
                            filterInfos = weixinService.getFilterInfo(deviceInfo.getModel());
                        }
                    }

                    String url = "http://wx.mypraise.cn/web/wechat/my_devices.html?deviceId=" + deviceId + "&type=" + deviceType;
                    String signature = getSignature(url, wxTicket.getJsApiTicket());
                    model.addAttribute("wxBindInfo", wxBindInfo);
                    Map<String, String> serverInfo = new HashMap<String, String>();
                    serverInfo.put("host", MQTT_HOST);
                    serverInfo.put("port", MQTT_PORT);
                    serverInfo.put("noncestr", noncestr);
                    serverInfo.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
                    serverInfo.put("signature", signature.toLowerCase());
                    model.addAttribute("serverInfo", serverInfo);
                    model.addAttribute("deviceInfo", deviceInfo);

                    if (!"".equals(update) && update != null) {
                        model.addAttribute("updateDeviceInfo", updateDeviceInfo);
                    }
                    if ("1".equals(wxBindInfo.getDeviceType())) {
                        model.addAttribute("filterInfo", filterInfos);
                        model.addAttribute("filterRank", request.getParameter("filterRank"));
                    }

                    if ("1".equals(wxBindInfo.getDeviceType())) {
                        return "jsq_home_new";
                    } else if ("2".equals(wxBindInfo.getDeviceType())) {
                        return "air_purifier";
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @RequestMapping(value = "test_new.html")
    public String test_new(HttpServletRequest request, HttpServletResponse response, String code, Model model) {
        try {
            String deviceId = request.getParameter("deviceId");
            String deviceType = request.getParameter("type");

            WxAppInfo wxAppInfo = new WxAppInfo();
            wxAppInfo.setGhId(JSQ_GH_ID);
            wxAppInfo = weixinService.getWxAppInfo(wxAppInfo);
            String appId = "wxf9c52bdadc627711";
            String appSecret = "f67286267e092865f3b2c0374d9ef0e3";
            String cookieUid = "oW5agwLRWH0kW-zuuOt0cbnQwhOs";
            logger.info("cookieUid: " + cookieUid);
            WxBindInfo wxBindInfo = new WxBindInfo();
            wxBindInfo.setOpenid(cookieUid);
            wxBindInfo.setAppId(appId);
            wxBindInfo.setDeviceId(deviceId);
            wxBindInfo.setDeviceType(deviceType);

            wxBindInfo = weixinService.getWxBindInfoByDevice(wxBindInfo);
            model.addAttribute("updateDeviceInfo", null);
            DeviceInfo deviceInfo = null;
            List<FilterInfo> filterInfos = null;
            if (wxBindInfo != null && StrUtil.strIsNotNull(wxBindInfo.getDeviceId())) {
                deviceInfo = weixinService.getDeviceInfo(wxBindInfo.getDeviceId());
                logger.info("model: " + deviceInfo.getModel());
                if (deviceInfo != null) {
                    filterInfos = weixinService.getFilterInfo(deviceInfo.getModel());
                    for (FilterInfo info : filterInfos) {
                        logger.info("filter" + info.getGrade() + " name: " + info.getFilter_name());
                    }
                }
            }

            model.addAttribute("wxBindInfo", wxBindInfo);
            Map<String, String> serverInfo = new HashMap<String, String>();
            serverInfo.put("host", MQTT_HOST);
            serverInfo.put("port", MQTT_PORT);
            model.addAttribute("serverInfo", serverInfo);
            model.addAttribute("deviceInfo", deviceInfo);
            model.addAttribute("filterInfo", filterInfos);
            return "paisijsq";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "";
        }
    }

    @RequestMapping(value = "get_general_bind_info")
    @ResponseBody
    public List<GeneralDeviceInfo> getGeneralBindInfo(HttpServletRequest request, @RequestParam("appId") String appId, Model model) throws Exception {
        try {
            String openId = CookieUtil.getCookie(appId + "_uid", request);
//            openId = "ovAFut6Jkhz9z2a6Egmh7CVSzorM"; // for test

            List<GeneralDeviceInfo> generalDeviceInfos;
            GeneralDeviceInfo info = new GeneralDeviceInfo();
            info.setOpen_id(openId);
            info.setApp_id(appId);
            generalDeviceInfos = weixinService.getGeneralBindInfo(info);
//            logger.info("general info: " + generalDeviceInfos);
            return generalDeviceInfos;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }


    @RequestMapping(value = "get_bind_info")
    @ResponseBody
    public List<WxBindInfo> getWxBindInfo(HttpServletRequest request, @RequestParam("appId") String appId, Model model) throws Exception {

        try {
            String openId = CookieUtil.getCookie(appId + "_uid", request);
//            openId = "ovAFut6Jkhz9z2a6Egmh7CVSzorM"; // for test
            logger.info("get bind info, openID=" + openId);
            List<WxBindInfo> bindInfos;
            WxBindInfo wxBindInfo = new WxBindInfo();
            wxBindInfo.setOpenid(openId);
            wxBindInfo.setAppId(appId);

            bindInfos = weixinService.getWxBindInfo(wxBindInfo);
            return bindInfos;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @RequestMapping(value = "get_device_info")
    @ResponseBody
    public List<DeviceInfo> getDeviceInfo(@RequestParam String wxBindInfo) {
        try {
            List<WxBindInfo> bindInfos = JSON.parseArray(wxBindInfo, WxBindInfo.class);
            List<DeviceInfo> deviceInfos = new ArrayList<DeviceInfo>();
            DeviceInfo deviceInfo;
            for (WxBindInfo bindInfo : bindInfos) {
                deviceInfo = weixinService.getDeviceInfo(bindInfo.getDeviceId());
                deviceInfos.add(deviceInfo);
            }
            return deviceInfos;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @RequestMapping(value = "get_update_info")
    @ResponseBody
    public DeviceVersionUpdate getUpdateInfo(String model, String deviceType, String version) {
        try {
            DeviceVersionUpdate deviceVersionUpdate = new DeviceVersionUpdate();
            deviceVersionUpdate.setDeviceType(deviceType);
            deviceVersionUpdate.setModel(model);
            deviceVersionUpdate = weixinService.getDeviceVersionUpdateInfo(deviceVersionUpdate);

            String serverVersion = deviceVersionUpdate.getVersion();
            if (isNeedUpdate(serverVersion, version)) {
                return deviceVersionUpdate;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @RequestMapping(value = "jsq_home.html")
    public String jsqLeaseHome(HttpServletRequest request, HttpServletResponse response, Model model) {
        String deviceId = request.getParameter("device_id");
        String deptName = request.getParameter("dept_name");
        String deviceSN = request.getParameter("device_sn");
        String address = request.getParameter("address_detail");
        String startDate = request.getParameter("start_date");
        String endDate = request.getParameter("end_date");
        String deviceModel = request.getParameter("model");

        List<FilterInfo> filterInfos = new ArrayList<FilterInfo>();
        if (deviceModel != null) {
            filterInfos = weixinService.getFilterInfo(deviceModel);
        }

        Map<String, String> deviceInfo = new HashMap<String, String>();
        deviceInfo.put("deviceId", deviceId);
        deviceInfo.put("deptName", deptName);
        deviceInfo.put("deviceSN", deviceSN);
        deviceInfo.put("address", address);
        deviceInfo.put("startDate", startDate);
        deviceInfo.put("endDate", endDate);
        deviceInfo.put("model", deviceModel);
        deviceInfo.put("host", MQTT_HOST);
        deviceInfo.put("port", MQTT_PORT);

        model.addAttribute("deviceInfo", deviceInfo);

        model.addAttribute("filterInfo", filterInfos);

        return "jsq_home";
    }

    @RequestMapping(value = "update_general_device_name")
    @ResponseBody
    public String updateGeneralDeviceName(HttpServletRequest request, String generalId, String name, String reminderCircle, String resetDate, Model model) {
        logger.info("name: " + name + ", generalId:" + generalId
                + ", reminderCircle:" + reminderCircle + ", resetDate: " + resetDate);
        try {
            if (!isInteger(reminderCircle)) {
                String regEx = "[^0-9]";
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(reminderCircle);
                reminderCircle = m.replaceAll("").trim();
            }
            GeneralDeviceInfo generalDeviceInfo = new GeneralDeviceInfo();
            generalDeviceInfo.setGeneral_id(generalId);
            generalDeviceInfo.setNick_name(name);
            generalDeviceInfo.setReminder_circle(Integer.parseInt(reminderCircle));
            generalDeviceInfo.setReset_date(resetDate);
            weixinService.updateGeneralDeviceName(generalDeviceInfo);
            return "SUCCESS";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "FAILED";
        }
    }

    @RequestMapping(value = "update_device_model")
    @ResponseBody
    public String updateDeviceModel(HttpServletRequest request, String deviceId, String deviceType, String newModel, Model model) {
        try {
            logger.info("new model: " + newModel);
            WxAppInfo wxAppInfo = new WxAppInfo();
            wxAppInfo.setGhId(JSQ_GH_ID);
            wxAppInfo = weixinService.getWxAppInfo(wxAppInfo);
            String appId = wxAppInfo.getAppId();
            String appSecret = wxAppInfo.getAppSecret();
            String cookieUid = CookieUtil.getCookie(appId + "_uid", request);
            DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.setDeviceId(deviceId);
            deviceInfo.setModel(newModel);
            weixinService.updateDeviceModel(deviceInfo);
            return "SUCCESS UPDATE MODEL";
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "FAILED TO UPDATE MODEL";
    }

    @RequestMapping(value = "update_device_name")
    @ResponseBody
    public String updateDeviceName(HttpServletRequest request, String deviceId, String deviceType, String name, Model model) {
        try {
            logger.info("name: " + name + ", deviceId = " + deviceId + ", type: " + deviceType);
            WxAppInfo wxAppInfo = new WxAppInfo();
            wxAppInfo.setGhId(JSQ_GH_ID);
            wxAppInfo = weixinService.getWxAppInfo(wxAppInfo);
            String appId = wxAppInfo.getAppId();
            String appSecret = wxAppInfo.getAppSecret();
            String cookieUid = CookieUtil.getCookie(appId + "_uid", request);
//            logger.info("openid=" + cookieUid);
//            cookieUid = "oPcODwQ-pBu1K6CLFZdAJhuMwiGo";

            WxBindInfo wxBindInfo = new WxBindInfo();
            wxBindInfo.setDeviceId(deviceId);
            wxBindInfo.setDeviceType(deviceType);
            wxBindInfo.setOpenid(cookieUid);
            wxBindInfo.setDeviceName(name);
            weixinService.updateDeviceName(wxBindInfo);
            return "SUCCESS";

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "FAILED";
    }

    @RequestMapping(value = "filterdetails.html")
    public String filter(@RequestParam("levelid") String levelid, @RequestParam("filterid") String filterid, @RequestParam("perused") String perused, Model model) {
        Map<String, String> filterdetails = new HashMap<String, String>();
        filterdetails.put("levelid", levelid);
        filterdetails.put("filterid", filterid);
        filterdetails.put("perused", perused);
        model.addAttribute("filterdetails", filterdetails);
        return "filter";
    }

    @RequestMapping(value = "faq.html")
    public String faq(HttpServletRequest request, HttpServletResponse response, String code, Model model) {
        return "Help/jsq_faq";
    }

    @RequestMapping(value = "cs.html")
    public String customService(HttpServletRequest request, HttpServletResponse response, String code, Model model) {
        try {
            WxAppInfo wxAppInfo = new WxAppInfo();
            wxAppInfo.setGhId(JSQ_GH_ID);
            wxAppInfo = weixinService.getWxAppInfo(wxAppInfo);
            String appId = wxAppInfo.getAppId();
            String cookieUid = CookieUtil.getCookie(appId + "_uid", request);
            model.addAttribute("openid", cookieUid);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "/Customer/cs";
    }

    @RequestMapping(value = "aftersale_service.html")
    public String installAndRepair(HttpServletRequest request, HttpServletResponse response, String code, Model model) {
        String category = request.getParameter("category");
        logger.info("category = " + category);
        if (!"".equals(category) && category != null) {
            model.addAttribute("category", category);
        }
        return "aftersale_service";
    }


    @RequestMapping(value = "customer_service.html")
    public String customerService(HttpServletRequest request, HttpServletResponse response, String code, Model model) {
        return "customer_service";
    }

    @RequestMapping(value = "service.html")
    public String serviceInfo(ServiceInfo info) {
        logger.info("service Info: " + info.toString());
        return "";
    }


    @RequestMapping(value = "net_setting.html")
    public String netSetting(HttpServletRequest request, HttpServletResponse response, String code, Model model) {
        return "netsetting";
    }

    @RequestMapping(value = "add_general_device.html")
    public String addNewDevice(HttpServletRequest request, HttpServletResponse response, String code, Model model) {
        String productModel = request.getParameter("productModel");
        logger.info("product model : " + productModel);
        model.addAttribute("productModel", productModel);
        return "add_general_device";
    }

    @RequestMapping(value = "pre_filter.html")
    public String preFilter(HttpServletRequest request, HttpServletResponse response, String code, Model model) {
        String generalId = request.getParameter("generalId");
        logger.info("general id: " + generalId);
        GeneralDeviceInfo generalDeviceInfo;
        try {
            generalDeviceInfo = weixinService.getGeneralInfo(generalId);
            logger.info("general Info: " + generalDeviceInfo.getNick_name() + ", reset date: " + generalDeviceInfo.getReset_date());
            if (generalDeviceInfo != null) {
                model.addAttribute("generalInfo", generalDeviceInfo);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "pre_filter";
    }

    @RequestMapping(value = "super_filter")
    public String superFilter() {
        return "super_filter";
    }


    @RequestMapping(value = "getCityInfo")
    @ResponseBody
    public String getCityInfo(String location) {
        try {
            String url = "http://apis.map.qq.com/ws/geocoder/v1/?location=" + location + "&key=" + ServiceConstant.QQ_MAP_KEY;
            String result = HttpClientUtil.httpGetRequest(url);
            String city = "";
            if (result != null && !"".equals(result)) {
                Map<String, Object> info = JSON.parseObject(result);
                Map<String, Object> addressInfo = JSON.parseObject(info.get("result").toString());
                Map<String, Object> cityInfo = JSON.parseObject(addressInfo.get("address_component").toString());
                city = cityInfo.get("city").toString();
            }
            return city;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "";
    }

    @RequestMapping(value = "registerGeneralDevice", method = RequestMethod.POST)
    public String registerGeneralDevice(HttpServletRequest request, String selectModel, Model model, RedirectAttributes attributes) {
        String deviceModel = request.getParameter("selectModel");
        String deviceByName = request.getParameter("deviceByName");
        String installDate = request.getParameter("installDate");
        logger.info("receive: " + deviceModel + ", " + deviceByName + ", " + installDate);
        try {
            WxAppInfo wxAppInfo = new WxAppInfo();
            wxAppInfo.setGhId(JSQ_GH_ID);
            wxAppInfo = weixinService.getWxAppInfo(wxAppInfo);
            String appId = wxAppInfo.getAppId();
            String appSecret = wxAppInfo.getAppSecret();
            String cookieUid = CookieUtil.getCookie(appId + "_uid", request);// cookieUid = openid
//            cookieUid = "ovAFut6Jkhz9z2a6Egmh7CVSzorM";
            List<DeviceInfo> registeredInfos = weixinService.getGeneralBindCount(cookieUid + "%");
            logger.info("count:" + registeredInfos.size());
//            DeviceInfo deviceInfo = new DeviceInfo();
            GeneralDeviceInfo generalDeviceInfo = new GeneralDeviceInfo();
            String deviceId = "";
            if (registeredInfos.size() < 5) {
                int rdm = (int) (Math.random() * (9000)) + 1000;
                deviceId = cookieUid + "_" + String.valueOf(rdm);
                generalDeviceInfo.setGeneral_id(deviceId);
                generalDeviceInfo.setApp_id(appId);
                generalDeviceInfo.setOpen_id(cookieUid);
                generalDeviceInfo.setDevice_model(deviceModel);
                generalDeviceInfo.setDevice_type("9");
                generalDeviceInfo.setNick_name(deviceByName);
                generalDeviceInfo.setInstall_date(installDate);
                generalDeviceInfo.setReset_date(installDate);
                generalDeviceInfo.setReminder_circle(15);
                weixinService.saveGeneralDeviceBindInfo(generalDeviceInfo);

                Map<String, String> serverInfo = new HashMap<String, String>();
                serverInfo.put("host", MQTT_HOST);
                serverInfo.put("port", MQTT_PORT);
                serverInfo.put("appId", appId);
                model.addAttribute("serverInfo", serverInfo);
            } else {
                return "redirect:http://wx.mypraise.cn/api/error.html";
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return "redirect:index.html";
    }

    @RequestMapping(value = "delete_general_bind_device")
    @ResponseBody
    public void deleteGeneralBindDevice(HttpServletRequest request, HttpServletResponse response, @RequestParam String generalId, Model model) {
        try {
            logger.info("to be delete general id: " + generalId);
            weixinService.deleteGeneralBindDevice(generalId);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
    }

    @RequestMapping(value = "wxInfo.html")
    public String wxInfo(HttpServletRequest request, HttpServletResponse response, String code, Model model, RedirectAttributes attributes) {
        try {
            String redirctUrl = request.getParameter("backurl");
            String info = request.getParameter("info");
            if ("".equals(redirctUrl) || redirctUrl == null) {
                return "";
            }
            WxAppInfo wxAppInfo = new WxAppInfo();
            wxAppInfo.setGhId(JSQ_GH_ID);
            wxAppInfo = weixinService.getWxAppInfo(wxAppInfo);
            String appId = wxAppInfo.getAppId();
            String appSecret = wxAppInfo.getAppSecret();
            String cookieUid = CookieUtil.getCookie(appId + "_uid", request);
            if (cookieUid != null && !"".equals(cookieUid)) {
                CookieUtil.setCookie(appId + "_uid", cookieUid, response);
            } else if (code != null && !"".equals(code)) {
                Map<Object, Object> openidMap = WeixinServerEngin.getOauthUserId(code, appId, appSecret);
                if (openidMap != null) {
                    cookieUid = StrUtil.objectToString(openidMap.get("openid"));

                    if (cookieUid != null && !"".equals(cookieUid)) {
                        CookieUtil.setCookie(appId + "_uid", cookieUid, response);
                    }
                }
            } else {
                String homeUrl = ServiceConstant.WX_DOMAIN + "web/wechat/index.html";
                try {
                    homeUrl = URLEncoder.encode(homeUrl, "utf-8");
                } catch (UnsupportedEncodingException e) {
                }
                String homeMenuUrl = WeixinConstant.OAUTH_URL;
                homeMenuUrl = homeMenuUrl.replace("${appid}", appId);
                homeMenuUrl = homeMenuUrl.replace("${redirect_uri}", homeUrl);

                return "redirect:" + homeMenuUrl;
            }
            WxUserInfo wxUserInfo = weixinService.getWxUserInfo(cookieUid);
            if (wxUserInfo != null) {
                attributes.addAttribute("wxid", wxUserInfo.getOpenid());
                attributes.addAttribute("wxname", wxUserInfo.getNickname());
                attributes.addAttribute("sex", wxUserInfo.getSex());
                attributes.addAttribute("issub", "0");
                attributes.addAttribute("headimg", wxUserInfo.getHeadimgurl());
                return "redirect:" + redirctUrl;
            } else {
                String redUrl = "http://wx.mypraise.cn/api/wechat/getWxPageAuthInfo.html?backurl=" + redirctUrl;
                String tempUrl = URLEncoder.encode(redUrl, "utf-8");
                String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId + "&redirect_uri=" + tempUrl + "&response_type=code&scope=snsapi_userinfo&state=" + redirctUrl + "#wechat_redirect";

                return "redirect:" + url;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "";
    }

    @RequestMapping(value = "getWxPageAuthInfo.html")
    public String getWxPageAuthInfo(HttpServletRequest request, HttpServletResponse response, String code, Model model, RedirectAttributes attributes) {
        try {
            String wxCode = request.getParameter("code");
            String state = request.getParameter("state");
            String redirectUrl = state;
            WxAppInfo wxAppInfo = new WxAppInfo();
            wxAppInfo.setGhId(JSQ_GH_ID);
            wxAppInfo = weixinService.getWxAppInfo(wxAppInfo);
            String appId = wxAppInfo.getAppId();
            String appSecret = wxAppInfo.getAppSecret();
            Map<String, String> wxAcccessMap = weixinService.getWxPageAccessToken(appId, appSecret, wxCode);
            String accessToken = wxAcccessMap.get("access_token");
            String openId = wxAcccessMap.get("openid");

            if (!weixinService.isValidAccess(accessToken, openId)) {
                Map<String, String> refreshAccess = weixinService.refreshWxPageAccessToken(appId);
                accessToken = refreshAccess.get("access_token");
                openId = wxAcccessMap.get("openid");
            }
            WxUserInfo wxUserInfo = weixinService.getWxPageAuthInfo(accessToken, openId);

            if (wxUserInfo != null) {
                attributes.addAttribute("wxid", wxUserInfo.getOpenid());
                attributes.addAttribute("wxname", wxUserInfo.getNickname());
                attributes.addAttribute("sex", wxUserInfo.getSex() + "");
                attributes.addAttribute("issub", "0");
                attributes.addAttribute("headimg", wxUserInfo.getHeadimgurl());
                return "redirect:" + redirectUrl;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "";
    }

    @RequestMapping(value = "share.html")
    @ResponseBody
    public String share(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            String shareUrl = request.getParameter("url");
            String api = request.getParameter("api");
            WxAppInfo wxAppInfo = new WxAppInfo();
            wxAppInfo.setGhId(JSQ_GH_ID);
            wxAppInfo = weixinService.getWxAppInfo(wxAppInfo);
            String appId = wxAppInfo.getAppId();
            WxJsApiTicket wxTicket = weixinService.getJsApiTicket(appId);
            String signature = getSignature(shareUrl, wxTicket.getJsApiTicket());
            Map<String, String> result = new HashMap<String, String>();
            result.put("status", "1");
            result.put("appid", appId);
            result.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
            result.put("noncestr", noncestr);
            result.put("signature", signature);
            result.put("url", shareUrl);
            result.put("info", "获取成功");

            return JSON.toJSONString(result);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "";
    }


    @RequestMapping(value = "postErrorCode")
    @ResponseBody
    public void postErrorCode(@RequestParam("deviceId") String deviceId, @RequestParam("errorcode") String errorcode, Model model) {
        System.out.println("postErrorCode deviceId:" + deviceId + " " + errorcode);
        DeviceErrorCode mDeviceErrorCode = new DeviceErrorCode();
        mDeviceErrorCode.setDeviceId(deviceId);
        mDeviceErrorCode.setErrorCode(Integer.parseInt(errorcode));
        weixinService.saveDeviceErrorCode(mDeviceErrorCode);
    }

    private boolean isNeedUpdate(String serverVersion, String currentVersion) {
        try {
            if (serverVersion == "" || serverVersion == null || currentVersion == "" || currentVersion == null) {
                return false;
            }
            int versionNunber1 = serverVersion.indexOf("VA") + 2;
            String sVersionString = serverVersion.substring(versionNunber1, serverVersion.length());
            int versionNunber2 = currentVersion.indexOf("VA") + 2;
            String cVersionString = currentVersion.substring(versionNunber2, currentVersion.length());
            if (Double.parseDouble(sVersionString) > Double.parseDouble(cVersionString)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private String getSignature(String url, String wxTicket) {
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String signature = ShaUtil.stringSHA1("jsapi_ticket=" + wxTicket + "&noncestr=" + noncestr +
                "&timestamp=" + timestamp + "&url=" + url);
        return signature;
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }


}
