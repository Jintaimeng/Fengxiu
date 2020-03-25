package com.meng.missyou.api.v1;

import com.meng.missyou.exception.http.NotFoundException;
import com.meng.missyou.model.Category;
import com.meng.missyou.model.GridCategory;
import com.meng.missyou.service.CategoryService;
import com.meng.missyou.service.GridCategoryService;
import com.meng.missyou.vo.CategoriesAllVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping("category")
@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private GridCategoryService gridCategoryService;

    @GetMapping("/all")
    public CategoriesAllVO getAll() {
        Map<Integer, List<Category>> categories = this.categoryService.getAll();
        return new CategoriesAllVO(categories);
    }

    @GetMapping("/grid/all")
    public List<GridCategory> getGridCategoryList() {
        List<GridCategory> gridCategoryList = this.gridCategoryService.getGridCategoryList();
        if (gridCategoryList.isEmpty()) {
            throw new NotFoundException(30009);
        }
        return gridCategoryList;
    }
}
