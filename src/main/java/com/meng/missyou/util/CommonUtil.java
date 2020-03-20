package com.meng.missyou.util;

import com.meng.missyou.bo.PageCounter;

public class CommonUtil {
    public static PageCounter convertToPageParameter(Integer start, Integer count) {
        Integer pageNum = start / count;
        PageCounter pageCounter = PageCounter.builder().page(pageNum).count(count).build();
        return pageCounter;
    }
}
