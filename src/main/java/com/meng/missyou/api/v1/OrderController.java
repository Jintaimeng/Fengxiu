package com.meng.missyou.api.v1;

import com.meng.missyou.core.Interceptors.ScopeLevel;
import com.meng.missyou.core.LocalUser;
import com.meng.missyou.dto.OrderDTO;
import com.meng.missyou.logic.OrderChecker;
import com.meng.missyou.service.OrderService;
import com.meng.missyou.vo.OrderIdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
@Validated
public class OrderController {
    @Autowired
    private OrderService orderService;

    @ScopeLevel()
    @PostMapping("")
    public OrderIdVO placeOrder(@RequestBody OrderDTO orderDTO) {
        Long uid = LocalUser.getUser().getId();
        OrderChecker orderChecker = this.orderService.isOk(uid, orderDTO);
        Long oid = this.orderService.placeOrder(uid, orderDTO, orderChecker);
        return new OrderIdVO(oid);
    }
}
