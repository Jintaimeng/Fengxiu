package com.meng.missyou.util;

import com.meng.missyou.bo.PageCounter;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class CommonUtil {
    public static PageCounter convertToPageParameter(Integer start, Integer count) {
        Integer pageNum = start / count;
        PageCounter pageCounter = PageCounter.builder().page(pageNum).count(count).build();
        return pageCounter;
    }

    public static Calendar addSomeSeconds(Calendar calendar, int seconds) {
        calendar.add(Calendar.SECOND, seconds);
        return calendar;
    }

    public static Boolean isInTimeLime(Date now, Date start, Date end) {
        Long nowTime = now.getTime();
        Long startTime = start.getTime();
        Long endTime = end.getTime();
        if (nowTime < endTime && nowTime > startTime) {
            return true;
        }
        return false;
    }

    //period  单位：秒
    public static Boolean isOutOfDate(Date startTime, Long period) {
        Long now = Calendar.getInstance().getTimeInMillis();
        Long startTimeStamp = startTime.getTime();
        Long periodMillSecond = period * 1000;
        if (now > startTimeStamp + periodMillSecond) {
            return true;
        }
        return false;
    }

    public static Boolean isOutOfDate(Date expiredTime) {
        Long now = Calendar.getInstance().getTimeInMillis();
        Long expiredStamp = expiredTime.getTime();
        if (now > expiredStamp) {
            return true;
        }
        return false;
    }

    public static String yuanTOFenPlainString(BigDecimal p) {
        p = p.multiply(new BigDecimal("100"));
        return CommonUtil.toPlain(p);
    }

    public static String toPlain(BigDecimal p) {
        return p.stripTrailingZeros().toString();
    }

    public static String timestamp10() {
        Long timestamp13 = Calendar.getInstance().getTimeInMillis();
        String timestamp13Str = timestamp13.toString();
        return timestamp13Str.substring(0, timestamp13Str.length() - 3);
    }
}
