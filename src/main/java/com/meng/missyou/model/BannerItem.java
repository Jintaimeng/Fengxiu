package com.meng.missyou.model;

import javax.persistence.*;

@Entity
public class BannerItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//id增序
    private long id;
    private String img;
    private String keyword;
    private short type;
    private String name;

    private long bannerId;

    @ManyToOne
    @JoinColumn(name = "bannerId")//打在多方的  外键
    private Banner banner;
}
