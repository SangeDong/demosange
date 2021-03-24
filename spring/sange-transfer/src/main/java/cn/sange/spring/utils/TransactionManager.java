package cn.sange.spring.utils;

import cn.sange.spring.annotation.Autowired;
import cn.sange.spring.annotation.Service;

import java.sql.SQLException;

/*
* 事物管理类
*/
@Service
public class TransactionManager {

    @Autowired
    private ConnUtil connUtil;

    /*
    * 开启事物
    */
    public void beginTransaction() throws SQLException {
        connUtil.getCurrentThreadConn().setAutoCommit(false);
    }

    /*
     * 提交事物
     */
    public void commit() throws SQLException {
        connUtil.getCurrentThreadConn().commit();
    }

    /*
     * 回滚事物
     */
    public void rollback() throws SQLException {
        connUtil.getCurrentThreadConn().rollback();
    }
}
