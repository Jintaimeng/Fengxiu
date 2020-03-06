package com.meng.missyou.dto;


import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;

@Getter
//@Setter
//@AllArgsConstructor//全参构造
//@NoArgsConstructor//无参构造
//@RequiredArgsConstructor//部分参数的构造函数
//@Data 不止包括Getter和Setter方法，还包括equals、hashCode、toString等
@Builder
public class PersonDTO {
    //@NonNull//不能传入空值
    @Length(min = 5, max = 12, message = "长度错误")
    private String name;
    private Integer age;
    @Valid
    private SchoolDTO schoolDTO;
    //数据传输对象
}
