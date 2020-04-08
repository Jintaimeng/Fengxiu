package com.meng.missyou.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coupon extends BaseEntity {
    @Id
    private Long id;
    private String title;
    private Date startTime;
    private Date endTime;
    private String description;
    private BigDecimal fullMoney;
    private BigDecimal minus;
    private BigDecimal rate;
    private Integer type;
    //private Object valitiy;
    private Long activityId;
    private String remark;
    private Boolean wholeStore;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "couponList")
    private List<Category> categoryList;

}
