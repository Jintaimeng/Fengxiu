package com.meng.missyou.sample.hero;

import com.meng.missyou.sample.HeroConfiguration;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class LOLConfigurationSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[] {HeroConfiguration.class.getName()};
    }
}
