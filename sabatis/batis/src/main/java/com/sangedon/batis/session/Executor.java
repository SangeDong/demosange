package com.sangedon.batis.session;

import com.sangedon.batis.config.Configuration;
import com.sangedon.batis.config.MapperStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface Executor {
    public <E> List<E> query(Configuration configuration, MapperStatement mapperStatement, Object... params) throws SQLException, ClassNotFoundException, IllegalAccessException, NoSuchFieldException, IntrospectionException, InvocationTargetException, InstantiationException;
}
