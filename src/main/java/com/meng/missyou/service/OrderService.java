package com.meng.missyou.service;

import com.meng.missyou.core.money.IMoneyDiscount;
import com.meng.missyou.dto.OrderDTO;
import com.meng.missyou.exception.http.NotFoundException;
import com.meng.missyou.exception.http.ParameterException;
import com.meng.missyou.logic.CouponChecker;
import com.meng.missyou.logic.OrderChecker;
import com.meng.missyou.model.Coupon;
import com.meng.missyou.model.Order;
import com.meng.missyou.model.Sku;
import com.meng.missyou.model.UserCoupon;
import com.meng.missyou.repository.CouponRepository;
import com.meng.missyou.repository.UserCouponRepository;
import com.meng.missyou.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    //    @Autowired
//    private OrderRepository orderRepository;
    @Autowired
    private SkuService skuService;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private UserCouponRepository userCouponRepository;
    @Autowired
    private IMoneyDiscount iMoneyDiscount;

    @Value("${missyou.order.max-sku-limit}")
    private int maxSkuLimit;
    @Value("${missyou.order.pay-time-limit}")
    private Integer payTimeLimit;

    public void placeOrder(Long uid, OrderDTO orderDTO, OrderChecker orderChecker) {
        String orderNo = OrderUtil.makeOrderNo();
        Order order = Order.builder()
                .orderNo(orderNo)
                .totalPrice(orderDTO.getTotalPrice())
                .finalTotalPrice(orderDTO.getFinalTotalPrice())
                .userId(uid)
                .totalCount(orderChecker.getTotalCount().longValue())
                .snapImg(orderChecker.getLeaderImg())
                .snapTitle(orderChecker.getLeaderTitle())
                .status()
        //.snapAddress(orderDTO.getAddress()
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

}
