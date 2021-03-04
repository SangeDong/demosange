package com.sangedon.batisclient.dao;

import com.sangedon.batisclient.po.User;
import org.dom4j.DocumentException;

import java.beans.IntrospectionException;
import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface IUserDao {
    List<User> findAll() throws IllegalAccessException, ClassNotFoundException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException, PropertyVetoException, DocumentException;

    User findByCondition(User user) throws PropertyVetoException, DocumentException, IllegalAccessException, IntrospectionException, InstantiationException, NoSuchFieldException, SQLException, InvocationTargetException, ClassNotFoundException;
}
