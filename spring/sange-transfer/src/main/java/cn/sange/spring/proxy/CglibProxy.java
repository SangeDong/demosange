package cn.sange.spring.proxy;

import cn.sange.spring.annotation.Autowired;
import cn.sange.spring.annotation.Service;
import cn.sange.spring.utils.TransactionManager;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@Service
public class CglibProxy implements MethodInterceptor {

    @Autowired
    private TransactionManager transactionManager;

    public Object getProxy(Object target) {
        return Enhancer.create(target.getClass(), this);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        try {
            System.out.println("sange start to invoke cglib proxy method");
            transactionManager.beginTransaction();
            Object result = method.invoke(o, objects);
            transactionManager.commit();
            return result;
        } catch (Exception e) {
            transactionManager.rollback();
            throw new RuntimeException("jdk proxy method invoke error, method: " + method.getName());
        }
    }
}
