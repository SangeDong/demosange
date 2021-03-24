package cn.sange.spring.utils;

import cn.sange.spring.annotation.Autowired;
import cn.sange.spring.annotation.Service;

import java.sql.Connection;
import java.sql.SQLException;

/* 
* 数据库连接工具类
*
* @params
* @return 
* @throws 
*/
@Service
public class ConnUtil {
    private volatile ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    @Autowired
    private DruidUtils druidUtils;

    /*
    * 获取当前线程数据库连接
    *
    * @return 当前线程数据库连接
    * @throws SQLException
    */
    public Connection getCurrentThreadConn() throws SQLException {
        if (threadLocal.get() == null) {
            threadLocal.set(druidUtils.getDataSource().getConnection());
        }
        return threadLocal.get();
    }
}
