package com.meng.missyou;

import com.meng.missyou.sample.EnableLOLConfiguration;
import com.meng.missyou.sample.HeroConfiguration;
import com.meng.missyou.sample.ISkill;
import com.meng.missyou.sample.hero.LOLConfigurationSelector;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/*自己编写Spring应用启动类*/


//@ComponentScan
//@Import(HeroConfiguration.class)  //需要.web(WebApplicationType.NONE)关闭服务器，不要求启动服务器
//@Import(LOLConfigurationSelector.class)
@EnableLOLConfiguration
public class LOLApplication {
    public static void main(String[] args){
        ConfigurableApplicationContext context = new SpringApplicationBuilder(LOLApplication.class)
                .web(WebApplicationType.NONE).run(args);
        ISkill iSkill = (ISkill) context.getBean("irelia");
        iSkill.r();

    }
}
