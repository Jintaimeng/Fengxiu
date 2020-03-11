package com.meng.missyou.model;

import javax.persistence.*;
import java.util.List;

@Entity
//@Table(name = "banner") //使表名与类名不相同，自定义表名
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 16)
    private String name;

    @Transient //使表中没有description
    private String description;
    private String img;
    private String title;
    @OneToMany
    @JoinColumn(name = "bannerId")
    private List<BannerItem> items;
}
