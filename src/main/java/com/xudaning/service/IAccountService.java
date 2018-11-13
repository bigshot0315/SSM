package com.xudaning.service;

import com.xudaning.domain.Account;

import java.util.List;

public interface IAccountService {
    void insertAccount(Account account);

    void deleteAccount(int id);

    Account findAccountById(Integer id);

    List<Account> findAllAccounts();

    void updateAccount(Account account);

    /**
     * 转账业务
     *
     * @param transferUser 转账人
     * @param receiveUser  收款人
     * @param salary       金额
     */
    void accountMoney(String transferUser, String receiveUser, Integer salary);
}
