package com.meng.missyou.vo;

import com.meng.missyou.model.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class CategoryPureVO {
    private Long id;
    private String name;
    private Boolean isRoot;
    private Long parentId;
    private String img;
    private Long index;

    public CategoryPureVO(Category category) {
        BeanUtils.copyProperties(category, this);//属性的拷贝
    }
}
