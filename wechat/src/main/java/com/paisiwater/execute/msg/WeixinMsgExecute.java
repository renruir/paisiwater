package com.paisiwater.execute.msg;

import java.util.Map;

/**
 * Created by yuhaichao on 2016/11/15.
 */
public interface WeixinMsgExecute {

	/**
	 * 关注消息
	 * @param requestMap
	 * @return
	 */
	public String subscribeMsg(Map<String, String> requestMap);

	/**
	 * 取消关注消息
	 * @param requestMap
	 * @return
	 */
	public String unsubscribeMsg(Map<String, String> requestMap);

	/**
	 * 绑定消息
	 * @param requestMap
	 * @return
	 */
	public String bindMsg(Map<String, String> requestMap);

	/**
	 * 解绑消息
	 * @param requestMap
	 * @return
	 */
	public String unbindMsg(Map<String, String> requestMap);




}
