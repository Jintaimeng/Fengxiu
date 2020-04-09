package com.meng.missyou.api.v1;

import com.meng.missyou.bo.PageCounter;
import com.meng.missyou.core.Interceptors.ScopeLevel;
import com.meng.missyou.core.LocalUser;
import com.meng.missyou.dto.OrderDTO;
import com.meng.missyou.logic.OrderChecker;
import com.meng.missyou.model.Order;
import com.meng.missyou.service.OrderService;
import com.meng.missyou.util.CommonUtil;
import com.meng.missyou.vo.OrderIdVO;
import com.meng.missyou.vo.OrderSimplifyVO;
import com.meng.missyou.vo.PagingDozer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
@Validated
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Value("${missyou.order.pay-time-limit}")
    private Long payTimeLimit;

    @ScopeLevel()
    @PostMapping("")
    public OrderIdVO placeOrder(@RequestBody OrderDTO orderDTO) {
        Long uid = LocalUser.getUser().getId();
        OrderChecker orderChecker = this.orderService.isOk(uid, orderDTO);
        Long oid = this.orderService.placeOrder(uid, orderDTO, orderChecker);
        return new OrderIdVO(oid);
    }

    @GetMapping("/status/unpaid")
    @ScopeLevel()
    public PagingDozer getUnpaid(@RequestParam(defaultValue = "0") Integer start,
                                 @RequestParam(defaultValue = "10") Integer count) {
        PageCounter page = CommonUtil.convertToPageParameter(start, count);
        Page<Order> orderPage = this.orderService.getUnpaid(page.getPage(), page.getCount());
        PagingDozer pagingDozer = new PagingDozer(orderPage, OrderSimplifyVO.class);
        pagingDozer.getItems().forEach((o) -> ((OrderSimplifyVO) o).setPeriod(payTimeLimit));
        return pagingDozer;
    }

    @ScopeLevel()
    @GetMapping("/by/status/{status}")
    public PagingDozer getByStatus(@PathVariable int status, @RequestParam(defaultValue = "0") Integer start,
                                   @RequestParam(defaultValue = "10") Integer count) {
        PageCounter page = CommonUtil.convertToPageParameter(start, count);
        Page<Order> orderPage = this.orderService.getByStatus(status, page.getPage(), page.getCount());
        PagingDozer pagingDozer = new PagingDozer(orderPage, OrderSimplifyVO.class);
        pagingDozer.getItems().forEach((o) -> ((OrderSimplifyVO) o).setPeriod(payTimeLimit));
        return pagingDozer;
    }

}
