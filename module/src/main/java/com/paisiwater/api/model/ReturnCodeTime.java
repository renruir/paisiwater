package com.paisiwater.api.model;

/**
 * Created by yuhaichao on 2016/11/17.
 */
public class ReturnCodeTime {
	private int errorCode;
	private long currentTime;

	public long getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(long currentTime) {
		this.currentTime = currentTime;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
