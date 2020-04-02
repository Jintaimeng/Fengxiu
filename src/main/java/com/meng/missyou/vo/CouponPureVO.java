package com.meng.missyou.vo;

import com.meng.missyou.model.Coupon;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class CouponPureVO {
    private Long id;
    private String title;
    private Date startTime;
    private Date endTime;
    private String description;
    private BigDecimal fullMoney;
    private BigDecimal minus;
    private BigDecimal rate;
    private Integer type;
    private Long activityId;
    private String remark;
    private Boolean wholeStore;

    public CouponPureVO(Coupon coupon) {
        BeanUtils.copyProperties(coupon, this);
    }
}
