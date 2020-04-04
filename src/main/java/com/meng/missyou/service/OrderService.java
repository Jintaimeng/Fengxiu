package com.meng.missyou.service;

import com.meng.missyou.dto.OrderDTO;
import com.meng.missyou.exception.http.ParameterException;
import com.meng.missyou.model.Sku;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void isOk(Long uid, OrderDTO orderDTO) {
        if (orderDTO.getFinalTotalPrice().compareTo(new BigDecimal("0")) < 0) {
            throw new ParameterException(50011);
        }
        List<Long> skuIdList = orderDTO.getSkuInfoDTOList()
                .stream()
                .map(skuInfoDTO -> skuInfoDTO.getId())
                .collect(Collectors.toList());
        List<Sku> skuList = this.skuService.getSkuListByIds(skuIdList);
    }
}
