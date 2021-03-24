package cn.sange.spring.dao;

import cn.sange.spring.pojo.Account;

/**
 * @author sange
 */
public interface AccountDao {

    Account queryAccountByCardNo(String cardNo) throws Exception;

    int updateAccountByCardNo(Account account) throws Exception;
}
