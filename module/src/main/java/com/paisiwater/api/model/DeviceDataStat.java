package com.paisiwater.api.model;

/**
 * create by renrui at 2018/1/10/0010 10:00
 */
public class DeviceDataStat {
    private String deviceId;
    private int ysTds;
    private int csTds;
    private int jsTotal;
    private int jhsSize;
    private int cjsSize;
    private int ysSize;
    private int zsTime;
    private String createDate;
    private String token;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getYsTds() {
        return ysTds;
    }

    public void setYsTds(int ysTds) {
        this.ysTds = ysTds;
    }

    public int getCsTds() {
        return csTds;
    }

    public void setCsTds(int csTds) {
        this.csTds = csTds;
    }

    public int getJsTotal() {
        return jsTotal;
    }

    public void setJsTotal(int jsTotal) {
        this.jsTotal = jsTotal;
    }

    public int getJhsSize() {
        return jhsSize;
    }

    public void setJhsSize(int jhsSize) {
        this.jhsSize = jhsSize;
    }

    public int getCjsSize() {
        return cjsSize;
    }

    public void setCjsSize(int cjsSize) {
        this.cjsSize = cjsSize;
    }

    public int getYsSize() {
        return ysSize;
    }

    public void setYsSize(int ysSize) {
        this.ysSize = ysSize;
    }

    public int getZsTime() {
        return zsTime;
    }

    public void setZsTime(int zsTime) {
        this.zsTime = zsTime;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "DeviceId:" + getDeviceId() + ", SourceTDS:" + getYsTds() + ", PureWaterTDS:" + getCsTds() +
                ", TotalPureWater:" + getJsTotal() + ", JhsWaterSize:" + getJhsSize() +
                ", PureWaterSize:" + getCjsSize() + ", SourceWaterSize:" + getYsSize() +
                ", PuringWaterState:" + (getZsTime() == 0 ? "start" : "end");
    }
}
