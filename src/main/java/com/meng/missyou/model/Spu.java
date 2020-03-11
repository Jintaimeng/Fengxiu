package com.meng.missyou.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Spu {
    @Id
    private long id;
    private String title;
    private String subtitle;
//    @ManyToMany
//    private List<Theme> themeList;//导航属性
}
