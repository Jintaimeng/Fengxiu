package com.meng.missyou.api.v1;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.meng.missyou.exception.http.NotFoundException;
import com.meng.missyou.model.Theme;
import com.meng.missyou.service.ThemeService;
import com.meng.missyou.vo.ThemePureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("theme")
@Validated
public class ThemeController {
    @Autowired
    private ThemeService themeService;

    @GetMapping("/by/names")
    public List<ThemePureVO> getThemeGroupByNames(@RequestParam(name = "names") String names) {
        List<String> nameList = Arrays.asList(names.split(","));
        List<Theme> themes = this.themeService.findByNames(nameList);
        List<ThemePureVO> list = new ArrayList<>();
        themes.forEach(s -> {
            Mapper mapper = DozerBeanMapperBuilder.buildDefault();
            ThemePureVO themePureVO = mapper.map(s, ThemePureVO.class);
            list.add(themePureVO);
        });
        return list;
    }

    @GetMapping("/name/{name}/with_spu")
    public Optional<Theme> getThemeByNameWithSpu(@PathVariable(name = "name") String themeName) {
        Optional<Theme> optionalTheme = this.themeService.findByName(themeName);
        if (optionalTheme == null) {
            throw new NotFoundException(30003);
        }
        return optionalTheme;
    }

}
