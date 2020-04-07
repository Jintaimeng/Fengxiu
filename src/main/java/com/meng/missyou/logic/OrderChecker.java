package com.meng.missyou.logic;

import com.meng.missyou.dto.OrderDTO;
import com.meng.missyou.dto.SkuInfoDTO;
import com.meng.missyou.exception.http.ParameterException;
import com.meng.missyou.model.Sku;

import java.util.List;

public class OrderChecker {
    private OrderDTO orderDTO;
    private List<Sku> serverSkuList;
    private CouponChecker couponChecker;

    public OrderChecker(OrderDTO orderDTO, List<Sku> serverSkuList, CouponChecker couponChecker) {
        this.orderDTO = orderDTO;
        this.serverSkuList = serverSkuList;
        this.couponChecker = couponChecker;
    }

    public void isOk() {
        //orderTotalPrice  serverTotalPrice
        //sku是否已下架
        //是否已售罄
        //购买数量是否大于库存量
        //sku购买上限
        //优惠券校验
        this.skuNotOnSale(this.serverSkuList.size(), this.orderDTO.getSkuInfoDTOList().size());
        for (int i = 0; i < this.serverSkuList.size(); i++) {
            Sku sku = this.serverSkuList.get(i);
            SkuInfoDTO skuInfoDTO = this.orderDTO.getSkuInfoDTOList().get(i);
            this.containsSoldOutSku(sku);
            this.beyondSkuStock(sku, skuInfoDTO);
        }
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
