package com.sangedon.batis.session;

import com.sangedon.batis.config.Configuration;
import com.sangedon.batis.config.MapperStatement;
import com.sangedon.batis.util.GenericTokenParser;
import com.sangedon.batis.util.ParameterMapping;
import com.sangedon.batis.util.ParameterMappingTokenHandler;
import com.sangedon.batis.util.TokenHandler;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecutor implements Executor{
    @Override
    public <E> List<E> query(Configuration configuration, MapperStatement mapperStatement, Object... params) throws SQLException, ClassNotFoundException, IllegalAccessException, NoSuchFieldException, IntrospectionException, InvocationTargetException, InstantiationException {
        Connection connection = configuration.getDataSource().getConnection();
        String sqlStr = mapperStatement.getSqlStr();
        ExecutableSql executableSql = parseParam(sqlStr);
        PreparedStatement preparedStatement = connection.prepareStatement(executableSql.getParseSql());

        String parameterType = mapperStatement.getParameterType();
        Class<?> clazz = getClasstype(parameterType);

        List<ParameterMapping> parameterMappings = executableSql.getParameterMappings();
        for (int i = 0; i < parameterMappings.size(); i++) {
            ParameterMapping parameterMapping = parameterMappings.get(i);
            String content = parameterMapping.getContent();

            Field declaredField = clazz.getDeclaredField(content);
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);

            preparedStatement.setObject(i + 1, o);
        }

        ArrayList<Object> objects = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();
        String resultType = mapperStatement.getResultType();
        Class<?> resultTypeClass = getClasstype(resultType);

        while (resultSet.next()) {
            Object instance = resultTypeClass.newInstance();
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String columnName = metaData.getColumnName(i);
                Object object = resultSet.getObject(columnName);

                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(instance, object);
            }
            objects.add(instance);
        }
        return (List<E>) objects;
    }

    private Class<?> getClasstype(String parameterType) throws ClassNotFoundException {
        if (parameterType != null && !parameterType.isEmpty()) {
            return Class.forName(parameterType);
        }
        return null;
    }

    private ExecutableSql parseParam(String sqlStr) {
        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", tokenHandler);
        String parseSql = genericTokenParser.parse(sqlStr);
        List<ParameterMapping> parameterMappings = tokenHandler.getParameterMappings();
        ExecutableSql executableSql = new ExecutableSql();
        executableSql.setParameterMappings(parameterMappings);
        executableSql.setParseSql(parseSql);
        return executableSql;
    }
}
