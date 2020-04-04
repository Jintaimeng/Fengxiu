package com.meng.missyou.logic;

import com.meng.missyou.exception.http.ForbiddenException;
import com.meng.missyou.model.Coupon;
import com.meng.missyou.model.UserCoupon;
import com.meng.missyou.util.CommonUtil;

import java.util.Date;

public class CouponChecker {
    private Coupon coupon;
    private UserCoupon userCoupon;

    public CouponChecker(Coupon coupon, UserCoupon userCoupon) {
        this.coupon = coupon;
        this.userCoupon = userCoupon;
    }

    public void isOk() {
        Date now = new Date();
        Boolean isInTimeLine = CommonUtil.isInTimeLime(now, this.coupon.getStartTime(), this.coupon.getEndTime());
        if (!isInTimeLine) {
            throw new ForbiddenException(40007);
        }
    }

    public void finalTotalPriceIsOk() {

    }

    public void canBeUsed() {

    }
}
