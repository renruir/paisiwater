package com.paisiwater.handler;

import com.paisiwater.common.WeixinUtil;
import com.paisiwater.execute.msg.WeixinMsgExecute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yuhaichao on 2016/11/15.
 */
public abstract class BaseDataProcess {

	private static final Logger logger = LoggerFactory.getLogger(BaseDataProcess.class);

	/**
	 *
	 * @param signature 微信加密签名
	 * @param timestamp 时间戳
	 * @param nonce 随机数
	 * @param echostr 随机字符串
	 * @return
	 */
	public String token(String signature,String timestamp,String nonce,String echostr,String token) {
		try {

			// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
			if (WeixinUtil.checkSignature(signature, timestamp, nonce, token)) {
				return echostr;
			}
		} catch (Exception e) {
			logger.error("token:" + e);
		}
		return "";
	}

	public abstract String processPost(String requestMsg,WeixinMsgExecute weixinMsgExecute);

}
