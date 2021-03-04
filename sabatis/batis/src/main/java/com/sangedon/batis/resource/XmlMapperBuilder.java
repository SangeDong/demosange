package com.sangedon.batis.resource;

import com.sangedon.batis.config.Configuration;
import com.sangedon.batis.config.MapperStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XmlMapperBuilder {
    private Configuration configuration;
    public XmlMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream inputStream) throws DocumentException {
        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();
        List<Element> selectNodes = rootElement.selectNodes("//select");
        String namespace = rootElement.attributeValue("namespace");
        for (Element selectNode : selectNodes) {
            MapperStatement mapperStatement = new MapperStatement();
            mapperStatement.setId(selectNode.attributeValue("id"));
            mapperStatement.setResultType(selectNode.attributeValue("resultType"));
            mapperStatement.setParameterType(selectNode.attributeValue("parameterType"));
            mapperStatement.setSqlStr(selectNode.getTextTrim());

            String key = namespace + "." + mapperStatement.getId();
            configuration.getMapperStatementMap().put(key, mapperStatement);
        }
    }
}
