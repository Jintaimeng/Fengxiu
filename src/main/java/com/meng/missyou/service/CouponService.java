package com.meng.missyou.service;

import com.meng.missyou.model.Coupon;
import com.meng.missyou.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CouponService {
    @Autowired
    private CouponRepository couponRepository;

    public List<Coupon> getByCategory(Long cid) {
        Date now = new Date();
        return this.couponRepository.findByCategory(cid, now);
    }
}
