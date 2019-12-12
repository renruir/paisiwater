package com.paisiwater.engin;

import com.alibaba.fastjson.JSONObject;
import com.paisi.utils.HttpClientUtil;
import com.paisi.utils.MD5Encode;
import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Decoder;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;


public class SCMUtil {


    public static String AppKey="5b043c2ca1fe459f6422bf6d";
    public static String Secretkey="5b043c2ca1fe459f6422bf70";
    public static String SessionKey="5b043c2da1fe459f6422bf73";


    /**
     * 对字符串进行加密获取SecretKey
     */
     public static JSONObject getSecretKey(String functionName,List paramList){
         if(strIsNotNull(functionName) && paramList.size()!=0 && paramList.size()>1){
             String paramStr=paramToArrays(paramList);
             String timeStemp=String.valueOf(System.currentTimeMillis()/ 1000);
             String a="_AppKey"+AppKey+"_AsyncFalse_FunctionName"+functionName+
                     "_SessionKey"+SessionKey+"_TimeStamp"+timeStemp+"_Topic"+paramStr;
             String keyStr=Secretkey+a+Secretkey;
             String StrMD5= MD5Encode.encode(keyStr);
             String Sign=StrMD5.toUpperCase();
             //封装参数，进行请求接口
             JSONObject infoJson = new JSONObject();
             infoJson.put("_Async","False");
             infoJson.put("_FunctionName",functionName);
             infoJson.put("_SessionKey",SessionKey);
             infoJson.put("_TimeStamp",timeStemp);
             infoJson.put("_Topic","");
             infoJson.put("_AppKey",AppKey);
             infoJson.put("_Sign",Sign);
             infoJson.put("Params",paramList );
             System.out.println(infoJson);
             return infoJson;
         }else if (strIsNotNull(functionName) &&  paramList.size()==1){
             String paramStr=paramToArrays2(paramList);
             String timeStemp=String.valueOf(System.currentTimeMillis()/ 1000);
             String a="_AppKey"+AppKey+"_AsyncFalse_FunctionName"+functionName+
                     "_SessionKey"+SessionKey+"_TimeStamp"+timeStemp+"_Topic"+paramStr;
             String keyStr=Secretkey+a+Secretkey;
             String StrMD5=MD5Encode.encode(keyStr);
             String Sign=StrMD5.toUpperCase();
             //封装参数，进行请求接口
             JSONObject infoJson = new JSONObject();
             infoJson.put("_Async","False");
             infoJson.put("_FunctionName",functionName);
             infoJson.put("_SessionKey",SessionKey);
             infoJson.put("_TimeStamp",timeStemp);
             infoJson.put("_Topic","");
             infoJson.put("_AppKey",AppKey);
             infoJson.put("_Sign",Sign);
             infoJson.put("Params",paramList );
             System.out.println(infoJson);
             return infoJson;
         }else {
             return null;
         }

     }


    /**
     * 对应用参数按照字段名排序,返回排序好的组装字符串(应用参数只有一个，值是json字符串)
     * @param paramList
     * @return
     */
    public static String  paramToArrays2(List paramList) {
        if (paramList.size() != 0) {
            System.out.println(paramList);
            String paramStr = "";
            JSONObject data=(JSONObject) paramList.get(0);
            String keyName=data.keySet().toString();
            paramStr = keyName.substring(1, keyName.length()-1 );
            System.out.println(paramStr);
            String valueName=data.values().toString();
            valueName = valueName.substring(1, valueName.length() - 1);
            System.out.println(valueName);
            String finStr=paramStr+valueName;
            return finStr;
        }
        return null;
    }



    /**
     * 对应用参数按照字段名排序,返回排序好的组装字符串
     * @param paramList
     * @return
     */
    public static String  paramToArrays(List paramList) {
        if (paramList.size() != 0) {
            String paramStr = "";
            for (int i = 0; i < paramList.size(); i++) {
                paramStr = paramStr + paramList.get(i) + ",";
            }
            paramStr = paramStr.substring(0, paramStr.length() - 1);
            System.out.println(paramStr);
            String[] paramStrArr = paramStr.split(",");
            Arrays.sort(paramStrArr);

            String finStr="";
            for (int j = 0; j <paramStrArr.length ; j++) {
                JSONObject json =(JSONObject) JSONObject.parse(paramStrArr[j]);
                String keyName=json.keySet().toString();
                keyName = keyName.substring(1, keyName.length() - 1);
                String valueName=json.values().toString();
                valueName = valueName.substring(1, valueName.length() - 1);
                finStr=finStr+keyName+valueName;

            }
            System.out.println(finStr);
            return finStr;
        }
        return null;
    }



    public static String getResult(String functionName,List paramList ){
        //获取SecretKey并且进行数据封装
        String SecretKey=SCMUtil.getSecretKey(functionName, paramList).toString();
        try {
            String urlString = URLEncoder.encode(SecretKey, "utf-8");

            String stringStr = HttpClientUtil.httpPostRequest("http://112.124.55.181:30005/GateWay",urlString);
            JSONObject returnStr = (JSONObject) JSONObject.parse(stringStr);
            System.out.println(returnStr);
            String msg=returnStr.getString("Msg");
//            System.out.println(msg);

            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = decoder.decodeBuffer(msg);
            String result = new String(b, "utf-8");
            System.out.println(result);
//            final Base64.Decoder decoder = Base64.getDecoder();
//            System.out.println(new String(decoder.decode(msg), "UTF-8"));

//            byte[] asBytes = Base64.getDecoder().decode(msg);
//            String result = new String(asBytes, "utf-8");
//            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 判断字符串是否不为空
     * @param str
     * @return
     */
    public static boolean strIsNotNull(String str){
        if(str!=null && !"".equals(str)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean strIsNull(String str){
        if(str==null || "".equals(str)){
            return true;
        }else{
            return false;
        }
    }



//    /**
//     * MD5编码
//     * @param origin 原始字符串
//     * @return 经过MD5加密之后的结果
//     */
//    public static String encode(String origin) {
//        String resultString = null;
//        try {
//            resultString = origin;
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            md.update(resultString.getBytes("UTF-8"));
//            resultString = byteArrayToHexString(md.digest());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return resultString;
//    }
}
