package com.sangedon.batis.session;

import com.sangedon.batis.config.Configuration;

import java.sql.SQLData;

public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession();
    }
}
