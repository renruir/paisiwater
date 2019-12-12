package com.paisiwater.engin;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;

public class InfoSCM {
    /**
     * 获取订单信息(根据tid)
     * 根据订单号/手机号获取订单信息
     * @return
     */
    public String getorderinfo(String tid,String orderno,String salesid,String mobile,String functionName) {
        ArrayList<JSONObject> paramList = new ArrayList<JSONObject>();
//        JSONObject paramsInfoJson = new JSONObject();
//        JSONObject paramsInfoJson2 = new JSONObject();
//        JSONObject paramsInfoJson3 = new JSONObject();
//        JSONObject paramsInfoJson4 = new JSONObject();
//        paramsInfoJson.put("tid",tid);
//        paramList.add(paramsInfoJson);
//        paramsInfoJson2.put("orderno",orderno);
//        paramList.add(paramsInfoJson2);
//        paramsInfoJson3.put("salesid",salesid);
//        paramList.add(paramsInfoJson3);
//        paramsInfoJson4.put("mobile",mobile);
//        paramList.add(paramsInfoJson4);



        if (SCMUtil.strIsNotNull(tid)) {
            JSONObject paramsInfoJson = new JSONObject();
            paramsInfoJson.put("tid", tid);
            paramList.add(paramsInfoJson);
        }
        if (SCMUtil.strIsNotNull(orderno)) {
            JSONObject paramsInfoJson = new JSONObject();
            paramsInfoJson.put("orderno", orderno);
            paramList.add(paramsInfoJson);
        }
        if (SCMUtil.strIsNotNull(salesid)) {
            JSONObject paramsInfoJson = new JSONObject();
            paramsInfoJson.put("salesid", salesid);
            paramList.add(paramsInfoJson);
        }
        if (SCMUtil.strIsNotNull(mobile)) {
            JSONObject paramsInfoJson = new JSONObject();
            paramsInfoJson.put("mobile", mobile);
            paramList.add(paramsInfoJson);
        }
        if (paramList.size() != 0) {
            String resu = SCMUtil.getResult(functionName, paramList);
            return resu;

        }
        return null;
    }


    /**
     * 创建服务单
     * @return
     */
    public String createOrder(String orderno,String servicetype,String openid,String receiver_name,
                            String receiver_mobile,String receiver_country,String receiver_state,String receiver_city,
                            String receiver_district,String receiver_address,String receiver_zip,String[] model,String[] modelName,String[] qty,String servicedate,
                            String servicedesc,String remark,String functionName ){
                            JSONObject paramJson = new JSONObject();
                            try {
                                paramJson.put("orderno",orderno);
                                paramJson.put("servicetype",servicetype);
                                paramJson.put("openid",openid);
                                paramJson.put("receiver_name",receiver_name);
                                paramJson.put("receiver_mobile",receiver_mobile);
                                paramJson.put("receiver_country",receiver_country);
                                paramJson.put("receiver_state",receiver_state);
                                paramJson.put("receiver_city",receiver_city);
                                paramJson.put("receiver_district",receiver_district);
                                paramJson.put("receiver_address",receiver_address);
                                paramJson.put("receiver_zip",receiver_zip);
                                //paramJson.put("model",model);
                                paramJson.put("servicedate",servicedate);
                                paramJson.put("servicedesc",servicedesc);
                                paramJson.put("remark",remark);

                                if(qty!=null && qty.length>0){
                                    ArrayList<JSONObject> detailList = new ArrayList<JSONObject>();
                                    for(int i=0;i<qty.length;i++){
                                        int qtyInt = Integer.parseInt(qty[i]);
                                        //if(qtyInt>0){
                                            JSONObject detailJson = new JSONObject();
                                        if(model!= null && i<model.length) {
                                            detailJson.put("product_code", model[i]);
                                        }else{
                                            detailJson.put("product_code", "");
                                        }

                                        if(modelName!=null && i<modelName.length) {
                                            detailJson.put("product_name", modelName[i]);
                                        }else{
                                            detailJson.put("product_name", "");
                                        }
                                            detailJson.put("qty",qtyInt);
                                            detailList.add(detailJson);
                                        //}
                                    }

                                    if(detailList.size()>0){
                                        paramJson.put("detail",detailList);
                                    }
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                                String paramStr=paramJson.toString();
                                JSONObject finalParamJson = new JSONObject();
                                finalParamJson.put("content",paramStr);
                                ArrayList<JSONObject> paramList = new ArrayList<JSONObject>();
                                paramList.add(finalParamJson);

                String resu=SCMUtil.getResult(functionName, paramList);
                return resu;
    }


    /**
     * 获取服务单进度信息
     * @return
     */
    public String getProcessInfo(String tid,String orderno,String salesid,String serviceno,String functionName ){
        ArrayList<JSONObject> paramList = new ArrayList<JSONObject>();
        if (SCMUtil.strIsNotNull(tid)) {
            JSONObject paramsInfoJson = new JSONObject();
            paramsInfoJson.put("tid", tid);
            paramList.add(paramsInfoJson);
        }
        if (SCMUtil.strIsNotNull(orderno)) {
            JSONObject paramsInfoJson = new JSONObject();
            paramsInfoJson.put("orderno", orderno);
            paramList.add(paramsInfoJson);
        }
        if (SCMUtil.strIsNotNull(salesid)) {
            JSONObject paramsInfoJson = new JSONObject();
            paramsInfoJson.put("salesid", salesid);
            paramList.add(paramsInfoJson);
        }
        if (SCMUtil.strIsNotNull(serviceno)) {
            JSONObject paramsInfoJson = new JSONObject();
            paramsInfoJson.put("serviceno", serviceno);
            paramList.add(paramsInfoJson);
        }



//        JSONObject paramsInfoJson = new JSONObject();
//        JSONObject paramsInfoJson2 = new JSONObject();
//        JSONObject paramsInfoJson3 = new JSONObject();
//        JSONObject paramsInfoJson4 = new JSONObject();
//        paramsInfoJson.put("tid",tid);
//        paramList.add(paramsInfoJson);
//        paramsInfoJson2.put("orderno",orderno);
//        paramList.add(paramsInfoJson2);
//        paramsInfoJson3.put("salesid",salesid);
//        paramList.add(paramsInfoJson3);
//        paramsInfoJson4.put("serviceno",serviceno);
//        paramList.add(paramsInfoJson4);
        String resu=SCMUtil.getResult(functionName, paramList);
        return resu;
    }



    public static void main(String[] args) {
        InfoSCM a=new InfoSCM();
//       获取订单信息
        String aaa=a.getorderinfo("","SO0000136878","","18633274368","ecp.scm.salesorder.getorderinfo");

//创建服务单
//        JSONObject bbb=a.createOrder("SO0000113706","服务dafg类型","455656","zzz","246246464","",
//                "海", "上市" ,"松区","九亭镇52号105室","1600",
//                "","","","","ecp.scm.salesorder.orderservicecreate");


//        获取服务单进度信息
//      JSONObject ccc=a.getProcessInfo( "", "", "", "ES0000034567","ecp.scm.salesorder.getorderservicestep");
    }


}
