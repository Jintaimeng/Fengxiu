package com.meng.missyou.service;

import com.meng.missyou.core.enumeration.CouponStatus;
import com.meng.missyou.exception.http.NotFoundException;
import com.meng.missyou.exception.http.ParameterException;
import com.meng.missyou.model.Activity;
import com.meng.missyou.model.Coupon;
import com.meng.missyou.model.UserCoupon;
import com.meng.missyou.repository.ActivityRepository;
import com.meng.missyou.repository.CouponRepository;
import com.meng.missyou.repository.UserCouponRepository;
import com.meng.missyou.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CouponService {
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private UserCouponRepository userCouponRepository;

    public List<Coupon> getByCategory(Long cid) {
        Date now = new Date();
        return this.couponRepository.findByCategory(cid, now);
    }

    public List<Coupon> getWholeStoreCoupons() {
        Date now = new Date();
        return this.couponRepository.findByWholeStore(true, now);
    }

    public void collectOneCoupon(Long uid, Long couponId) {
        this.couponRepository.findById(couponId).orElseThrow(() -> new NotFoundException(40003));
        Activity activity = this.activityRepository.findByCouponListId(couponId)
                .orElseThrow(() -> new NotFoundException(40010));
        Date now = new Date();
        Boolean isIn = CommonUtil.isInTimeLime(now, activity.getStartTime(), activity.getEndTime());
        if (!isIn) {
            throw new ParameterException(40005);
        }
        this.userCouponRepository.findFirstByUserIdAndCouponId(uid, couponId).orElseThrow(() -> new ParameterException(40006));
        UserCoupon userCouponNew = UserCoupon.builder()
                .userId(uid)
                .couponId(couponId)
                .status(CouponStatus.AVAILABLE.getValue())
                .createTime(now)
                .build();
        this.userCouponRepository.save(userCouponNew);
    }
}
