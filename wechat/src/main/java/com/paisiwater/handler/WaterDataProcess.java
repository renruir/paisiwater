package com.paisiwater.handler;

import com.paisiwater.execute.msg.WeixinMsgExecute;
import com.paisiwater.common.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by yuhaichao on 2016/11/15.
 */
public class WaterDataProcess extends BaseDataProcess{

	private static final Logger logger = LoggerFactory.getLogger(WaterDataProcess.class);

	@Override
	public String processPost(String requestMsg,WeixinMsgExecute weixinMsgExecute) {
		String respContent = "";
		try{
//			logger.info("rec msg: "+ requestMsg);
			Map<String, String> requestMap = MessageUtil.parseXmlByString(requestMsg);
			String fromUserName = requestMap.get("FromUserName");
			String toUserName = requestMap.get("ToUserName");
			String msgType = requestMap.get("MsgType");

			if(MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgType)){
				String eventType = requestMap.get("Event");
				if(MessageUtil.EVENT_TYPE_SUBSCRIBE.equals(eventType)){
					respContent = weixinMsgExecute.subscribeMsg(requestMap);
				}else if(MessageUtil.EVENT_TYPE_UNSUBSCRIBE.equals(eventType)){
					respContent = weixinMsgExecute.unsubscribeMsg(requestMap);
				}
			}else if(MessageUtil.REQ_MESSAGE_TYPE_DEVICE_EVENT.equals(msgType)){
				String eventType = requestMap.get("Event");
				if(MessageUtil.EVENT_TYPE_BIND.equals(eventType)){
					respContent = weixinMsgExecute.bindMsg((requestMap));
				}else if(MessageUtil.EVENT_TYPE_UNBIND.equals(eventType)){
					respContent = weixinMsgExecute.unbindMsg(requestMap);
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return respContent;
	}
}
