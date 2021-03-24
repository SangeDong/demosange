package cn.sange.spring.dao.impl;

import cn.sange.spring.annotation.Autowired;
import cn.sange.spring.annotation.Service;
import cn.sange.spring.pojo.Account;
import cn.sange.spring.dao.AccountDao;
import cn.sange.spring.utils.ConnUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author sange
 */
@Service
public class JdbcAccountDaoImpl implements AccountDao {

    @Autowired
    private ConnUtil connUtil;

    @Override
    public Account queryAccountByCardNo(String cardNo) throws Exception {
        Connection con = connUtil.getCurrentThreadConn();
        String sql = "select * from account where cardNo=?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1,cardNo);
        ResultSet resultSet = preparedStatement.executeQuery();

        Account account = new Account();
        while(resultSet.next()) {
            account.setCardNo(resultSet.getString("cardNo"));
            account.setName(resultSet.getString("name"));
            account.setMoney(resultSet.getInt("money"));
        }

        resultSet.close();
        preparedStatement.close();
        return account;
    }

    @Override
    public int updateAccountByCardNo(Account account) throws Exception {

        // 从连接池获取连接
        Connection con = connUtil.getCurrentThreadConn();
        String sql = "update account set money=? where cardNo=?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1,account.getMoney());
        preparedStatement.setString(2,account.getCardNo());
        int i = preparedStatement.executeUpdate();

        preparedStatement.close();
        return i;
    }
}
