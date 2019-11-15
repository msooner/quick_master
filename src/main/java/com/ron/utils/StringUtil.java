package com.ron.utils;

import org.springframework.util.DigestUtils;

import java.util.Random;

/**
 * 字符串工具类
 *
 * @author Ron 2019/11/12
 */
public class StringUtil {

    /**
     * 生成指定长度的随机字符串
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        if(length <= 0) {
            return "";
        }
        String tempString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int tempLength = tempString.length();
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int numberIndex = random.nextInt(tempLength);
            sb.append(tempString.charAt(numberIndex));
        }
        return sb.toString();
    }

    /**
     * 判断字符串是否是数字型字符串
     * @param numberStr
     * @return
     */
    public static boolean isNumberString(String numberStr) {
        if (numberStr == null || numberStr == "") {
            return false;
        }
        for (int i = numberStr.length(); --i >= 0;) {
            if (!Character.isDigit(numberStr.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 生成MD5值
     * @param encodeString
     * @param salt
     * @return
     */
    public static String getMD5String(String encodeString, String salt) {
        if (encodeString == null || "".equals(encodeString)) {
            return "";
        }
        if (salt == null) {
            salt = "";
        }
        return DigestUtils.md5DigestAsHex((encodeString + "/" + salt).getBytes()).toString();
    }
}
