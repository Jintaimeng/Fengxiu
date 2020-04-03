package com.meng.missyou.api.v1;

import com.meng.missyou.core.Interceptors.ScopeLevel;
import com.meng.missyou.core.LocalUser;
import com.meng.missyou.core.enumeration.CouponStatus;
import com.meng.missyou.exception.CreateSuccess;
import com.meng.missyou.exception.http.ParameterException;
import com.meng.missyou.model.Coupon;
import com.meng.missyou.service.CouponService;
import com.meng.missyou.vo.CouponPureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ScopeLevel()
    @PostMapping("/collect/{id}")
    public void collectCoupon(@PathVariable Long id) {//此处的id为优惠券的id
        Long uid = LocalUser.getUser().getId();
        this.couponService.collectOneCoupon(uid, id);
        throw new CreateSuccess(0);
    }

    @ScopeLevel
    @GetMapping("/myself/by/status/{status}")
    public List<CouponPureVO> getMyCouponByStatus(@PathVariable Integer status) {
        Long uid = LocalUser.getUser().getId();
        List<Coupon> couponList;
        switch (CouponStatus.toType(status)) {
            case AVAILABLE:
                couponList = this.couponService.getMyAvailableCoupons(uid);
                break;
            case USED:
                couponList = this.couponService.getMyUsedCoupons(uid);
                break;
            case EXPIRED:
                couponList = this.couponService.getMyExpiredCoupons(uid);
                break;
            default:
                throw new ParameterException(40001);
        }
        return CouponPureVO.getList(couponList);
    }
}
