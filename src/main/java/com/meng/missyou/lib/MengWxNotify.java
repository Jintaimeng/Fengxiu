package com.meng.missyou.lib;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MengWxNotify {
    public static String readNotify(InputStream stream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();
    }

    public static String fail() {
        return "false";

    }

    public static String success() {
        return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }
}
