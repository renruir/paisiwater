package com.paisiwater.api.controller.constant;

import java.io.Serializable;

/**
 * @author renrui
 * @date 2020/5/1 0001 10:51
 */
public class ReturnMsg implements Serializable {
    private int code;
    private String message;
    private Object data;

    public ReturnMsg(){

    }

    /**
     * 全参构造函数
     *
     * @param code    状态码
     * @param message 返回内容
     * @param data    返回数据
     */
    private ReturnMsg(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 构造一个自定义的API返回
     *
     * @param code    状态码
     * @param message 返回内容
     * @param data    返回数据
     * @return ApiResponse
     */
    public static ReturnMsg res(Integer code, String message, Object data) {
        return new ReturnMsg(code, message, data);
    }

    /**
     * 构造一个成功且不带数据的API返回
     *
     * @return ApiResponse
     */
    public static ReturnMsg resSuccess() {
        return resSuccess(null);
    }

    /**
     * 构造一个成功且带数据的API返回
     *
     * @param data 返回数据
     * @return ApiResponse
     */
    public static ReturnMsg resSuccess(Object data) {
        return resStatus(Status.SUCCESS, data);
    }

    /**
     * 构造一个成功且自定义消息的API返回
     *
     * @param message 返回内容
     * @return ApiResponse
     */
    public static ReturnMsg resMessage(String message) {
        return res(Status.SUCCESS.getCode(), message, null);
    }

    /**
     * 构造一个有状态的API返回
     *
     * @param status 状态 {@link Status}
     * @return ApiResponse
     */
    public static ReturnMsg resStatus(Status status) {
        return resStatus(status, null);
    }

    /**
     * 构造一个有状态且带数据的API返回
     *
     * @param status 状态 {@link IStatus}
     * @param data   返回数据
     * @return ApiResponse
     */
    public static ReturnMsg resStatus(IStatus status, Object data) {
        return res(status.getCode(), status.getMessage(), data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
