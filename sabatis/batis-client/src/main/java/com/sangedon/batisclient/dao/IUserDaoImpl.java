package com.sangedon.batisclient.dao;

import com.sangedon.batis.resource.ResourceReader;
import com.sangedon.batis.session.SqlSession;
import com.sangedon.batis.session.SqlSessionFactory;
import com.sangedon.batis.session.SqlSessionFactoryBuilder;
import com.sangedon.batisclient.po.User;
import org.dom4j.DocumentException;

import java.beans.IntrospectionException;
import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class IUserDaoImpl implements IUserDao {
    @Override
    public List<User> findAll() throws IllegalAccessException, ClassNotFoundException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException, PropertyVetoException, DocumentException {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(ResourceReader.load("batis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();

        List<User> users = sqlSession.selectList("com.sangedon.batisclient.po.User.selectList");

        return users;
    }

    @Override
    public User findByCondition(User user) throws PropertyVetoException, DocumentException, IllegalAccessException, IntrospectionException, InstantiationException, NoSuchFieldException, SQLException, InvocationTargetException, ClassNotFoundException {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(ResourceReader.load("batis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user1 = sqlSession.selectOne("com.sangedon.batisclient.po.User.selectOne", user);
        return user1;
    }
}
