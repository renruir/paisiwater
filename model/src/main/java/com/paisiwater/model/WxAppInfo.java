package com.paisiwater.model;

/**
 * Created by renrui on 2018/1/10.
 */
public class WxAppInfo {

	private String ghId;

	private String appId;

	private String appSecret;

	private String token;

	private String gzMsg;

	public String getGhId() {
		return ghId;
	}

	public void setGhId(String ghId) {
		this.ghId = ghId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getGzMsg() {
		return gzMsg;
	}

	public void setGzMsg(String gzMsg) {
		this.gzMsg = gzMsg;
	}
}
