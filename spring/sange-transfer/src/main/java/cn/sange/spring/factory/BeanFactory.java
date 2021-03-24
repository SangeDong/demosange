package cn.sange.spring.factory;

import cn.sange.spring.annotation.Controller;
import cn.sange.spring.annotation.Service;
import cn.sange.spring.annotation.Transactional;
import cn.sange.spring.pojo.ControllerHolder;
import cn.sange.spring.utils.ClassLoadUtil;
import cn.sange.spring.annotation.Autowired;
import cn.sange.spring.utils.StringUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 工厂类，扫描相关包下面类，为相应注解的类创建对象
 */
public class BeanFactory {
    private static BeanFactory beanFactory = new BeanFactory();

    private BeanFactory(){
    }

    /*
    * 对外提供bean工厂对象
    * @return bean工厂单利对象
    */
    public static BeanFactory getInstance() {
        return beanFactory;
    }

    private static Map<String,Object> singletonObjects = new HashMap<>();  // 存储对象

    private static Map<String,Object> webObjects = new HashMap<>();  // 存储servlet对象

    /*
    * 容器启动方法：
    * 1. 扫描相关包，为Controller，Service等注解类创建对象
    * 2. 为Autowired注解的字段进行属性填充
    * 3. 替换添加了Transactial注解的依赖为代理对象
    *
    * @params basePackage 扫描的包路径
    */
    public void refresh(String basePackage) {
        if (!singletonObjects.isEmpty()) {
            singletonObjects.clear();
        }
        try {
            Set<Class<?>> serviceClasses = ClassLoadUtil.loadByAnnotation(basePackage, Service.class);
            Set<Class<?>> controllerClasses = ClassLoadUtil.loadByAnnotation(basePackage, Controller.class);
            serviceClasses.addAll(controllerClasses);

            createBean(serviceClasses);
            populateBean(serviceClasses);
            beanPostProcessor(serviceClasses);
            System.out.println("sange");
        } catch (IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createBean(Set<Class<?>> classes) {
        doCreatServiceBean(classes);
        doCreatWebBean(classes);
    }

    private void doCreatWebBean(Set<Class<?>> classes) {
        try {
            for (Class<?> aClass : classes) {
                Constructor<?> constructor = aClass.getConstructor();
                Object o = constructor.newInstance();
                Controller annotation = aClass.getAnnotation(Controller.class);
                if (annotation == null) {
                    continue;
                }
                String value = annotation.value();
                singletonObjects.put(aClass.getName(), o);
                String classId = StringUtil.toLowerCamelName(aClass.getSimpleName());
                singletonObjects.put(classId, o);

                webObjects.put(classId, new ControllerHolder(value, o));
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("creat classes error");
        }
    }

    private void doCreatServiceBean(Set<Class<?>> classes) {
        try {
            for (Class<?> aClass : classes) {
                Constructor<?> constructor = aClass.getConstructor();
                Object o = constructor.newInstance();
                Service annotation = aClass.getAnnotation(Service.class);
                if (annotation == null) {
                    continue;
                }
                String value = annotation.value();
                singletonObjects.put(aClass.getName(), o);

                Class<?>[] interfaces = aClass.getInterfaces();
                for (Class<?> anInterface : interfaces) {
                    singletonObjects.put(anInterface.getName(), o);
                }

                if (value.isEmpty()) {
                    String classId = StringUtil.toLowerCamelName(aClass.getSimpleName());
                    singletonObjects.put(classId, o);
                    continue;
                }
                singletonObjects.put(value, o);
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("creat classes error");
        }
    }

    private void populateBean(Set<Class<?>> classes) throws IllegalAccessException {
        for (Class<?> aClass : classes) {
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field field : declaredFields) {
                Autowired autowired = field.getAnnotation(Autowired.class);
                if (autowired == null) {
                    continue;
                }

                String value = autowired.value();
                if (!value.isEmpty()) {
                    field.setAccessible(true);
                    field.set(singletonObjects.get(aClass.getName()), singletonObjects.get(value));
                    continue;
                }

                field.setAccessible(true);
                field.set(singletonObjects.get(aClass.getName()), singletonObjects.get(field.getType().getName()));
            }
        }
    }

    private void beanPostProcessor(Set<Class<?>> allClasses) throws IllegalAccessException, ClassNotFoundException {
        createProxyBean(allClasses);
        replaceByProxy(allClasses);
    }

    private void createProxyBean(Set<Class<?>> allClasses) {
        for (Class<?> aClass : allClasses) {
            if (aClass.getAnnotation(Transactional.class) == null) {
                continue;
            }
            Object target = singletonObjects.get(aClass.getName());
            ProxyFactory proxy = (ProxyFactory)singletonObjects.get(ProxyFactory.class.getName());
            Object targetProxy = proxy.getProxy(target);

            singletonObjects.put(aClass.getName(), targetProxy);

            Service service = aClass.getAnnotation(Service.class);
            String value = service.value();
            if (value.isEmpty()) {
                String classId = StringUtil.toLowerCamelName(aClass.getSimpleName());
                singletonObjects.put(classId, targetProxy);
            } else {
                singletonObjects.put(value, targetProxy);
            }

            Class<?>[] interfaces = aClass.getInterfaces();
            for (Class<?> anInterface : interfaces) {
                singletonObjects.put(anInterface.getName(), targetProxy);
            }
        }
    }

    private void replaceByProxy(Set<Class<?>> allClasses) throws IllegalAccessException, ClassNotFoundException {
        for (Class<?> aClass : allClasses) {
            if (aClass.getAnnotation(Controller.class) == null) {
                continue;
            }
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field field : declaredFields) {
                Autowired autowired = field.getAnnotation(Autowired.class);
                if (autowired == null) {
                    continue;
                }

                Object target = singletonObjects.get(field.getType().getName());
                String[] split = target.toString().split("@");
                Class<?> forName = Class.forName(split[0]);
                if (forName.getAnnotation(Transactional.class) == null) {
                    continue;
                }

                field.setAccessible(true);
                field.set(singletonObjects.get(aClass.getName()), target);
            }
        }
    }

    /*
    * 根据id从IoC容器中获取对象
    *
    * @params id 对象Id
    * @return 对象
    */
    public static  Object getBean(String id) {
        return singletonObjects.get(id);
    }

    /*
     * 获取servlet对象
     *
     * @return servlet对象集合
     */
    public static Map<String, Object> getWebObjects() {
        return webObjects;
    }

    /*
     * 容器销毁方法
     */
    public void destroy() {
        singletonObjects.clear();
        webObjects.clear();
    }
}
