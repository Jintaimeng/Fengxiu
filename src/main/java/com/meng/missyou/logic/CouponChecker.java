package com.meng.missyou.logic;

import com.meng.missyou.core.enumeration.CouponType;
import com.meng.missyou.core.money.IMoneyDiscount;
import com.meng.missyou.exception.http.ForbiddenException;
import com.meng.missyou.exception.http.ParameterException;
import com.meng.missyou.model.Coupon;
import com.meng.missyou.model.UserCoupon;
import com.meng.missyou.util.CommonUtil;

import java.math.BigDecimal;
import java.util.Date;


public class CouponChecker {
    private Coupon coupon;
    private UserCoupon userCoupon;
    private IMoneyDiscount iMoneyDiscount;

    public CouponChecker(Coupon coupon, UserCoupon userCoupon, IMoneyDiscount iMoneyDiscount) {
        this.coupon = coupon;
        this.userCoupon = userCoupon;
        this.iMoneyDiscount = iMoneyDiscount;
    }

    public void isOk() {
        Date now = new Date();
        Boolean isInTimeLine = CommonUtil.isInTimeLime(now, this.coupon.getStartTime(), this.coupon.getEndTime());
        if (!isInTimeLine) {
            throw new ForbiddenException(40007);
        }
    }

    public void finalTotalPriceIsOk(BigDecimal orderFinalTotalPrice, BigDecimal serverTotalPrice) {
        BigDecimal serverFinalTotalPrice;
        switch (CouponType.toType(this.coupon.getType())) {
            case FULL_MINUS:
                serverFinalTotalPrice = serverTotalPrice.subtract(this.coupon.getMinus());
                break;
            case FULL_OFF:
                serverFinalTotalPrice = this.iMoneyDiscount.discount(serverTotalPrice, this.coupon.getRate());
                break;
            case NO_THRESHOLD_MINUS:
                serverFinalTotalPrice = serverTotalPrice.subtract(this.coupon.getMinus());
                if (serverFinalTotalPrice.compareTo(new BigDecimal("0")) < 0) {
                    throw new ForbiddenException(50008);
                }
                break;
            default:
                throw new ParameterException(40009);
        }
        int compare = serverFinalTotalPrice.compareTo(orderFinalTotalPrice);
        if (compare != 0) {
            throw new ForbiddenException(50008);
        }
    }

    public void canBeUsed() {

    }
}
