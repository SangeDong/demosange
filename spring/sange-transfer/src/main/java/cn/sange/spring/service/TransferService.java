package cn.sange.spring.service;

/**
 * @author sange
 */
public interface TransferService {

    void transfer(String fromCardNo,String toCardNo,int money) throws Exception;
}
