package com.meng.missyou.api.v1;

import com.meng.missyou.model.Coupon;
import com.meng.missyou.service.CouponService;
import com.meng.missyou.vo.CouponPureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("coupon")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @GetMapping("/by/category/{cid}")
    public List<CouponPureVO> getCouponListByCategory(@PathVariable Long cid) {//默认使用二级分类
        List<Coupon> coupons = this.couponService.getByCategory(cid);
        if (coupons.isEmpty()) {
            return Collections.emptyList();
        }
        return CouponPureVO.getList(coupons);
    }

    @GetMapping("/whole_store")
    public List<CouponPureVO> geWholeStoreCouponList() {
        List<Coupon> coupons = this.couponService.getWholeStoreCoupons();
        if (coupons.isEmpty()) {
            return Collections.emptyList();
        }
        return CouponPureVO.getList(coupons);
    }
}
