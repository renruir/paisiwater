package com.paisiwater.dao;

import com.paisiwater.api.model.*;
import com.paisiwater.device.model.LeaseInfo;
import com.paisiwater.model.*;

import java.util.List;
import java.util.Map;

/**
 * create by renrui at 2018/1/10/0010 10:00
 */

public interface WeixinDao {

    /**
     * 保存微信公众号基本信息
     * @param wxAppInfo
     */
    public void saveWxAppInfo(WxAppInfo wxAppInfo);

    /**
     * 保存微信公众号AccessToken
     * @param wxAccessToken
     */
    public void saveAccessToken(WxAccessToken wxAccessToken);

    /**
     * 获取微信AccessToken
     * @param appId
     * @return
     */
    public WxAccessToken getAccessToken(String appId);

    /**
     * 保存微信JsApiTicket
     * @param wxJsApiTicket
     */
    public void saveJsApiTicket(WxJsApiTicket wxJsApiTicket);

    /**
     * 获取微信jsApiTicket
     */
    public WxJsApiTicket getJsApiTicket(String appId);

    /**
     * 获取指定的微信信息
     * @param wxAppInfo
     * @return
     */
    public WxAppInfo getWxAppInfo(WxAppInfo wxAppInfo);

    /**
     * 获取所有的微信信息列表
     * @return
     */
    public List<WxAppInfo> getWxAppInfoList();

    /**
     * 添加注册设备信息
     * @param deviceInfo
     */
    public void saveDeviceInfo(DeviceInfo deviceInfo);

    /**
     * 添加设备净水数据统计
     * @param deviceDataStat
     */
    public void saveDeviceDataStat(DeviceDataStat deviceDataStat);

    /**
     * 获取设备净水数据统计(日)
     * @param map
     */
    public List<DeviceDataStat> getDailyDeviceDataStat(Map<String, Object> map);

    /**
     * 获取所有净水数据统计
     * @param sqlDate
     */
    public List<DeviceDataStat> getDeviceDataStat(java.sql.Date sqlDate);

    /**
     * 获取设备净水数据统计(月)
     * @param map
     */
    public List<DeviceDataStat> getMonthlyDeviceDataStat(Map<String, Object> map);

    /**
     * 获取设备净水数据统计(年)
     * @param map
     */
    public List<DeviceDataStat> getYearlyDeviceDataStat(Map<String, Object> map);

    /**
     * 删除设备净水数据
     * @param map
     */
    public void delDeviceDataStat(Map<String, Object> map);

    /**
     * 获取设备最新净水数据统计
     * @param deviceId
     */
    public String getjsTotal(String deviceId);

    /**
     * 添加绑定信息
     * @param wxBindInfo
     */
    public void saveWxBindInfo(WxBindInfo wxBindInfo);

    /**
     * 更新绑定状态
     * @param wxBindInfo
     */
    public void updateWxBindStat(WxBindInfo wxBindInfo);

    /**
     * 解绑设备所有用户
     * @param wxBindInfo
     */
    public void unbindDeviceAllUser(WxBindInfo wxBindInfo);

    /**
     * 解绑用户所有设备
     * @param wxBindInfo
     */
    public void unbindUserAllDevice(WxBindInfo wxBindInfo);

    /**
     * 获取绑定信息
     * @param wxBindInfo
     * @return
     */
    public List<WxBindInfo> getWxBindInfo(WxBindInfo wxBindInfo);

    /**
     * 获取指定设备绑定信息
     * @param wxBindInfo
     * @return
     */
    public WxBindInfo getWxBindInfoByDevice(WxBindInfo wxBindInfo);

    /**
     * 更新绑定设备的型号
     * 2019-04-23 新需求，由于部门型号产品销售不好，为减少电控型号，统一使用一个型号，然后用户在绑定以后自行
     * 修改型号名称
     */
    public void updateDeviceModel(DeviceInfo deviceInfo);

    /**
     * 更新绑定设备的名称
     * @param wxBindInfo
     */
    public void updateDeviceName(WxBindInfo wxBindInfo);

    /**
     * 添加微信用户信息
     * @param wxUserInfo
     */
    public void saveWxUserInfo(WxUserInfo wxUserInfo);

    /**
     * 获取微信用户信息
     * @param openid
     * @return
     */
    public WxUserInfo getWxUserInfo(String openid);

    /**
     * 获取升级信息
     * @param deviceVersionUpdate
     * @return
     */
    public DeviceVersionUpdate getDeviceVersionUpdateInfo(DeviceVersionUpdate deviceVersionUpdate);

    /**
     * 获取设备信息
     * @param deviceId
     * @return
     */
    public DeviceInfo getDeviceInfo(String deviceId);

    /**
     * 获取设备信息
     * @param model
     * @return
     */
    public List<FilterInfo> getFilterInfo(String model);

    /**
     * 添加设备故障码
     * @param deviceErrorCode
     */
    public void saveDeviceErrorCode(DeviceErrorCode deviceErrorCode);

    /**
     * 获取绑定用户的openID
     * @param device_id
     */
    public List<String> getBindUserOpenId(String device_id);

    /**
     * 获取用户注册时间
     * @param device_id
     */
    public String getRegisterTime(String device_id);

    /**
     * 获取微信用户详细信息
     * @param openid
     * @return
     */
    public WxUserDetailsInfo getWxUserDetailsInfo(String openid);

    /**w
     * 添加微信用户详细信息
     * @param wxUserDetailsInfo
     */
    public void saveWxUserDetailsInfo(WxUserDetailsInfo wxUserDetailsInfo);

    /**
     * 获取所有省份以及直辖市tds信息
     * @param
     */
    public List<ProvinceTds> getAllProvinceInfo();

    /**
     * 获取省份以及直辖市tds信息
     * @param province
     */
    public List<ProvinceTds> getProvinceInfo(String province);

    /**
     * 获取所有市tds信息
     * @param
     */
    public List<CityTds> getAllCityInfo();

    /**
     * 获取市级tds信息
     * @param provinceid
     */
    public List<CityTds> getCityInfo(String provinceid);

    /**
     * 获取所有县级市、区tds信息
     * @param
     */
    public List<RegionTds> getAllRegionInfo();

    /**
     * 获取县级市、区tds信息
     * @param cityid
     */
    public List<RegionTds> getRegionInfo(String cityid);


    /**
     * 添加租赁设备信息
     * @param leaseInfo
     */
    public void saveLeaseInfo(LeaseInfo leaseInfo);

    /**
     * 获取租赁设备信息
     */
    public List<LeaseInfo> getLeaseInfos();

    /**
     * 获取同一个openid下注册的通用设备数量
     */
    public List<DeviceInfo> getGeneralBindCount(String openid);

    /**
     * 保存通用设备绑定信息
     */
    public void saveGeneralDeviceBindInfo(GeneralDeviceInfo generalDeviceInfo);


    /**
     * 获取非智能通用绑定信息
     * @param generalDeviceInfo
     * @return
     */
    public List<GeneralDeviceInfo> getGeneralBindInfo(GeneralDeviceInfo generalDeviceInfo);

    /**
     * 更新通用设备的名称
     * update General device name
     */
    public void updateGeneralDeviceName(GeneralDeviceInfo generalDeviceInfo);

    /**
     * 获取指定id的设备信息
     */
    public GeneralDeviceInfo getGeneralInfo(String general_id);

    /**
     * 删除指定general id的绑定设备
     */
    public void deleteGeneralBindDevice(String general_id);

    /**
     * 获取快接近冲洗周期的设备信息
     */
    public List<GeneralDeviceInfo> scanGeneralDeviceInfo();

}


