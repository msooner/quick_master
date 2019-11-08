package com.ron.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Ron 2019/08/01
 */
public class DateUtil {

    public static String getCurrentDateStr(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    public static String getDateString(String format,Integer date) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 拿到和当前时间相差指定月数的日期字符串
     * @param format
     * @param unit 操作单位
     * @param offset 加上或者减去的日期偏移量
     * @return
     */
    public static String getAppointDateStr(String format,Integer unit,Integer offset) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(unit,offset);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 拿到和当前时间相差指定月数的日期字符串
     * @param format
     * @param unit 操作单位
     * @param offset 加上或者减去的日期偏移量
     * @return
     */
    public static Integer getAppointDateSecond(String format,Integer unit,Integer offset) {
        String dateStr = getAppointDateStr(format,unit,offset);
        return getSecondFromDateString(dateStr,"yyyyMMddHHmmss");
    }

    /**
     * 根据字符串时间获取对应时间戳
     *
     * @param date
     * @param format
     * @return
     */
    public static Integer getSecondFromDateString(String date, String format) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            Date d = simpleDateFormat.parse(date);
            return new Long(d.getTime() / 1000).intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDateFromString(String date,String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前时间戳 精确到秒
     *
     * @return
     */
    public static Integer getCurrentSecondIntValue() {
        return new Long(System.currentTimeMillis() / 1000).intValue();
    }

    /**
     * 获取当前时间戳 精确到秒
     *
     * @return
     */
    public static Long getCurrentSecondLongValue() {
        return System.currentTimeMillis() / 1000;
    }


    /**
     * 获取常用格式的时间字符串 年-月-日 时:分:秒
     *
     * @param second
     * @return
     */
    public static String getChineseDateFromSecond(Integer second) {
        if (second == null) {
            return null;
        }
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.SIMPLIFIED_CHINESE);
        long milliS = second * 1000l;
        Date date = new Date(milliS);
        return sFormat.format(date);

    }

}
