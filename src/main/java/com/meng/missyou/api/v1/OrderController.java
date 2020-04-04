package com.meng.missyou.api.v1;

import com.meng.missyou.core.Interceptors.ScopeLevel;
import com.meng.missyou.core.LocalUser;
import com.meng.missyou.dto.OrderDTO;
import com.meng.missyou.vo.OrderIdVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
@Validated
public class OrderController {
    @ScopeLevel()
    @PostMapping("")
    public OrderIdVO placeOrder(@RequestBody OrderDTO orderDTO) {
        Long uid = LocalUser.getUser().getId();
        //OrderChecker
        //CouponChecker
    }
}
