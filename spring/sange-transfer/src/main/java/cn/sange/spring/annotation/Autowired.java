package cn.sange.spring.annotation;

import java.lang.annotation.*;

/*
 * 使用了该注解的实例数据注入相关依赖，value可用来指定相关对象id
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    String value() default "";
}
