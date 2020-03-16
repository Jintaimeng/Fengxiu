package com.meng.missyou.model1;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Spu {
    @Id
    private long id;
    private String title;
    private String subtitle;
    @ManyToMany(mappedBy = "spuList")
    private List<Theme> themeList;//导航属性
}
