package cn.sange.spring.utils;

public class StringUtil {
    public static String toLowerCamelName(String simpleName) {
        String substring = simpleName.substring(0, 1);
        return substring.toLowerCase() + simpleName.substring(1);
    }
}
