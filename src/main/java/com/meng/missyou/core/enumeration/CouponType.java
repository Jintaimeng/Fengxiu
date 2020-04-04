package com.meng.missyou.core.enumeration;

import java.util.stream.Stream;

public enum CouponType {
    FULL_MINUS(1, "满减券"),
    FULL_OFF(2, "满减折扣券"),
    NO_THRESHOLD_MINUS(3, "无门槛减除券");
    private Integer value;

    CouponType(Integer value, String descirption) {
        this.value = value;
    }

    public static CouponType toType(Integer value) {
        return Stream.of(CouponType.values())
                .filter(couponType -> couponType.value == value)
                .findAny()
                .orElse(null);
    }
}
