package com.meng.missyou.bo;

import com.meng.missyou.dto.SkuInfoDTO;
import com.meng.missyou.model.Sku;

import java.math.BigDecimal;

public class SkuOrderBO {
    private BigDecimal actualPrice;
    private Integer count;
    private Long categoryId;

    public SkuOrderBO(Sku sku, SkuInfoDTO skuInfoDTO) {
        this.actualPrice = sku.getActualPrice();
        this.count = skuInfoDTO.getCount();
        this.categoryId = sku.getCategoryId();
    }

}
