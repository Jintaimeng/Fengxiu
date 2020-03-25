package com.meng.missyou.service;

import com.meng.missyou.model.Theme;
import com.meng.missyou.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThemeService {
    @Autowired
    private ThemeRepository themeRepository;

    public List<Theme> findByNames(List<String> names) {
        List<Theme> themeList = this.themeRepository.findByNames(names);
        return themeList;
    }

    public Optional<Theme> findByName(String name) {
        Optional<Theme> theme = this.themeRepository.findByName(name);
        return theme;
    }
}
