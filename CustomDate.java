package com.example.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//抑制未经使用的警告
@SuppressWarnings("unused")
public class CustomDate {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //日期转字符串
    public static String ToDateTimeString(Date date) {
        return simpleDateTimeFormat.format(date);
    }

    public static String ToDateString(Date date) {
        return simpleDateFormat.format(date);
    }

    //字符串转日期
    public static Date parse(String date) throws ParseException {
        return simpleDateTimeFormat.parse(date);
    }

    //获得年
    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    //获得月
    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    //获得日数
    public static int getDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    //增减年份、月份、日数,返回Date
    public static Date addDate(Date oldDate, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(oldDate);
        calendar.add(Calendar.YEAR, year);
        calendar.add(Calendar.MONTH, month);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    //增减小时、分钟、秒数,返回Date
    public static Date addTime(Date oldDate, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(oldDate);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        calendar.add(Calendar.MINUTE, minute);
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }

    //计算两个时间的差,返回秒
    public static long calcBetween(Date firstDate, Date secondDate) {
        return Math.abs(firstDate.getTime() - secondDate.getTime()) / 1000;
    }

    //计算两个时间的差,返回秒
    public static long calcBetween(String firstDate, String secondDate) {
        try {
            return Math.abs(parse(firstDate).getTime() - parse(secondDate).getTime()) / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    //将秒数转换为小时，保留两位有效数字
    public static double absToHours(long seconds) {
        return Math.round(seconds / (60.0 * 60) * 100) / 100.0;
    }

    //将秒数转换为天，保留两位有效数字
    public static double absToDays(long seconds) {
        return Math.round(seconds / (60.0 * 60 * 24) * 100) / 100.0;
    }

    //将秒数转换为月，保留两位有效数字
    public static double absToMonths(long seconds) {
        return Math.round(seconds / (60.0 * 60 * 24 * 30) * 100) / 100.0;
    }

    //将秒数转换为年，保留两位有效数字
    public static double absToYears(long seconds) {
        return Math.round(seconds / (60.0 * 60 * 24 * 30 * 12) * 100) / 100.0;
    }

    //将秒数转换为描述时间的字符串
    public static String absToString(long seconds) {
        long s = seconds;
        int year = (int) (s / (365 * 24 * 60 * 60));
        s = s % (365 * 24 * 60 * 60);
        int month = (int) (s / (30 * 24 * 60 * 60));
        s = s % (30 * 24 * 60 * 60);
        int day = (int) (s / (24 * 60 * 60));
        s = s % (24 * 60 * 60);
        int hour = (int) (s / (60 * 60));
        s = s % (60 * 60);
        int minutes = (int) (s / 60);
        int second = (int) (s % 60);
        return year + "年" + month + "月" + day + "日 " + hour + "时" + minutes + "分" + second + "秒";
    }

    //保留两位有效数字
    public static double calcYouXiaoShuZi(double a) {
        return Math.round(a * 100) / 100.0;
    }

}
