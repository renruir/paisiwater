package com.paisiwater.service;

import com.alibaba.fastjson.JSON;
import com.paisiwater.api.model.BaseTemplateStruct;
import com.paisiwater.api.model.GeneralDeviceInfo;
import com.paisiwater.api.model.ModelMsg;
import com.paisiwater.service.constant.ServiceConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class GeneralScanService {

    private static final Logger logger = LoggerFactory.getLogger(GeneralScanService.class);

    @Autowired
    private WeixinService weixinService;

    public void scanGeneralDeviceInfo() {
        List<GeneralDeviceInfo> generalDeviceInfos = new ArrayList<GeneralDeviceInfo>();
        try {
            generalDeviceInfos = weixinService.scanGeneralDeviceInfo();
            if (generalDeviceInfos.size() == 0) {
                logger.info("====It has not yet expired filter!====");
                return;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        for (GeneralDeviceInfo info : generalDeviceInfos) {
            if (info.getOpen_id().isEmpty()) {
                return;
            }
            logger.info("resetDate: " + info.getReset_date() + ",name:" + info.getNick_name());

            String keyword1 = new StringBuilder("前置过滤器（名称：" + info.getNick_name() + ")").toString();
            String keyword2 = new StringBuilder("您设定的冲洗周期是" + info.getReminder_circle() + "天"
                    + ", 距离上次冲洗已经有" + calcNow2Last(info.getReset_date()) + "天").toString();

            Map<String, BaseTemplateStruct> map = new HashMap<String, BaseTemplateStruct>();
            map.put("first", new BaseTemplateStruct("您的前置过滤需要冲洗！", "#0d0c0c"));
            map.put("keyword1", new BaseTemplateStruct(keyword1, "#00008B"));
            map.put("keyword2", new BaseTemplateStruct(keyword2, "#00008B"));
            map.put("remark", new BaseTemplateStruct("请及时冲洗滤芯，以免影响水质健康！", "#0d0c0c"));
            packageModelMsg(info.getOpen_id(), map);
        }
        return;
    }

    public void packageModelMsg(String openID, Map<String, BaseTemplateStruct> data) {
        logger.info("openId: " + openID);
        ModelMsg modelMsg = new ModelMsg();
        modelMsg.setTemplate_id(ServiceConstant.FILTER_RINSE_NOTICE_MODEL_ID);
        modelMsg.setTouser(openID);
        modelMsg.setUrl("http://weixin.tejien.com/web/wechat/index.html");
        modelMsg.setTopcolor("#FF0000");
        modelMsg.setData(data);
        weixinService.sendTemplateMsg(JSON.toJSONString(modelMsg));
    }

    private static int calcNow2Last(String last) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            ParsePosition pos = new ParsePosition(0);
            Date lastDate = formatter.parse(last, pos);
            long now = new Date().getTime();
            int days = (int) ((now - lastDate.getTime()) / (1000 * 60 * 60 * 24));
            return days;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return 0;
    }

    public static void main(String[] args) {
        System.out.println(calcNow2Last("2018-09-02") + "");
    }
}
