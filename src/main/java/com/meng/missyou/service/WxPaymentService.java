package com.meng.missyou.service;

import com.github.wxpay.sdk.MengWxPayConfig;
import com.github.wxpay.sdk.WXPay;
import com.meng.missyou.core.LocalUser;
import com.meng.missyou.exception.http.ForbiddenException;
import com.meng.missyou.exception.http.NotFoundException;
import com.meng.missyou.exception.http.ServerErrorException;
import com.meng.missyou.model.Order;
import com.meng.missyou.repository.OrderRepository;
import com.meng.missyou.util.CommonUtil;
import com.meng.missyou.util.HttpRequestProxy;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Map<String, String> preOrder(Long oid) {
        Long uid = LocalUser.getUser().getId();
        Optional<Order> orderOptional = this.orderRepository.findFirstByUserIdAndId(uid, oid);
        Order order = orderOptional.orElseThrow(() -> new NotFoundException(50009));
        if (order.needCancel()) {
            throw new ForbiddenException(50010);
        }
        WXPay wxPay = this.assembleWxPayConfig();
        wxPay.unifiedOrder(this.makePreOrderParams());
    }

    private Map<String, String> makePreOrderParams(BigDecimal serverFinalPrice, String orderNo) {
        Map<String, String> data = new HashMap<>();
        data.put("body", "Sleeve");
        data.put("out_trade_no", orderNo);
        data.put("device_info", "Sleeve");
        data.put("fee_type", "CNY");
        data.put("trade_type", "JSAPI");
        data.put("total_fee", CommonUtil.yuanTOFenPlainString(serverFinalPrice));
        data.put("open_id", LocalUser.getUser().getOpenid());
        data.put("spbill_create_ip", HttpRequestProxy.getRemoteRealIp());
        data.put("notify_url", )
    }

    private WXPay assembleWxPayConfig() {
        WXPay wxPay;
        try {
            wxPay = new WXPay(WxPaymentService.mengWxPayConfig);
        } catch (Exception e) {
            throw new ServerErrorException(9999);
        }
        return wxPay;
    }
}
