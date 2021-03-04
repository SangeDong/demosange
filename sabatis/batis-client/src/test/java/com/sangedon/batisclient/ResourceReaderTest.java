package com.sangedon.batisclient;

import com.sangedon.batis.resource.ResourceReader;
import com.sangedon.batis.resource.XmlConfigBuilder;
import com.sangedon.batis.session.SqlSession;
import com.sangedon.batis.session.SqlSessionFactory;
import com.sangedon.batis.session.SqlSessionFactoryBuilder;
import com.sangedon.batisclient.dao.IUserDao;
import com.sangedon.batisclient.po.User;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;

public class ResourceReaderTest {

    @Test
    public void hello() throws Exception {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(ResourceReader.load("batis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();

        IUserDao iUserDao = sqlSession.getMapper(IUserDao.class);
       /* List<User> all = iUserDao.findAll();
        for (User user1 : all) {
            System.out.println(user1);
        }*/

        User user = new User();
        user.setId(1);
        user.setUsername("lucy");
        User byCondition = iUserDao.findByCondition(user);
        System.out.println(byCondition);
    }

}
