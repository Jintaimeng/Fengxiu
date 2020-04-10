package com.meng.missyou.service;

import com.github.wxpay.sdk.MengWxPayConfig;
import com.github.wxpay.sdk.WXPay;
import com.meng.missyou.core.LocalUser;
import com.meng.missyou.exception.http.ForbiddenException;
import com.meng.missyou.exception.http.NotFoundException;
import com.meng.missyou.exception.http.ServerErrorException;
import com.meng.missyou.model.Order;
import com.meng.missyou.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private Map<String, String> makePreOrderParams() {
        Map<String, String> data = new HashMap<>();
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
