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
    //@OneToMany(fetch = FetchType.EAGER)//急加载
    @OneToMany(mappedBy = "banner")//被维护端要加上mappedBy，这里填 维护端 中导航属性的名字，这样才配置好双向的属性
    private List<BannerItem> items;//导航属性
}
