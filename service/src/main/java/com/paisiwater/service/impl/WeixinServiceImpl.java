package com.paisiwater.service.impl;

import com.alibaba.fastjson.JSON;
import com.paisiwater.api.model.*;
import com.paisi.utils.StrUtil;
import com.paisiwater.dao.WeixinDao;
import com.paisiwater.device.model.LeaseInfo;
import com.paisiwater.engin.WeixinServerEngin;
import com.paisiwater.model.*;
import com.paisiwater.service.WeixinService;
import com.paisiwater.service.constant.ServiceConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuhaichao on 2016/11/17.
 */
@Service
public class WeixinServiceImpl implements WeixinService {

    private static final Logger logger = LoggerFactory.getLogger(WeixinServiceImpl.class);

    @Autowired
    private WeixinDao weixinDao;

    public void saveWxAppInfo(WxAppInfo wxAppInfo) throws Exception {
        weixinDao.saveWxAppInfo(wxAppInfo);
    }

    public void saveAccessToken(WxAccessToken wxAccessToken) throws Exception {
        weixinDao.saveAccessToken(wxAccessToken);
    }

    public WxAccessToken getAccessToken(String appId) throws Exception {
        return weixinDao.getAccessToken(appId);
    }

    @Override
    public void saveJsApiTicket(WxJsApiTicket wxJsApiTicket) {
        weixinDao.saveJsApiTicket(wxJsApiTicket);
    }

    @Override
    public WxJsApiTicket getJsApiTicket(String appId) {
        return weixinDao.getJsApiTicket(appId);
    }

    public WxAppInfo getWxAppInfo(WxAppInfo wxAppInfo) throws Exception {
        return weixinDao.getWxAppInfo(wxAppInfo);
    }

    public List<WxAppInfo> getWxAppInfoList() throws Exception {
        return weixinDao.getWxAppInfoList();
    }

    public void initWxAccessToken() throws Exception {
        List<WxAppInfo> appInfoList = weixinDao.getWxAppInfoList();
        if (appInfoList != null && appInfoList.size() > 0) {
            for (WxAppInfo wxAppInfo : appInfoList) {
                WxAccessToken wxAccessToken = new WxAccessToken();
                wxAccessToken.setAppId(wxAppInfo.getAppId());
                Map<String, Object> accessTokenMap = WeixinServerEngin.getWxAccessToken(wxAppInfo.getAppId(), wxAppInfo.getAppSecret());
                String accessToken = StrUtil.objectToString(accessTokenMap.get("access_token"));
                wxAccessToken.setAccessToken(accessToken);
                weixinDao.saveAccessToken(wxAccessToken);
                logger.info(wxAppInfo.getAppId() + " init success...");
            }
        }
    }


    public void initWxJsApiTicket() throws Exception {
        List<WxAppInfo> appInfoList = weixinDao.getWxAppInfoList();
        if (appInfoList != null && appInfoList.size() > 0) {
            for (WxAppInfo wxAppInfo : appInfoList) {
                WxAccessToken wxAccessToken = weixinDao.getAccessToken(wxAppInfo.getAppId());
                WxJsApiTicket wxJsApiTicket = new WxJsApiTicket();
                wxJsApiTicket.setAppId(wxAppInfo.getAppId());
                wxJsApiTicket.setToken(wxAccessToken.getAccessToken());
                Map<String, Object> ticketMap = WeixinServerEngin.getWxJsApiTicket(wxAccessToken.getAccessToken());
                String jsApiTicket = StrUtil.objectToString(ticketMap.get("ticket"));
                wxJsApiTicket.setJsApiTicket(jsApiTicket);
                weixinDao.saveJsApiTicket(wxJsApiTicket);
                logger.info(wxJsApiTicket.getJsApiTicket() + " ticket init success...");
            }
        }
    }

    public boolean authorizeDevice(String deviceId, String mac, String productId, String appId) throws Exception {
        logger.info("appId:" + appId);
        WxAccessToken wxAccessToken = weixinDao.getAccessToken(appId);
        if (wxAccessToken != null) {
            logger.info("ttt...");
            Map<String, Object> statMap = WeixinServerEngin.getAuthStat(deviceId, wxAccessToken.getAccessToken());
            String status = StrUtil.objectToString(statMap.get("status"));
            if ("0".equals(status)) {
                Map<String, Object> authMap = WeixinServerEngin.authorizeDevice(deviceId, mac, productId, wxAccessToken.getAccessToken());
                List<Map<String, Object>> respList = (List<Map<String, Object>>) authMap.get("resp");
                if (respList != null && respList.size() > 0) {
                    Map<String, Object> respObj = respList.get(0);
                    String errcode = StrUtil.objectToString(respObj.get("errcode"));
                    if ("0".equals(errcode)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Map<String, String> getWxPageAccessToken(String appId, String appSecret, String wxCode) {
        return WeixinServerEngin.getWxPageAccessToken(appId, appSecret, wxCode);
    }

    public Map<String, String> refreshWxPageAccessToken(String appId) {
        return WeixinServerEngin.refreshWxPageAccessToken(appId);
    }

    public WxUserInfo getWxPageAuthInfo(String accessToken, String openId) {
        WxUserInfo wxUserInfo = new WxUserInfo();
        Map<String, Object> infoMap = WeixinServerEngin.getWxPageAuthInfo(accessToken, openId);
        if (infoMap == null) {
            return null;
        } else {
            wxUserInfo.setOpenid(infoMap.get("openid").toString());
            wxUserInfo.setCity(infoMap.get("city").toString());
            wxUserInfo.setNickname(infoMap.get("nickname").toString());
            wxUserInfo.setSex(infoMap.get("sex").toString());
            wxUserInfo.setProvince(infoMap.get("province").toString());
            wxUserInfo.setCountry(infoMap.get("country").toString());
            wxUserInfo.setHeadimgurl(infoMap.get("headimgurl").toString());
            return wxUserInfo;
        }
    }


    public boolean isValidAccess(String accessToken, String openId) {
        return WeixinServerEngin.isValidAccess(accessToken, openId);
    }

    public void saveDeviceInfo(DeviceInfo deviceInfo) throws Exception {
        weixinDao.saveDeviceInfo(deviceInfo);
    }

    @Transactional
    public void saveWxBindInfo(WxBindInfo wxBindInfo) throws Exception {
        weixinDao.saveWxBindInfo(wxBindInfo);
    }

    public void updateWxBindStat(WxBindInfo wxBindInfo) throws Exception {
        weixinDao.updateWxBindStat(wxBindInfo);
    }

    public void unbindDeviceAllUser(WxBindInfo wxBindInfo) throws Exception {
        weixinDao.unbindDeviceAllUser(wxBindInfo);
    }

    public List<WxBindInfo> getWxBindInfo(WxBindInfo wxBindInfo) throws Exception {
        return weixinDao.getWxBindInfo(wxBindInfo);
    }

    public WxBindInfo getWxBindInfoByDevice(WxBindInfo wxBindInfo) throws Exception {
        return weixinDao.getWxBindInfoByDevice(wxBindInfo);
    }

    @Override
    public void updateDeviceModel(DeviceInfo deviceInfo) throws Exception {
       weixinDao.updateDeviceModel(deviceInfo);
    }

    @Override
    public void updateDeviceName(WxBindInfo wxBindInfo) throws Exception {
        weixinDao.updateDeviceName(wxBindInfo);
    }


    public void unbindUserAllDevice(WxBindInfo wxBindInfo) {
        weixinDao.unbindUserAllDevice(wxBindInfo);
    }

    public void saveWxUserInfo(WxUserInfo wxUserInfo) {
        weixinDao.saveWxUserInfo(wxUserInfo);
    }

    public WxUserInfo getWxUserInfo(String openid) {
        return weixinDao.getWxUserInfo(openid);
    }

    public void saveDeviceDataStat(DeviceDataStat deviceDataStat) {
        weixinDao.saveDeviceDataStat(deviceDataStat);
    }

//    public List<DeviceDataStat> getDailyDeviceDataStat(Map<String, Object> map) {
//        return weixinDao.getDailyDeviceDataStat(map);
//    }
//
//    public List<DeviceDataStat> getDeviceDataStat(java.sql.Date sqlDate) {
//        return weixinDao.getDeviceDataStat(sqlDate);
//    }
//
//    public List<DeviceDataStat> getMonthlyDeviceDataStat(Map<String, Object> map) {
//        return weixinDao.getMonthlyDeviceDataStat(map);
//    }
//
//    public List<DeviceDataStat> getYearlyDeviceDataStat(Map<String, Object> map) {
//        return weixinDao.getYearlyDeviceDataStat(map);
//    }

    public void delDeviceDataStat(Map<String, Object> map) {
        weixinDao.delDeviceDataStat(map);
    }

    public String getjsTotal(String deviceId) {
        return weixinDao.getjsTotal(deviceId);
    }

    public DeviceVersionUpdate getDeviceVersionUpdateInfo(DeviceVersionUpdate deviceVersionUpdate) {
        return weixinDao.getDeviceVersionUpdateInfo(deviceVersionUpdate);
    }

    public DeviceInfo getDeviceInfo(String deviceId) {
        return weixinDao.getDeviceInfo(deviceId);
    }

    @Override
    public List<FilterInfo> getFilterInfo(String model) {
        return weixinDao.getFilterInfo(model);
    }

    public void saveDeviceErrorCode(DeviceErrorCode deviceErrorCode) {
        weixinDao.saveDeviceErrorCode(deviceErrorCode);
    }

    @Override
    public List<String> getBindUserOpenId(String device_id) {
        return weixinDao.getBindUserOpenId(device_id);
    }

    @Override
    public String getRegisterTime(String device_id) {
        return weixinDao.getRegisterTime(device_id);
    }

    @Override
    public ReturnCode sendTemplateMsg(String info) {
        logger.info("========senTemplateMsg=======");
        ReturnCode returnCode = new ReturnCode();
        returnCode.setErrorCode(1);
        WxAccessToken wxAccessToken = weixinDao.getAccessToken(ServiceConstant.appId);
        Map<String, String> result = WeixinServerEngin.sendTemplateMsg(wxAccessToken.getAccessToken(), info);
        if (!result.isEmpty()) {
            if ("0" == result.get("errcode")) {
                returnCode.setErrorCode(0);
            }
        }
        return returnCode;
    }

    @Override
    public ReturnCode sendCustomMsg(String openId, String info) {
        try {
            ReturnCode returnCode = new ReturnCode();
            returnCode.setErrorCode(1);

            Map<String, String> replyContent = new HashMap<String, String>();
            replyContent.put("content", info);

            Map<String, Object> replyInfo = new HashMap<String, Object>();
            replyInfo.put("touser", openId);
            replyInfo.put("msgtype", "text");
            replyInfo.put("text", replyContent);

            String msg = JSON.toJSONString(replyInfo);

            WxAccessToken wxAccessToken = weixinDao.getAccessToken(ServiceConstant.appId);
            Map<String, String> result = WeixinServerEngin.sendCustommsg(wxAccessToken.getAccessToken(), msg);
            if (!result.isEmpty()) {
                if ("0" == result.get("errcode")) {
                    returnCode.setErrorCode(0);
                }
            }
            return returnCode;
        } catch (Exception e) {
            logger.info(e.getMessage());
            return null;
        }
    }

//	@Override
//	public String getMaterial(String info) {
//		logger.info("========getMaterial=======");
//		ReturnCode returnCode = new ReturnCode();
//		returnCode.setErrorCode(1);
//		WxAccessToken wxAccessToken = weixinDao.getAccessToken(ServiceConstant.appId);
//		String result = WeixinServerEngin.getMaterials(wxAccessToken.getAccessToken(), info);
//		return result;
//	}

    public WxUserDetailsInfo getWxUserDetailsInfo(String openid) {
        return weixinDao.getWxUserDetailsInfo(openid);
    }

    public void saveWxUserDetailsInfo(WxUserDetailsInfo wxUserDetailsInfo) {
        weixinDao.saveWxUserDetailsInfo(wxUserDetailsInfo);
    }

    public List<ProvinceTds> getAllProvinceInfo() {
        return weixinDao.getAllProvinceInfo();
    }

    public List<ProvinceTds> getProvinceInfo(String province) {
        return weixinDao.getProvinceInfo(province);
    }

    public List<CityTds> getAllCityInfo() {
        return weixinDao.getAllCityInfo();
    }

    public List<CityTds> getCityInfo(String provinceid) {
        return weixinDao.getCityInfo(provinceid);
    }

    public List<RegionTds> getAllRegionInfo() {
        return weixinDao.getAllRegionInfo();
    }

    public List<RegionTds> getRegionInfo(String cityid) {
        return weixinDao.getRegionInfo(cityid);
    }

    @Override
    public void saveLeaseInfo(LeaseInfo leaseInfo) {
        weixinDao.saveLeaseInfo(leaseInfo);
    }

    public List<LeaseInfo> getLeaseInfos() {
        return weixinDao.getLeaseInfos();
    }

    public List<DeviceInfo> getGeneralBindCount(String openid) {
        return weixinDao.getGeneralBindCount(openid);
    }

    public void saveGeneralDeviceBindInfo(GeneralDeviceInfo generalDeviceInfo) {
        weixinDao.saveGeneralDeviceBindInfo(generalDeviceInfo);
    }

    @Override
    public List<GeneralDeviceInfo> getGeneralBindInfo(GeneralDeviceInfo generalDeviceInfo) throws Exception {
        return weixinDao.getGeneralBindInfo(generalDeviceInfo);
    }

    @Override
    public void updateGeneralDeviceName(GeneralDeviceInfo generalDeviceInfo) throws Exception {
        weixinDao.updateGeneralDeviceName(generalDeviceInfo);
    }

    @Override
    public GeneralDeviceInfo getGeneralInfo(String general_id) {
        return weixinDao.getGeneralInfo(general_id);
    }

    /**
     * 删除指定general id的绑定设备
     */
    public void deleteGeneralBindDevice(String general_id) {
        weixinDao.deleteGeneralBindDevice(general_id);
    }

    @Override
    public List<GeneralDeviceInfo> scanGeneralDeviceInfo() throws Exception {
        return weixinDao.scanGeneralDeviceInfo();
    }

    @Override
    public WxUserInfo getWxUserInfoByUnionId(String unionid) throws Exception {
        return weixinDao.getWxUserInfoByUnionId(unionid);
    }


}
