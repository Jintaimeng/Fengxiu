package com.meng.missyou.optional;

import org.junit.Test;

import java.util.Optional;

public class OptionalTest {
    @Test
    public void testOptional() {
        Optional<String> empty = Optional.empty();
        //Optional<String> t1 = Optional.of(null);
        Optional<String> t2 = Optional.ofNullable(null);
        t2.ifPresent(t -> System.out.println(t));//t2不是空值，就会执行lambda表达式

        String s = t2.orElse("默认值");//t2不为空，就将值赋给s，t2为空值，s就等于“默认值”
        System.out.println(s);

        // consumer  supplier  runnable  function  predicate
        //lambda中是给你去消费，就是consumer eg：t2.ifPresent(t->System.out.println(t));
        String s1 = t2.map(t -> t + "b").orElse("c");
        System.out.println(s1);
    }
}
