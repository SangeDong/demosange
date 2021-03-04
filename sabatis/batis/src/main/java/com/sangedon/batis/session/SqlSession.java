package com.sangedon.batis.session;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface SqlSession {
    <T> T getMapper(Class<?> mapperClass);

    public <E> List<E> selectList(String statementId, Object... params) throws SQLException, IllegalAccessException, IntrospectionException, InstantiationException, NoSuchFieldException, InvocationTargetException, ClassNotFoundException;

    public <T> T selectOne(String statementId, Object... params) throws SQLException, IllegalAccessException, IntrospectionException, InstantiationException, ClassNotFoundException, InvocationTargetException, NoSuchFieldException;

    public int delete(String statementId, Object... params);

    public int update(String statementId, Object... params);
}
