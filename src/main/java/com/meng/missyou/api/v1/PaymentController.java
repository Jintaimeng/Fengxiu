package com.meng.missyou.api.v1;

import com.meng.missyou.core.Interceptors.ScopeLevel;
import com.meng.missyou.lib.MengWxNotify;
import com.meng.missyou.service.WxPaymentNotifyService;
import com.meng.missyou.service.WxPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Positive;
import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("payment")
@Validated
public class PaymentController {
    @Autowired
    private WxPaymentService wxPaymentService;
    @Autowired
    private WxPaymentNotifyService wxPaymentNotifyService;

    @ScopeLevel()
    @PostMapping("/pay/order/{id}")
    public Map<String, String> preWxOrder(@PathVariable(name = "id") @Positive Long oid) {
        Map<String, String> miniPayParams = this.wxPaymentService.preOrder(oid);
        return miniPayParams;
    }

    @RequestMapping("wx/notify")
    public String payCallback(HttpServletRequest request, HttpServletResponse response) {
        InputStream s;
        try {
            s = request.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
            return MengWxNotify.fail();
        }
        String xml = MengWxNotify.readNotify(s);
        try {
            this.wxPaymentNotifyService.ProcessPayNotify(xml);
        } catch (Exception e) {
            return MengWxNotify.fail();
        }
        return MengWxNotify.success();
    }
}
