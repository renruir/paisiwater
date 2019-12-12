package com.paisiwater.api.model;

/**
 * create by renrui at 2018/1/10/0010 10:00
 */
public class DeviceVersionUpdate {
	private String appId;
	private String deviceType;
	private String model;
	private String version;
	private String pkgSize;
	private String md5;
	private String downloadUrl;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPkgSize() {
		return pkgSize;
	}

	public void setPkgSize(String pkgSize) {
		this.pkgSize = pkgSize;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
}
