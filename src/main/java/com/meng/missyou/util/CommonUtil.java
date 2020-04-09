package com.meng.missyou.util;

import com.meng.missyou.bo.PageCounter;

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
}
