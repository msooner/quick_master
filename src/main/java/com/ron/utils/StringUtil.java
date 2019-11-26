package com.ron.utils;

import org.springframework.util.DigestUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author Ron 2019/11/12
 */
public class StringUtil {

    private static final char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

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
        String regEx1 = "^([a-z0-9A-Z]+[_|-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
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
        String badString = "'|exec|execute|insert|select|delete|update|count|drop|%|master|" +
                "declare|sitename|net user|xp_cmdshell|like'|create|drop|" +
                "table|from|grant|group_concat|column_name|" +
                "information_schema.columns|table_schema|union|whereorder|*|" +
                "master|truncate|declare|;|--|+|,|like|//|/|%|#";//过滤掉的sql关键字，可以手动添加
        String[] badStrings = badString.split("\\|");
        for (int i = 0; i < badStrings.length; i++) {
            if (str.indexOf(badStrings[i]) >= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 生成UUID字符串
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * base64加密
     *
     * @param data
     * @return
     */
    public static String base64Encode(byte[] data) {
        int start = 0;
        int len = data.length;
        StringBuffer buf = new StringBuffer(data.length * 3 / 2);

        int end = len - 3;
        int i = start;
        int n = 0;

        while (i <= end) {
            int d = ((((int) data[i]) & 0x0ff) << 16) | ((((int) data[i + 1]) & 0x0ff) << 8) | (((int) data[i + 2]) & 0x0ff);

            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append(legalChars[(d >> 6) & 63]);
            buf.append(legalChars[d & 63]);

            i += 3;

            if (n++ >= 14) {
                n = 0;
                buf.append(" ");
            }
        }

        if (i == start + len - 2) {
            int d = ((((int) data[i]) & 0x0ff) << 16) | ((((int) data[i + 1]) & 255) << 8);

            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append(legalChars[(d >> 6) & 63]);
            buf.append("=");
        } else if (i == start + len - 1) {
            int d = (((int) data[i]) & 0x0ff) << 16;

            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append("==");
        }

        return buf.toString();
    }

    /**
     * base64解密
     *
     * @param c
     * @return
     */
    private static int base64Decode(char c) {
        if (c >= 'A' && c <= 'Z')
            return ((int) c) - 65;
        else if (c >= 'a' && c <= 'z')
            return ((int) c) - 97 + 26;
        else if (c >= '0' && c <= '9')
            return ((int) c) - 48 + 26 + 26;
        else
            switch (c) {
                case '+':
                    return 62;
                case '/':
                    return 63;
                case '=':
                    return 0;
                default:
                    throw new RuntimeException("unexpected code: " + c);
            }
    }

    /**
     * base64解密另一种形式
     *
     * @param s
     * @return
     */
    public static byte[] base64Decode(String s) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            base64Decode(s, bos);
        } catch (IOException e) {
            throw new RuntimeException();
        }
        byte[] decodedBytes = bos.toByteArray();
        try {
            bos.close();
            bos = null;
        } catch (IOException ex) {
            System.err.println("Error while decoding BASE64: " + ex.toString());
        }
        return decodedBytes;
    }

    /**
     * base64解密另一种形式
     *
     * @param s
     * @param os
     * @throws IOException
     */
    private static void base64Decode(String s, OutputStream os) throws IOException {
        int i = 0;

        int len = s.length();

        while (true) {
            while (i < len && s.charAt(i) <= ' ')
                i++;

            if (i == len)
                break;

            int tri = (base64Decode(s.charAt(i)) << 18) + (base64Decode(s.charAt(i + 1)) << 12) + (base64Decode(s.charAt(i + 2)) << 6) + (base64Decode(s.charAt(i + 3)));

            os.write((tri >> 16) & 255);
            if (s.charAt(i + 2) == '=')
                break;
            os.write((tri >> 8) & 255);
            if (s.charAt(i + 3) == '=')
                break;
            os.write(tri & 255);

            i += 4;
        }
    }
}
