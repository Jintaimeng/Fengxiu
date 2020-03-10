package com.meng.missyou.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BannerItem {
    @Id
    private long id;
    private String img;
    private String keyword;
    private short type;
    private String name;
}
