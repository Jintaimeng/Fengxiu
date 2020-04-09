package com.meng.missyou.service;

import com.meng.missyou.core.LocalUser;
import com.meng.missyou.core.enumeration.OrderStatus;
import com.meng.missyou.core.money.IMoneyDiscount;
import com.meng.missyou.dto.OrderDTO;
import com.meng.missyou.exception.http.ForbiddenException;
import com.meng.missyou.exception.http.NotFoundException;
import com.meng.missyou.exception.http.ParameterException;
import com.meng.missyou.logic.CouponChecker;
import com.meng.missyou.logic.OrderChecker;
import com.meng.missyou.model.*;
import com.meng.missyou.repository.CouponRepository;
import com.meng.missyou.repository.OrderRepository;
import com.meng.missyou.repository.SkuRepository;
import com.meng.missyou.repository.UserCouponRepository;
import com.meng.missyou.util.CommonUtil;
import com.meng.missyou.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SkuService skuService;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private UserCouponRepository userCouponRepository;
    @Autowired
    private IMoneyDiscount iMoneyDiscount;
    @Autowired
    private SkuRepository skuRepository;

    @Value("${missyou.order.max-sku-limit}")
    private int maxSkuLimit;
    @Value("${missyou.order.pay-time-limit}")
    private Integer payTimeLimit;

    @Transactional //添加事务，将连续的多次数据库更新，插入操作包裹在一起
    public Long placeOrder(Long uid, OrderDTO orderDTO, OrderChecker orderChecker) {
        String orderNo = OrderUtil.makeOrderNo();
        Calendar now = Calendar.getInstance();
        Calendar now1 = (Calendar) now.clone();
        Calendar expiredTime = CommonUtil.addSomeSeconds(now, this.payTimeLimit);
        now.add(Calendar.SECOND, payTimeLimit);
        Order order = Order.builder()
                .orderNo(orderNo)
                .totalPrice(orderDTO.getTotalPrice())
                .finalTotalPrice(orderDTO.getFinalTotalPrice())
                .userId(uid)
                .totalCount(orderChecker.getTotalCount().longValue())
                .snapImg(orderChecker.getLeaderImg())
                .snapTitle(orderChecker.getLeaderTitle())
                .status(OrderStatus.UNPAID.value())
                .expiredTime(expiredTime.getTime())
                .placedTime(now1.getTime())
                .build();
        order.setSnapAddress(orderDTO.getAddress());
        order.setSnapItems(orderChecker.getOrderSkuList());
        order.setCreateTime(now1.getTime());
        this.orderRepository.save(order);
        this.reduceStock(orderChecker);
        if (orderDTO.getCouponId() != null) {
            this.writeOffCoupon(orderDTO.getCouponId(), order.getId(), uid);
        }
        //reduceStock
        //核销优惠券
        //加入到延迟消息队列
        return order.getId();
    }

    public Page<Order> getUnpaid(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createTime").descending());
        Long uid = LocalUser.getUser().getId();
        Date now = new Date();
        return this.orderRepository.findByExpiredTimeGreaterThanAndStatusAndUserId(now, OrderStatus.UNPAID.value(), uid, pageable);
    }

    public OrderChecker isOk(Long uid, OrderDTO orderDTO) {
        if (orderDTO.getFinalTotalPrice().compareTo(new BigDecimal("0")) < 0) {
            throw new ParameterException(50011);
        }
        List<Long> skuIdList = orderDTO.getSkuInfoDTOList()
                .stream()
                .map(skuInfoDTO -> skuInfoDTO.getId())
                .collect(Collectors.toList());
        List<Sku> skuList = this.skuService.getSkuListByIds(skuIdList);
        Long couponId = orderDTO.getCouponId();
        CouponChecker couponChecker = null;
        if (couponId != null) {
            Coupon coupon = this.couponRepository.findById(couponId).orElseThrow(() -> new NotFoundException(40003));
            UserCoupon userCoupon = this.userCouponRepository.findFirstByUserIdAndCouponId(uid, couponId)
                    .orElseThrow(() -> new NotFoundException(50006));
            couponChecker = new CouponChecker(coupon, iMoneyDiscount);
        }
        OrderChecker orderChecker = new OrderChecker(orderDTO, skuList, couponChecker, maxSkuLimit);
        orderChecker.isOk();
        return orderChecker;
    }

    private void reduceStock(OrderChecker orderChecker) {
        List<OrderSku> orderSkuList = orderChecker.getOrderSkuList();
        for (OrderSku orderSku : orderSkuList) {
            int result = this.skuRepository.reduceStock(orderSku.getId(), orderSku.getCount().longValue());
            if (result != 1) {
                throw new ParameterException(50003);
            }
        }
    }

    private void writeOffCoupon(Long couponId, Long oid, Long uid) {
        int result = this.userCouponRepository.writeOff(couponId, oid, uid);
        if (result != 1) {
            throw new ForbiddenException(40012);
        }
    }
}
