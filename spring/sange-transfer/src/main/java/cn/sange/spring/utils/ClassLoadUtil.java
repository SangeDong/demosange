package cn.sange.spring.utils;

import cn.hutool.core.lang.ClassScaner;

import java.util.*;
import java.util.stream.Collectors;

/*
* 获取相关包路径下所有类的工具类
*/
public class ClassLoadUtil {
    public static String transformBasePackage(String basePackage) {
        if (isClassPath(basePackage)) {
            return "";
        }
        return basePackage;
    }

    private static boolean isClassPath(String basePackage) {
        return basePackage.equalsIgnoreCase("/")
                || basePackage.equalsIgnoreCase("/*")
                || basePackage.equalsIgnoreCase("/**");
    }

    /*
    * 加载某个包下面的所有类
    *
    * @params basePackage 带扫描的包
    * @return 所有类的class对象集合
    */
    public static Set<Class<?>> load(String basePackage) {
        return ClassScaner.scanPackage(transformBasePackage(basePackage));
    }

    /*
    * 获取某个包下面含有某个注解的所有类
    *
    * @params basePackage 待扫描包
    * @params annotationClass 注解class对象
    * @return 含有某个注解的所有类对象
    */
    public static Set<Class<?>> loadByAnnotation(String basePackage, final Class annotationClass) {
        return load(basePackage).stream().filter(item -> {
            if (item.getAnnotation(annotationClass) != null) {
                return true;
            }
            return false;
        }).collect(Collectors.toSet());
    }
}
