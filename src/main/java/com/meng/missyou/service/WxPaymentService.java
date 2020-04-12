package com.meng.missyou.service;

import com.github.wxpay.sdk.MengWxPayConfig;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.meng.missyou.core.LocalUser;
import com.meng.missyou.exception.http.ForbiddenException;
import com.meng.missyou.exception.http.NotFoundException;
import com.meng.missyou.exception.http.ParameterException;
import com.meng.missyou.exception.http.ServerErrorException;
import com.meng.missyou.model.Order;
import com.meng.missyou.repository.OrderRepository;
import com.meng.missyou.util.CommonUtil;
import com.meng.missyou.util.HttpRequestProxy;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class WxPaymentService {

    @Autowired
    private OrderRepository orderRepository;

    private static MengWxPayConfig mengWxPayConfig = new MengWxPayConfig();

    @Autowired
    private OrderService orderService;

    @Value("${missyou.order.pay_callback_host}")
    private String payCallbackHost;
    @Value("${missyou.order.pay_callback_path}")
    private String payCallbackPath;

    public Map<String, String> preOrder(Long oid) {
        Long uid = LocalUser.getUser().getId();
        Optional<Order> orderOptional = this.orderRepository.findFirstByUserIdAndId(uid, oid);
        Order order = orderOptional.orElseThrow(() -> new NotFoundException(50009));
        if (order.needCancel()) {
            throw new ForbiddenException(50010);
        }
        WXPay wxPay = this.assembleWxPayConfig();
        Map<String, String> params = this.makePreOrderParams(order.getFinalTotalPrice(), order.getOrderNo());
        Map<String, String> wxOrder;
        try {
            wxOrder = wxPay.unifiedOrder(params);
        } catch (Exception e) {
            throw new ServerErrorException(9999);
        }
        //prepay_id
        //wx.requestPayment
        if (this.unifiedOrderSuccess(wxOrder)) {
            this.orderService.updateOrderPrepayId(order.getId(), wxOrder.get("prepay_id"));
        }
        return this.makePaySignature(wxOrder);
    }

    private Map<String, String> makePaySignature(Map<String, String> wxOrder) {
        String packages = "prepay_id=" + wxOrder.get("prepay_id");
        Map<String, String> wxPayMap = new HashMap<>();
        wxPayMap.put("appId", WxPaymentService.mengWxPayConfig.getAppID());
        wxPayMap.put("timeStamp", CommonUtil.timestamp10());
        wxPayMap.put("nonceStr", RandomStringUtils.randomAlphabetic(32));
        wxPayMap.put("package", packages);
        wxPayMap.put("signType", "HMAC-SHA256");
        String sign;
        try {
            sign = WXPayUtil.generateSignature(wxPayMap, WxPaymentService.mengWxPayConfig.getKey(), WXPayConstants.SignType.HMACSHA256);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
        Map<String, String> miniPayParams = new HashMap<>();
        miniPayParams.put("paySign", sign);
        miniPayParams.putAll(wxPayMap);
        miniPayParams.remove("appId");
        return miniPayParams;
    }

    private Boolean unifiedOrderSuccess(Map<String, String> wxOrder) {
        if (!wxOrder.get("return_code").equals("SUCCESS") || !wxOrder.get("result_code").equals("SUCCESS")) {
            throw new ParameterException(10007);
        }
        return true;
    }

    private Map<String, String> makePreOrderParams(BigDecimal serverFinalPrice, String orderNo) {
        String payCallbackUrl = payCallbackHost + payCallbackPath;
        Map<String, String> data = new HashMap<>();
        data.put("body", "Sleeve");
        data.put("out_trade_no", orderNo);
        data.put("device_info", "Sleeve");
        data.put("fee_type", "CNY");
        data.put("trade_type", "JSAPI");
        data.put("total_fee", CommonUtil.yuanTOFenPlainString(serverFinalPrice));
        data.put("openid", LocalUser.getUser().getOpenid());
        data.put("spbill_create_ip", HttpRequestProxy.getRemoteRealIp());
        data.put("notify_url", payCallbackUrl);
        return data;
    }

    public WXPay assembleWxPayConfig() {
        WXPay wxPay;
        try {
            wxPay = new WXPay(WxPaymentService.mengWxPayConfig);
        } catch (Exception e) {
            throw new ServerErrorException(9999);
        }
        return wxPay;
    }
}
