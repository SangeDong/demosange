package cn.sange.spring.annotation;

import java.lang.annotation.*;

/*
* 事物开启注解
*/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Transactional {
}
