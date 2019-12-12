package com.paisi.utils;

/**
 * Created by yuhaichao on 2016/11/17.
 */
public class StrUtil {

	public static int stringToInt(String str, int defaultVal) {
		int val;
		try {
			val = Integer.parseInt(str);
		} catch (Exception e) {
			val = defaultVal;
		}
		return val;
	}


	public static String objectToString(Object o){
		if(o!=null && !o.equals("")){
			return String.valueOf(o);
		}
		return "";
	}

	public static boolean strIsNull(String str){
		if(str==null || "".equals(str)){
			return true;
		}else{
			return false;
		}
	}

	public static boolean strIsNotNull(String str){
		if(str!=null && !"".equals(str)){
			return true;
		}else{
			return false;
		}
	}

}
