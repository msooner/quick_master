package com.ron.utils;

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
}
