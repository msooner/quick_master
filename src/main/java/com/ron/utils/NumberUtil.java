package com.ron.utils;

import org.springframework.util.DigestUtils;

/**
 * 数据工具类
 *
 * @author Ron 2019/08/01
 */
public class NumberUtil {

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
        return DigestUtils.md5DigestAsHex((encodeString + "/" + salt).getBytes());
    }
}
