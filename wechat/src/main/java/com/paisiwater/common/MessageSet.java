package com.paisiwater.common;

import com.paisiwater.api.TextMessage;

import java.util.Date;

/**
 * Created by yuhaichao on 2016/11/16.
 */
public class MessageSet {

    public static String sendTextMessage(String fromUserName,
                                         String toUserName, String message) {
        // 回复文本消息
        TextMessage textMessage = new TextMessage();
        textMessage.setToUserName(fromUserName);
        textMessage.setFromUserName(toUserName);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        textMessage.setContent(message);
        String respMessage = MessageUtil.textMessageToXml(textMessage);
        return respMessage;
    }

//    public static String sendNewsMessage(){
//
//    }

}
