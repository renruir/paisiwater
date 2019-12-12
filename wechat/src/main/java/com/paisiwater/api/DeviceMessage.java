package com.paisiwater.api;

/**
 * Created by yuhaichao on 2016/11/15.
 */
public class DeviceMessage extends BaseMessage{
		private String DeviceType;

		private String DeviceID;

		private String DeviceStatus;

		public String getDeviceType() {
			return DeviceType;
		}

		public void setDeviceType(String deviceType) {
			DeviceType = deviceType;
		}

		public String getDeviceID() {
			return DeviceID;
		}

		public void setDeviceId(String deviceId) {
			DeviceID = deviceId;
		}

		public String getDeviceStatus() {
			return DeviceStatus;
		}

		public void setDeviceStatus(String deviceStatus) {
			DeviceStatus = deviceStatus;
		}

}
