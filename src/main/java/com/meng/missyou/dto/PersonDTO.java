package com.meng.missyou.dto;


import lombok.*;

@Getter
@Setter
//@AllArgsConstructor//全参构造
@NoArgsConstructor//无参构造
@RequiredArgsConstructor//部分参数的构造函数
//@Data 不止包括Getter和Setter方法，还包括equals、hashCode、toString等
public class PersonDTO {
    @NonNull//不能传入空值
    private String name;
    private Integer age;

    //数据传输对象
}
