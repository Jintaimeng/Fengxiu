package com.meng.missyou.service;

import com.meng.missyou.bo.OrderMessageBO;
import com.meng.missyou.exception.http.ServerErrorException;
import com.meng.missyou.model.Order;
import com.meng.missyou.repository.OrderRepository;
import com.meng.missyou.repository.SkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrderCancelService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SkuRepository skuRepository;

    @Transactional
    //@EventListener  订阅监听事件，一旦触发就会执行
    public void cancel(OrderMessageBO orderMessageBO) {
        if (orderMessageBO.getOrderId() <= 0) {
            throw new ServerErrorException(9999);
        }
        this.cancel(orderMessageBO.getOrderId());
    }

    private void cancel(Long oid) {
        Optional<Order> orderOptional = this.orderRepository.findById(oid);
        Order order = orderOptional.orElseThrow(() -> new ServerErrorException(9999));
        int res = this.orderRepository.cancelOrder(oid);
        if (res != 1) {
            return;
        }
        order.getSnapItems().forEach(orderSku -> {
            this.skuRepository.recoverStock(orderSku.getId(), orderSku.getCount().longValue());
        });
    }
}
