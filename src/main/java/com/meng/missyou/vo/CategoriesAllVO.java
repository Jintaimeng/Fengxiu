package com.meng.missyou.vo;

import com.meng.missyou.model.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoriesAllVO {
    private List<Category> roots;
    private List<Category> subs;
}
