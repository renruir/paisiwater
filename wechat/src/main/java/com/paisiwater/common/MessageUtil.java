package com.paisiwater.common;

import com.paisiwater.api.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuhaichao on 2016/11/15.
 */
public class MessageUtil {

	private static final Logger logger = LoggerFactory.getLogger(MessageUtil.class);

	public static final String RESP_MESSAGE_TYPE_TEXT = "text";


	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";


	public static final String RESP_MESSAGE_TYPE_NEWS = "news";


	public static final String REQ_MESSAGE_TYPE_TEXT = "text";


	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	public static final String REQ_MESSAGE_TYPE_VIDEO = "video";

	public static final String REQ_MESSAGE_TYPE_SHORTVIDEO = "shortvideo";


	public static final String REQ_MESSAGE_TYPE_LINK = "link";


	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";


	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";


	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	public static final String REQ_MESSAGE_TYPE_DEVICE_EVENT = "device_event";

	public static final String REQ_MESSAGE_TYPE_DEVICE_STATUS= "device_status";

	public static final String EVENT_TYPE_BIND = "bind";

	public static final String EVENT_TYPE_UNBIND = "unbind";

	public static final String EVENT_TYPE_SET = "set";

	public static final String  SUBSCRIBE_STATUS = "subscribe_status";

	public static final String  UNSUBSCRIBE_STATUS = "unsubscribe_status";

	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";


	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

	//订单通知
	public static final String EVENT_TYPE_ORDER = "merchant_order";


	public static final String EVENT_TYPE_CLICK = "CLICK";

	public static final String EVENT_TYPE_VIEW = "VIEW";


	/**
	 * 解析微信发来的消息
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXmlByString(String msg) throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();


		Document document = DocumentHelper.parseText(msg);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element e : elementList)  {
			map.put(e.getName(), e.getText());
			//logger.error(e.getName()+":"+e.getText());
		}


		return map;
	}


	/**
	 * 文本消息对象转换成xml
	 *
	 * @param textMessage 文本消息对象
	 * @return xml
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * 设备消息对象转换成xml
	 *
	 * @param deviceMessage 文本消息对象
	 * @return xml
	 */
	public static String deviceMessageToXml(DeviceMessage deviceMessage) {
		xstream.alias("xml", deviceMessage.getClass());
		String devicemsg = xstream.toXML(deviceMessage);
		return devicemsg;
	}




	/**
	 * 图文消息对象转换成xml
	 *
	 * @param newsMessage 图文消息对象
	 * @return xml
	 */
	public static String newsMessageToXml(NewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}


	/**
	 * 错误消息
	 * @param errorMsg
	 * @return
	 */
	public static String errorMessageToXml(ErrorMessage errorMsg){
		xstream.alias("xml", ErrorMessage.class);
		return xstream.toXML(errorMsg);
	}

	/**
	 * 扩展xstream，使其支持CDATA块
	 *
	 * @date 2013-05-19
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;

				@SuppressWarnings("unchecked")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});
}
