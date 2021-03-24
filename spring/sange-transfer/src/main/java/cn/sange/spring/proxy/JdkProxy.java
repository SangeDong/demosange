package cn.sange.spring.proxy;

import cn.sange.spring.annotation.Autowired;
import cn.sange.spring.annotation.Service;
import cn.sange.spring.utils.TransactionManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Service
public class JdkProxy implements InvocationHandler {

    @Autowired
    private TransactionManager transactionManager;

    public Object getProxy(Object target) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            System.out.println("sange start to invoke jdk proxy method");
            transactionManager.beginTransaction();
            Object result = method.invoke(proxy, args);
            transactionManager.commit();
            return result;
        } catch (Exception e) {
            transactionManager.rollback();
            throw new RuntimeException("jdk proxy method invoke error, method: " + method.getName() + e);
        }
    }
}
