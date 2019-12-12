package com.paisi.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yuhaichao on 2016/8/29.
 */
public class CookieUtil {

    public static String getCookie(String key,HttpServletRequest request){
        String keyVal="";
        try{
            Cookie[] cookies = request.getCookies();

            if (cookies != null && cookies.length > 0 && key!=null && !"".equals(key)) {
                for (Cookie cookie : cookies) {
                    if(key.equalsIgnoreCase(cookie.getName())){
                        keyVal = cookie.getValue();
                        break;
                    }
                }
            }
        }catch(Exception e){
        }
        return keyVal;
    }

    public static void setCookie(String key,String value,HttpServletResponse response){
        try{
            if(key!=null && !"".equals(key)){
                Cookie  cookie = new Cookie(key,value);
                cookie.setPath("/");
                cookie.setMaxAge(24*60*60*365);
                response.addCookie(cookie);
            }
        }catch(Exception e){
        }
    }

    public static void deleteCookie(String key,HttpServletResponse response){
        try{
            if(key!=null && !"".equals(key)){
                Cookie  cookie = new Cookie(key,null);
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }catch(Exception e){
        }
    }
}
