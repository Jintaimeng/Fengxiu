package com.meng.missyou.service;

import com.meng.missyou.model.Category;
import com.meng.missyou.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Map<Integer, List<Category>> getAll() {
        List<Category> root = this.categoryRepository.findAllByIsRootOrderByIndexAsc(true);
        List<Category> subs = this.categoryRepository.findAllByIsRootOrderByIndexAsc(false);
        Map<Integer, List<Category>> categories = new HashMap<>();
        categories.put(1, root);
        categories.put(2, subs);
        return categories;
    }
}
