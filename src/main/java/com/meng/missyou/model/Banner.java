package com.meng.missyou.model;

import com.meng.missyou.model1.BannerItem;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Banner {
    @Id
    private Long id;
    private String name;
    private String description;
    private Date createTime;
    private Date updateTime;
    private Date deleteTime;
    private String title;
    private String img;

    @OneToMany(fetch = FetchType.LAZY)//懒加载
    @JoinColumn(name = "bannerId")
    private List<BannerItem> items;//导航属性


}
