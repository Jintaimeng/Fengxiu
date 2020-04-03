package com.meng.missyou.vo;

import com.meng.missyou.model.Category;
import com.meng.missyou.model.Coupon;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CouponCategoryVO extends CouponPureVO {
    private List<CategoryPureVO> categories = new ArrayList<>();

    public CouponCategoryVO(Coupon coupon) {
        super(coupon);
        List<Category> categories = coupon.getCategoryList();
        categories.forEach(category -> {
            CategoryPureVO categoryPureVO = new CategoryPureVO(category);
            this.categories.add(categoryPureVO);
        });
    }

}
