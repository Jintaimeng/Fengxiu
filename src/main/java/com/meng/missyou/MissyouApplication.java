package com.meng.missyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan("com.meng")  ComponentScan扫描机制可使其也扫描com.meng这个路径,使该路径的包也扫描到该项目的IOC容器中去
public class MissyouApplication {

    public static void main(String[] args) {
        SpringApplication.run(MissyouApplication.class, args);
    }

}
