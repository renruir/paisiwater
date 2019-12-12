package com.paisiwater.controller;

import com.paisiwater.api.model.*;
import com.paisiwater.engin.InfoSCM;
import com.paisiwater.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("orderservice")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);


    @RequestMapping(value = "createOrderService",method = RequestMethod.POST)
    @ResponseBody
    public String createOrderService(String orderno,String servicetype,String openid,String receiver_name,
                                     String receiver_mobile,String receiver_country,String receiver_state,String receiver_city,
                                     String receiver_district,String receiver_address,String receiver_zip,String[] model,String[] modelName,String[] qty,String servicedate,
                                     String servicedesc,String remark,String functionName) {
        try {
            InfoSCM infoSCM = new InfoSCM();
            String jsonObj = infoSCM.createOrder(orderno,servicetype,openid,receiver_name,receiver_mobile,receiver_country,receiver_state,receiver_city,receiver_district,receiver_address,receiver_zip,model,modelName,qty,servicedate,
                    servicedesc,remark,"ecp.scm.salesorder.orderservicecreate");
            return jsonObj;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }


    @RequestMapping(value = "getOrderList")
    @ResponseBody
    public String getOrderList(String tid,String orderno,String salesid,String mobile,String functionName){
        try{
            InfoSCM infoSCM = new InfoSCM();
            String jsonObj = infoSCM.getorderinfo(tid,orderno,salesid,mobile,"ecp.scm.salesorder.getorderinfo");
            return jsonObj;
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }

    @RequestMapping(value = "getProcessList")
    @ResponseBody
    public String getProcessList(String tid,String orderno,String salesid,String serviceno,String functionName){
        try{
            InfoSCM infoSCM = new InfoSCM();
            String jsonObj = infoSCM.getProcessInfo(tid,orderno,salesid,serviceno,"ecp.scm.salesorder.getorderservicestep");
            return jsonObj;
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }


}
