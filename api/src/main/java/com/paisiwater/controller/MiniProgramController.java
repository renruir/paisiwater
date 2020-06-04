package com.paisiwater.controller;

import com.alibaba.fastjson.JSON;
import com.paisi.utils.CookieUtil;
import com.paisi.utils.StrUtil;
import com.paisiwater.api.controller.constant.WeixinConstant;
import com.paisiwater.api.model.DeviceInfo;
import com.paisiwater.api.model.FilterInfo;
import com.paisiwater.api.model.GeneralDeviceInfo;
import com.paisiwater.model.WxBindInfo;
import com.paisiwater.model.WxUserInfo;
import com.paisiwater.service.WeixinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("miniprogram")
public class MiniProgramController {

    private static final Logger logger = LoggerFactory.getLogger(MiniProgramController.class);
    @Autowired
    private WeixinService weixinService;

    @RequestMapping("test")
    public String test(String abc) {
        return "test";
    }

    @RequestMapping(value = "get_bind_device_info")
    @ResponseBody
    public String getDeviceInfoByUnionId(HttpServletRequest request, @RequestParam("deviceId") String deviceId, @RequestParam("unionId") String unionId, Model model) {
        Map<String, String> zInfo = new HashMap<>();
        try {
            WxUserInfo wxUserInfo = weixinService.getWxUserInfoByUnionId(unionId);
            WxBindInfo wxBindInfo = new WxBindInfo();
            wxBindInfo.setOpenid(wxUserInfo.getOpenid());
            wxBindInfo.setDeviceId(deviceId);
            wxBindInfo.setAppId(WeixinConstant.APP_ID);
            wxBindInfo = weixinService.getWxBindInfoByDevice(wxBindInfo);
            DeviceInfo deviceInfo = null;
            List<FilterInfo> filterInfos = null;
            if (wxBindInfo != null && StrUtil.strIsNotNull(wxBindInfo.getDeviceId())) {
                deviceInfo = weixinService.getDeviceInfo(wxBindInfo.getDeviceId());
                if (deviceInfo != null) {
                    filterInfos = weixinService.getFilterInfo(deviceInfo.getModel());
//                    zInfo.put("unionid", wxUserInfo.getUnionid());
                    zInfo.put("deviceId", deviceInfo.getDeviceId());
                    zInfo.put("seqNum", deviceInfo.getSeqNum());
                    zInfo.put("model", deviceInfo.getModel());
                    String filters = JSON.toJSONString(filterInfos);
                    zInfo.put("filters", filters);
                    logger.info("zInfo: " + JSON.toJSONString(zInfo));
                    return JSON.toJSONString(zInfo);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequestMapping(value = "get-bind-list")
    @ResponseBody
    public String getBindListbyUnionId(HttpServletRequest request, @RequestParam("unionId") String unionId, Model model) {
        logger.info("unionId: " + unionId);
        try {
            WxUserInfo wxUserInfo = weixinService.getWxUserInfoByUnionId(unionId);
            WxBindInfo wxBindInfo = new WxBindInfo();
            wxBindInfo.setOpenid(wxUserInfo.getOpenid());
            wxBindInfo.setAppId(WeixinConstant.APP_ID);
            List<WxBindInfo> wxBindInfoList = weixinService.getWxBindInfo(wxBindInfo);
            List<DeviceInfo> deviceInfoList = new ArrayList<>();

            if (wxBindInfoList != null) {
                for (WxBindInfo bindInfo : wxBindInfoList) {
                    DeviceInfo deviceInfo = weixinService.getDeviceInfo(bindInfo.getDeviceId());
                    deviceInfoList.add(deviceInfo);
                }
                String strDevicesList = JSON.toJSONString(deviceInfoList);
                Map<String, String> info = new HashMap<>();
                info.put("deviceList", strDevicesList);
                logger.info("deviceInfoList:" + strDevicesList);
                return JSON.toJSONString(info);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequestMapping(value = "get-general-list")
    @ResponseBody
    public String getGeneralBindInfo(HttpServletRequest request, @RequestParam("unionId") String unionId, Model model) throws Exception {
        try {
            WxUserInfo wxUserInfo = weixinService.getWxUserInfoByUnionId(unionId);
            List<GeneralDeviceInfo> generalDeviceInfos;
            GeneralDeviceInfo info = new GeneralDeviceInfo();
            info.setOpen_id(wxUserInfo.getOpenid());
            info.setApp_id(WeixinConstant.APP_ID);
            generalDeviceInfos = weixinService.getGeneralBindInfo(info);
            if (generalDeviceInfos != null) {
                Map<String, String> result = new HashMap<>();
                result.put("generalList", JSON.toJSONString(generalDeviceInfos));
                return JSON.toJSONString(result);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

}
