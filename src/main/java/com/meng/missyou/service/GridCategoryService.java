package com.meng.missyou.service;

import com.meng.missyou.model.GridCategory;
import com.meng.missyou.repository.GridCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GridCategoryService {
    @Autowired
    private GridCategoryRepository gridCategoryRepository;

    public List<GridCategory> getGridCategoryList() {
        return this.gridCategoryRepository.findAll();
    }
}
