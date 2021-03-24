package cn.sange.spring.service.impl;

import cn.sange.spring.annotation.Service;
import cn.sange.spring.annotation.Transactional;
import cn.sange.spring.pojo.Account;
import cn.sange.spring.service.TransferService;
import cn.sange.spring.annotation.Autowired;
import cn.sange.spring.dao.AccountDao;

/**
 * @author sange
 */
@Service
@Transactional
public class  TransferServiceImpl implements TransferService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {
        Account from = accountDao.queryAccountByCardNo(fromCardNo);
        Account to = accountDao.queryAccountByCardNo(toCardNo);

        from.setMoney(from.getMoney()-money);
        to.setMoney(to.getMoney()+money);

        accountDao.updateAccountByCardNo(to);
//        int a = 1 / 0;
        accountDao.updateAccountByCardNo(from);
    }
}
