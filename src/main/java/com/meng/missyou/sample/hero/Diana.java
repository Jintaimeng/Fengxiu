package com.meng.missyou.sample.hero;

import com.meng.missyou.sample.ISkill;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

//@Component   //SpringBoot会将Diana注入到容器中，来负责对象的实例化
//@Lazy  延迟实例化
public class Diana implements ISkill {
    private String name;
    private Integer age;
    public Diana(String name,Integer age){
        this.name = name;
        this.age = age;
    }
    public Diana(){
        System.out.println("Hello,TaiMeng~");
    }
    public void q(){
        System.out.println("Diana Q");
    }
    public void w(){
        System.out.println("Diana W");
    }
    public void e(){
        System.out.println("Diana E");
    }
    public void r(){
        System.out.println("Diana R");
    }
}
