package com.meng.missyou.logic;

import com.meng.missyou.bo.SkuOrderBO;
import com.meng.missyou.core.enumeration.CouponType;
import com.meng.missyou.core.money.IMoneyDiscount;
import com.meng.missyou.exception.http.ForbiddenException;
import com.meng.missyou.exception.http.ParameterException;
import com.meng.missyou.model.Coupon;
import com.meng.missyou.util.CommonUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class CouponChecker {
    private Coupon coupon;
    //private UserCoupon userCoupon;
    private IMoneyDiscount iMoneyDiscount;

    public CouponChecker(Coupon coupon, IMoneyDiscount iMoneyDiscount) {
        this.coupon = coupon;
        //this.userCoupon = userCoupon;
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

    public void canBeUsed(List<SkuOrderBO> skuOrderBOList, BigDecimal serverTotalPrice) {
        BigDecimal orderCategoryPrice;
        if (this.coupon.getWholeStore()) {
            orderCategoryPrice = serverTotalPrice;
        } else {
            List<Long> cidList = coupon.getCategoryList().stream()
                    .map(category -> category.getId()).collect(Collectors.toList());
            orderCategoryPrice = this.getSumByCategoryList(skuOrderBOList, cidList);
        }
        this.CouponCanBeUsed(orderCategoryPrice);
    }

    private void CouponCanBeUsed(BigDecimal orderCategoryPrice) {
        switch (CouponType.toType(this.coupon.getType())) {
            case FULL_OFF:
            case FULL_MINUS:
                int compare = this.coupon.getFullMoney().compareTo(orderCategoryPrice);
                if (compare > 0) {
                    throw new ParameterException(40008);
                }
                break;
            case NO_THRESHOLD_MINUS:
                break;
            default:
                throw new ParameterException(40009);
        }
    }

    private BigDecimal getSumByCategoryList(List<SkuOrderBO> skuOrderBOList, List<Long> cidList) {
        BigDecimal sum = cidList.stream().map(cid -> this.getSumByCategoryId(skuOrderBOList, cid)).reduce(BigDecimal::add)
                .orElse(new BigDecimal("0"));
        return sum;
    }

    private BigDecimal getSumByCategoryId(List<SkuOrderBO> skuOrderBOList, Long cid) {
        BigDecimal sum = skuOrderBOList.stream()
                .filter(skuOrderBO -> skuOrderBO.getCategoryId().equals(cid))
                .map(skuOrderBO -> skuOrderBO.getTotalPrice())
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal("0"));
        return sum;
    }
}
