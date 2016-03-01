package com.web.core.tool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;

/**
 * Created by shenzhiqiang on 16/2/7.
 */
public class ToolClass {

    public static String StrToMd5L32(String str){
        String reStr = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(str.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bytes){
                int bt = b&0xff;
                if (bt < 16){
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(bt));
            }
            reStr = stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reStr;
    }

    public static String getImgFilename(String filename) {
        String suffix = filename.substring(filename.lastIndexOf(".") + 1);
        return ToolClass.StrToMd5L32(filename) + String.valueOf(System.currentTimeMillis()) + "." + suffix;
    }

    public static String getSidFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie c = cookies[i];
                if ("sid".equals(c.getName())) {
                    return c.getValue();
                }
            }
        }
        return "";
    }

}
