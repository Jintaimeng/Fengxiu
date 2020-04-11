package com.meng.missyou.service;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.meng.missyou.exception.http.ServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WxPaymentNotifyService {
    @Autowired
    private WxPaymentService wxPaymentService;

    public void ProcessPayNotify(String data) {
        Map<String, String> dataMap = new HashMap<>();
        try {
            dataMap = WXPayUtil.xmlToMap(data);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
        WXPay wxPay = this.wxPaymentService.assembleWxPayConfig();
        Boolean valid;
        try {
            valid = wxPay.isResponseSignatureValid(dataMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
        if (!valid) {
            throw new ServerErrorException(9999);
        }
    }
}
