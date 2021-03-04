package com.sangedon.batis.session;

import com.sangedon.batis.config.Configuration;
import com.sangedon.batis.resource.XmlConfigBuilder;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

public class SqlSessionFactoryBuilder {
    public SqlSessionFactory build(InputStream is) throws DocumentException, PropertyVetoException {
        XmlConfigBuilder xmlConfigBuilder = new XmlConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(is);

        return new DefaultSqlSessionFactory(configuration);
    }
}
