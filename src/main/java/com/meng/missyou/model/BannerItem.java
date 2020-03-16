package com.meng.missyou.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity

public class BannerItem {
    @Id
    private Long id;
    private String img;
    private String keyword;
    private short type;
    private Date createTime;
    private Date updateTime;
    private Date deleteTime;
    private Long bannerId;
    private String name;


}
