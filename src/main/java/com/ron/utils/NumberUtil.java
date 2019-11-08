package com.ron.utils;

/**
 * 数据工具类
 *
 * @author Ron 2019/08/01
 */
public class NumberUtil {

    /**
     * 判断字符串是否是数字型字符串
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
}
