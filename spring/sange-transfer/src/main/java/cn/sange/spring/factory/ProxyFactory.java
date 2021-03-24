package cn.sange.spring.factory;

import cn.sange.spring.annotation.Autowired;
import cn.sange.spring.annotation.Service;
import cn.sange.spring.utils.TransactionManager;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理对象工厂：生成代理对象
 */
@Service
public class ProxyFactory {

    @Autowired
    private TransactionManager transactionManager;

    /*
    * 对外提供代理对象：
    * 1：有接口提供jdk代理对象
    * 2：未实现接口，提供cglib代理对象
    *
    * @params target 委托类
    * @return 代理类
    */
    public Object getProxy(Object target) {
        Class<?>[] interfaces = target.getClass().getInterfaces();
        if (interfaces != null && interfaces.length > 0) {
            return getJdkProxy(target);
        }
        return getCglibProxy(target);
    }

    private Object getCglibProxy(Object target) {
        return Enhancer.create(target.getClass(), new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                try {
                    System.out.println("sange start to invoke cglib proxy method");
                    transactionManager.beginTransaction();
                    Object result = method.invoke(target, objects);
                    transactionManager.commit();
                    return result;
                } catch (Exception e) {
                    transactionManager.rollback();
                    throw new RuntimeException("jdk proxy method invoke error, method: " + method.getName());
                }
            }
        });
    }

    private Object getJdkProxy(Object target) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                try {
                    System.out.println("sange start to invoke jdk proxy method");
                    transactionManager.beginTransaction();
                    Object result = method.invoke(target, args);
                    transactionManager.commit();
                    return result;
                } catch (Exception e) {
                    transactionManager.rollback();
                    throw new RuntimeException("jdk proxy method invoke error, method: " + method.getName());
                }
            }
        });
    }


}
