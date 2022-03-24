package com.qingyan.base.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DateUtil
 * 常用的日期转换工具
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2021/8/16 20:33
 */
public class DateUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);


    /**
     * 日期组成部分：年、月、日
     */
    private static final int DATE_COMPONENT_NUM = 3;
    static String[] chinese = new String[]{"〇", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
    static String[] number = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

    /**
     * 中文日期转为数字
     * example：二零二一年四月五日 -> 2021年04月05日
     *
     * @param date 中文日期（yyyy年mm月dd日）
     * @return 数字日期
     */
    public static String chinese2Str(String date) throws IllegalArgumentException {

        if (date == null || "".equals(date.trim())) {
            return null;
        }

        String[] split = date.split("[年月日]");
        if (split.length != DATE_COMPONENT_NUM) {
            throw new IllegalArgumentException("Date format must be like yyyy年mm月dd天");
        }
        String year = split[0];
        for (int i = 0; i < chinese.length; i++) {
            year = year.replace(chinese[i], number[i]);
        }

        String month = split[1];
        for (int i = chinese.length - 1; i > 0; i--) {
            month = month.replace(chinese[i], number[i]);
        }
        if (month.length() == 1) {
            month = number[0] + month;
        }

        String day = split[2];
        if ("十".equals(day)) {
            day = "10";
        } else if ("二十".equals(day)) {
            day = "20";
        } else if ("三十".equals(day)) {
            day = "30";
        } else {
            if (day.length() == 2) {
                day = day.replace(chinese[10], number[1]);
            }
            if (day.length() == 3) {
                day = day.replace("二十", number[2]).replace("三十", number[3]);
            }
            for (int i = 1; i < chinese.length; i++) {
                day = day.replace(chinese[i], number[i]);
            }
        }
        if (day.length() == 1) {
            day = number[0] + day;
        }

        return year + "年" + month + "月" + day + "日";
    }

    /**
     * 数字日期转为中文日期
     * example 2021-08-17/2021年08月17日 -> 二零二一年八月十七日
     *
     * @param date 数字日期(yyyy-mm-dd/yyyy年mm月dd日)
     * @return 中文日期
     */
    public static String str2Chinese(String date) throws IllegalArgumentException {

        if (date == null || "".equals(date.trim())) {
            return null;
        }

        String[] split = date.split("[-—年月日]");
        if (split.length != DATE_COMPONENT_NUM) {
            throw new IllegalArgumentException("Date format must be like yyyy年mm月dd天 or yyyy-mm-dd");
        }
        StringBuilder year = new StringBuilder();
        char[] years = split[0].toCharArray();
        for (char c : years) {
            year.append(chinese[Integer.parseInt(String.valueOf(c))]);
        }

        String month = split[1];
        if (month.length() == 2) {
            if (month.startsWith(number[0])) {
                month = month.substring(1);
            }
        }
        month = chinese[Integer.parseInt(month)];

        String day;
        char[] days = split[2].toCharArray();
        int bits = Integer.parseInt(String.valueOf(days[0]));
        if (days.length == 1) {
            day = chinese[bits];
        } else {
            int ten = Integer.parseInt(String.valueOf(days[1]));

            if (bits == 0) {
                day = chinese[ten];
            } else if (bits == 1) {
                day = chinese[10] + chinese[ten];
            } else {
                if (ten == 0) {
                    day = chinese[bits] + chinese[10];
                } else {
                    day = chinese[bits] + chinese[10] + chinese[ten];
                }
            }
        }

        return year + "年" + month + "月" + day + "日";
    }


    /**
     * 获得某天结束时间 2020-02-19 23:59:59
     *
     * @param date
     * @return
     */
    public static Date getEndOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获得某天开始时间 2020-02-17 00:00:00
     *
     * @param date
     * @return
     */
    public static Date getStartOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 切割時間段
     *
     * @param dateType 交易類型 M/D/H/N -->每月/每天/每小時/每分鐘
     * @param start    yyyy-MM-dd HH:mm:ss
     * @param end      yyyy-MM-dd HH:mm:ss
     * @param fixRate  表示间隔天数，即多少天为一组
     * @return
     */
    public static List<String> cutDate(String dateType, String start, String end, int fixRate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dBegin = sdf.parse(start);
            Date dEnd = sdf.parse(end);
            return findDates(dateType, dBegin, dEnd, fixRate);
        } catch (Exception e) {
            LOGGER.error("切分日期发生错误", e);
        }
        return null;
    }

    public static List<String> findDates(String dateType, Date dBegin, Date dEnd, int fixRate) {
        List<String> listDate = new ArrayList<>(2);
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(dEnd);
        while (calEnd.after(calBegin)) {
            switch (dateType) {
                case "M":
                    calBegin.add(Calendar.MONTH, fixRate);
                    break;
                case "D":
                    calBegin.add(Calendar.DAY_OF_YEAR, fixRate);
                    break;
                case "H":
                    calBegin.add(Calendar.HOUR, fixRate);
                    break;
                case "F":
                    calBegin.add(Calendar.MINUTE, fixRate);
                    break;
                case "N":
                    calBegin.add(Calendar.SECOND, fixRate);
                    break;
                default:
                    break;
            }
            if (calEnd.after(calBegin)) {
                listDate.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calBegin.getTime()));
            } else {
                listDate.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calEnd.getTime()));
            }
        }
        return listDate;
    }
}
