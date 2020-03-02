package com.meng.missyou.sample;

import com.meng.missyou.sample.condition.DianaCondition;
import com.meng.missyou.sample.condition.IreliaCondition;
import com.meng.missyou.sample.hero.Camille;
import com.meng.missyou.sample.hero.Diana;
import com.meng.missyou.sample.hero.Irelia;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/*@Configuration 和 @Bean就可以使camille进入到IOC容器中去了*/
@Configuration
public class HeroConfiguration {
    //@Bean  //此注解要求下面的方法要返回一个bean
    //@Conditional(DianaCondition.class)//自定义条件注解
    //@ConditionalOnProperty(value = "hero.condition",havingValue = "diana",matchIfMissing = true)  //成品条件注解
    //@ConditionalOnBean(name = "mysql")  //先前IOC中  有  mysql这个Bean，则diana这个Bean就会被加载到IOC容器中
    @ConditionalOnMissingBean(name = "mysql")  //先前IOC中  没有  mysql这个Bean，则diana这个Bean就会被加载到IOC容器中
    public ISkill diana(){
        return new Diana("Diana",18);
    }
    @Bean
    //@Conditional(IreliaCondition.class)
    //@ConditionalOnProperty(value = "hero.condition",havingValue = "irelia")
    public ISkill irelia(){
        return new Irelia();
    }
}
