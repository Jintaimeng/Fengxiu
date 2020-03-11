package com.meng.missyou.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Theme {
    @Id
    private long id;
    private String title;
    private String name;
    @ManyToMany
    @JoinTable(name = "theme_spu", joinColumns = @JoinColumn(name = "them_id"),
            inverseJoinColumns = @JoinColumn(name = "spu_id"))//定义第三张表的名称，以及属性的名称
    private List<Spu> spuList;//导航属性

}
