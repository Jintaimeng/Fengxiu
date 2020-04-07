package com.meng.missyou.logic;

import com.meng.missyou.bo.SkuOrderBO;
import com.meng.missyou.dto.OrderDTO;
import com.meng.missyou.dto.SkuInfoDTO;
import com.meng.missyou.exception.http.ParameterException;
import com.meng.missyou.model.OrderSku;
import com.meng.missyou.model.Sku;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderChecker {
    private OrderDTO orderDTO;
    private List<Sku> serverSkuList;
    private CouponChecker couponChecker;
    private Integer maxSkuLimit;
    @Getter
    private List<OrderSku> orderSkuList = new ArrayList<>();


    public OrderChecker(OrderDTO orderDTO, List<Sku> serverSkuList, CouponChecker couponChecker, Integer maxSkuLimit) {
        this.orderDTO = orderDTO;
        this.serverSkuList = serverSkuList;
        this.couponChecker = couponChecker;
        this.maxSkuLimit = maxSkuLimit;
    }

    public String getLeaderImg() {
        return this.serverSkuList.get(0).getImg();
    }

    public String getLeaderTitle() {
        return this.serverSkuList.get(0).getTitle();
    }

    public Integer getTotalCount() {
        return this.orderDTO.getSkuInfoDTOList().stream().map(skuInfoDTO -> skuInfoDTO.getCount()).reduce(Integer::sum).orElse(0);
    }

    public void isOk() {
        //orderTotalPrice  serverTotalPrice
        //sku是否已下架
        //是否已售罄
        //购买数量是否大于库存量
        //sku购买上限
        //优惠券校验
        BigDecimal serverTotalPrice = new BigDecimal("0");
        List<SkuOrderBO> skuOrderBOList = new ArrayList<>();
        this.skuNotOnSale(this.serverSkuList.size(), this.orderDTO.getSkuInfoDTOList().size());
        for (int i = 0; i < this.serverSkuList.size(); i++) {
            Sku sku = this.serverSkuList.get(i);
            SkuInfoDTO skuInfoDTO = this.orderDTO.getSkuInfoDTOList().get(i);
            this.containsSoldOutSku(sku);
            this.beyondSkuStock(sku, skuInfoDTO);
            serverTotalPrice.add(this.calculateSkuOrderPrice(sku, skuInfoDTO));
            skuOrderBOList.add(new SkuOrderBO(sku, skuInfoDTO));
            this.orderSkuList.add(new OrderSku(sku, skuInfoDTO));
        }
        this.totalPriceIsOk(orderDTO.getTotalPrice(), serverTotalPrice);
        if (this.couponChecker != null) {
            this.couponChecker.isOk();
            this.couponChecker.canBeUsed(skuOrderBOList, serverTotalPrice);
            this.couponChecker.finalTotalPriceIsOk(orderDTO.getFinalTotalPrice(), serverTotalPrice);
        }
    }

    private void totalPriceIsOk(BigDecimal orderTotalPrice, BigDecimal serverTotalPrice) {
        if (orderTotalPrice.compareTo(serverTotalPrice) != 0) {
            throw new ParameterException(50005);
        }
    }

    private BigDecimal calculateSkuOrderPrice(Sku sku, SkuInfoDTO skuInfoDTO) {
        if (skuInfoDTO.getCount() <= 0) {
            throw new ParameterException(50007);
        }
        return sku.getActualPrice().multiply(new BigDecimal(skuInfoDTO.getCount()));
    }

    private void skuNotOnSale(int count1, int count2) {
        if (count1 != count2) {
            throw new ParameterException(50002);
        }
    }

    private void containsSoldOutSku(Sku sku) {
        if (sku.getStock() == 0) {
            throw new ParameterException(50001);
        }
    }

    private void beyondSkuStock(Sku sku, SkuInfoDTO skuInfoDTO) {
        if (sku.getStock() < skuInfoDTO.getCount()) {
            throw new ParameterException(50003);
        }
    }

    private void beyondMaxSkuLimit(SkuInfoDTO skuInfoDTO) {
        if (skuInfoDTO.getCount() > this.maxSkuLimit) {
            throw new ParameterException(50004);
        }
    }
}
