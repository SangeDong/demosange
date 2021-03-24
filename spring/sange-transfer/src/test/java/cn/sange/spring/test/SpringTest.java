package cn.sange.spring.test;

import cn.sange.spring.factory.BeanFactory;
import org.junit.Test;

public class SpringTest {

    @Test
    public void test1() {
        BeanFactory.getInstance().refresh("");
        Object transferServiceImpl = BeanFactory.getBean("transferServiceImpl");
        System.out.println(111);
    }
}
