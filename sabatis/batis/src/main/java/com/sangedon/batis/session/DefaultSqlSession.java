package com.sangedon.batis.session;

import com.sangedon.batis.config.Configuration;

import java.beans.IntrospectionException;
import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class DefaultSqlSession implements SqlSession {
    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String methodName = method.getName();
                String className = method.getDeclaringClass().getName();
                String statementId = className + "." + methodName;
                Type genericReturnType = method.getGenericReturnType();
                if (genericReturnType instanceof ParameterizedType) {
                    return selectList(statementId, args);
                }
                return selectOne(statementId, args);
            }
        });
        return (T) proxyInstance;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws SQLException, IllegalAccessException, IntrospectionException, InstantiationException, NoSuchFieldException, InvocationTargetException, ClassNotFoundException {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        Configuration configuration = Configuration.getInstance();
        List<Object> list = simpleExecutor.query(configuration, configuration.getMapperStatementMap().get(statementId), params);
        return (List<E>) list;
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws SQLException, IllegalAccessException, IntrospectionException, InstantiationException, ClassNotFoundException, InvocationTargetException, NoSuchFieldException {
        List<Object> objects = selectList(statementId, params);
        if (objects.size() == 1) {
            return(T) objects.get(0);
        } else {
            throw new RuntimeException("未查询到相关结果或不止一条记录");
        }
    }

    @Override
    public int delete(String statementId, Object... params) {
        return 0;
    }

    @Override
    public int update(String statementId, Object... params) {
        return 0;
    }
}
