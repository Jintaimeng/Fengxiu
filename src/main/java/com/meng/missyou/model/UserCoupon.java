package com.meng.missyou.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_coupon", schema = "sleeve", catalog = "")
public class UserCoupon {
    @Id
    private Long id;
    private Long userId;
    private Long couponId;
    private Integer status;
    private Date createTime;
    private Long orderId;
    //private Timestamp updateTime;
}
