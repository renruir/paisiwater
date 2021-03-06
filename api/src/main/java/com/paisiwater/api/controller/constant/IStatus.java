package com.paisiwater.api.controller.constant;

/**
 * <p>
 * REST API 错误码接口
 * </p>
 *
 * @package: com.xkcoding.rbac.security.common
 * @description: REST API 错误码接口
 * @author: ryan
 * @date: Created in 2018-12-07 14:35
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 */
public interface IStatus {

    /**
     * 状态码
     *
     * @return 状态码
     */
    Integer getCode();

    /**
     * 返回信息
     *
     * @return 返回信息
     */
    String getMessage();

}