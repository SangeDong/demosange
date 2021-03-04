package com.sangedon.batis.config;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * batis 主配置文件
 * 包含数据库配置及mapper配置
 */
public class Configuration {
    private DataSource dataSource;
    private Map<String, MapperStatement> mapperStatementMap = new HashMap<>();

    private Configuration() {
    }

    private static class ConfigurationInstance {
        public static final Configuration configuration = new Configuration();
    }

    public static Configuration getInstance() {
        return ConfigurationInstance.configuration;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MapperStatement> getMapperStatementMap() {
        return mapperStatementMap;
    }

    public void setMapperStatementMap(Map<String, MapperStatement> mapperStatementMap) {
        this.mapperStatementMap = mapperStatementMap;
    }
}
