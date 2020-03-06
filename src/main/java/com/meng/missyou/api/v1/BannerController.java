package com.meng.missyou.api.v1;

import com.meng.missyou.dto.PersonDTO;
import com.meng.missyou.sample.ISkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Controller
/*若在类上加上@ResponseBody ，则不需要在每个方法上写@ResponseBody
 * @Controller和@ResponseBody可以用@RestController代替，
 * @RestController包括前面两者*/

/*若url前缀都一样，可在类上使用@RequestMapping("/v1/banner")*/
//@RequestMapping("/banner")
@Validated //使参数校验生效
public class BannerController {
    @Autowired //将Diana注入进来  @Autowired(required = false)说明允许为空值
    //@Qualifier("irelia")//强制注入irelia
    private ISkill iSkill;  //属性注入的方法
//    @Autowired
//    private IConnect iConnect;
//    @Autowired
//    public void setDiana(Diana diana) {//setter注入
//        this.diana = diana;
//    }

//    @Autowired
//    public BannerController(Diana diana){//构造注入
//        this.diana = diana;
//    }

    //MVC SPringMVC

//    @PostMapping
//    @PutMapping
//    @DeleteMapping
//    public void test(HttpServletResponse response) throws IOException {
//        response.getWriter().write("Hello,TaiMeng");
//        //return "Hello,TaiMeng";
//    }
//@RequestMapping(value = "/test",method = {RequestMethod.GET,
// RequestMethod.POST})  RequestMapping比GetMapping功能更强大
@PostMapping("/test/{id}") //路由  RESTFulAPI
@ResponseBody

public PersonDTO test(@PathVariable @Max(value = 10, message = "不可以超过10") Integer id, @RequestParam @Min(4) String name,
                      @RequestBody @Validated PersonDTO person) {
    iSkill.r();//此处就不用实例化了
    PersonDTO dto = PersonDTO.builder().name("taimeng").age(18).build();
    //throw new ForbiddenException(10000);
    //throw new Exception("这里错了");
    return dto;
}

//    @GetMapping("/test1")
//    @ResponseBody
//    public void test1(){
//        iConnect.connect();
//    }
    //POSTMan  单元测试
}
