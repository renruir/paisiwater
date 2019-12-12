package com.paisiwater.constant;

/**
 * Created by yuhaichao on 2016/11/16.
 */
public interface WeixinConstant {
	//get请求
	String GET = "GET";

	//post请求
	String POST = "POST";

	String OAUTH_URL="https://open.weixin.qq.com/connect/oauth2/authorize?appid=${appid}&redirect_uri=${redirect_uri}&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
}
