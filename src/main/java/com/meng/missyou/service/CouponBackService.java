package com.meng.missyou.service;

import com.meng.missyou.bo.OrderMessageBO;
import com.meng.missyou.core.enumeration.OrderStatus;
import com.meng.missyou.exception.http.ServerErrorException;
import com.meng.missyou.model.Order;
import com.meng.missyou.repository.OrderRepository;
import com.meng.missyou.repository.UserCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CouponBackService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserCouponRepository userCouponRepository;

    @Transactional
    //@EventListener  订阅监听事件，一旦触发就会执行
    public void returnBack(OrderMessageBO bo) {
        Long couponId = bo.getCouponId();
        Long uid = bo.getUserId();
        Long orderId = bo.getOrderId();

        if (couponId == -1) {
            //没有使用优惠券
            return;
        }
        Optional<Order> orderOptional = this.orderRepository.findFirstByUserIdAndId(uid, orderId);
        Order order = orderOptional.orElseThrow(() -> new ServerErrorException(9999));
        if (order.getStatusEnum().equals(OrderStatus.UNPAID) || order.getStatusEnum().equals(OrderStatus.CANCELED)) {
            this.userCouponRepository.returnBack(couponId, uid);
        }
    }
}
