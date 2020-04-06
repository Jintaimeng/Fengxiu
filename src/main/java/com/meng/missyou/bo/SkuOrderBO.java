package com.meng.missyou.bo;

import com.meng.missyou.dto.SkuInfoDTO;
import com.meng.missyou.model.Sku;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class SkuOrderBO {
    private BigDecimal actualPrice;
    private Integer count;
    private Long categoryId;

    public SkuOrderBO(Sku sku, SkuInfoDTO skuInfoDTO) {
        this.actualPrice = sku.getActualPrice();
        this.count = skuInfoDTO.getCount();
        this.categoryId = sku.getCategoryId();
    }

    public BigDecimal getTotalPrice() {
        return this.actualPrice.multiply(new BigDecimal(this.count));
    }

}
