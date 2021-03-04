package com.sangedon.batis.resource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.sangedon.batis.config.Configuration;
import com.sangedon.batis.config.MapperStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class XmlConfigBuilder{
    public Configuration parseConfig(InputStream is) throws DocumentException, PropertyVetoException {
        Document document = new SAXReader().read(is);
        Element rootElement = document.getRootElement();
        List<Element> nodes = rootElement.selectNodes("//property");
        Properties properties = new Properties();
        for (Element node : nodes) {
            String name = node.attributeValue("name");
            String value = node.attributeValue("value");
            properties.put(name, value);
        }

        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));

        Configuration configuration = Configuration.getInstance();
        configuration.setDataSource(comboPooledDataSource);

        List<Element> mapperNodes = rootElement.selectNodes("//mapper");
        HashMap<String, MapperStatement> statementHashMap = new HashMap<>();
        for (Element mapper : mapperNodes) {
            String path = mapper.attributeValue("path");
            InputStream inputStream = ResourceReader.load(path);
            XmlMapperBuilder mapperBuilder = new XmlMapperBuilder(configuration);
            mapperBuilder.parse(inputStream);
        }

        return configuration;
    }
}
