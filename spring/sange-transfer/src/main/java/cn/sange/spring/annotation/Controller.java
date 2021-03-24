package cn.sange.spring.annotation;

import java.lang.annotation.*;

/*
 * 使用了该注解的类反射创建放入ioc容器，value为该类实例后的id
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {
    String value() default "";
}
