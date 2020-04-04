package com.meng.missyou.api.v1;

import com.meng.missyou.sample.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    //private ObjectFactory<Test> test;
    private Test test;

    @GetMapping("")
    public void getDetail() {
        //System.out.println(this.test.getObject());
        System.out.println(this.test);
    }
}
