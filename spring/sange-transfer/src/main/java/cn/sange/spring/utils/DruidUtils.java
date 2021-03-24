package cn.sange.spring.utils;

import com.alibaba.druid.pool.DruidDataSource;
import cn.sange.spring.annotation.Service;

import javax.sql.DataSource;

/**
 * Druid连接池
 */
@Service
public class DruidUtils {

    private static DruidDataSource druidDataSource = new DruidDataSource();

    static {
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://192.168.100.10:3306/bank");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("123456");
    }

    public DataSource getDataSource() {
        return druidDataSource;
    }
}
