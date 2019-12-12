package com.paisiwater.model;

/**
 * create by renrui on 2018/1/10/ 10:00
 */
public class WxJsApiTicket {

    private String appId;

    private String token;

    private String jsApiTicket;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getJsApiTicket() {
        return jsApiTicket;
    }

    public void setJsApiTicket(String jsApiTicket) {
        this.jsApiTicket = jsApiTicket;
    }
}
