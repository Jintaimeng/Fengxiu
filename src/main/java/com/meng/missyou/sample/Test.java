package com.meng.missyou.sample;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)//Spring boot会以多例的方式进行依赖注入
//proxyMode 动态代理
//注入的是类，所以选择TARGET_CLASS,注入的是接口的话选择INTERFACE
public class Test {
    private String name = "7";
}
