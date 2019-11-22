package com.ron.utils;

import org.springframework.util.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author Ron 2019/11/12
 */
public class StringUtil {

    /**
     * 生成指定长度的随机字符串
     *
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
     *
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
     *
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

    /**
     * 判断是否是邮箱地址
     *
     * @param string
     * @return
     */
    public static boolean isEmail(String string) {
        if (string == null)
            return false;
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches())
            return true;
        else
            return false;
    }

    /**
     * MD5加密算法
     *
     * @param input
     * @return
     */
    public static String stringMD5(String input) {

        try {
            // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 输入的字符串转换成字节数组
            byte[] inputByteArray = input.getBytes();
            // inputByteArray是输入字符串转换得到的字节数组
            messageDigest.update(inputByteArray);
            // 转换并返回结果，也是字节数组，包含16个元素
            byte[] resultByteArray = messageDigest.digest();
            // 字符数组转换成字符串返回
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String byteArrayToHex(byte[] byteArray) {
        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };
        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray =new char[byteArray.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b& 0xf];
        }
        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }

    /**
     * 防止SQL注入(此方法后台项目比较适合，如果是前台可以去掉些过滤关键字)
     *
     * @param str
     * @return
     */
    public static boolean isSqlInjection(String str) {
        str = str.toLowerCase();//统一转为小写
        String badString = "'|and|exec|execute|insert|select|delete|update|count|drop|*|%|chr|mid|master|truncate|" +
                "char|declare|sitename|net user|xp_cmdshell|;|or|-|+|,|like'|and|exec|execute|insert|create|drop|" +
                "table|from|grant|use|group_concat|column_name|" +
                "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|" +
                "chr|mid|master|truncate|char|declare|or|;|-|--|+|,|like|//|/|%|#";//过滤掉的sql关键字，可以手动添加
        String[] badStrings = badString.split("\\|");
        for (int i = 0; i < badStrings.length; i++) {
            if (str.indexOf(badStrings[i]) >= 0) {
                return true;
            }
        }
        return false;
    }
}
