package com.xudaning.service.impl;

import com.xudaning.domain.Account;
import com.xudaning.mapper.AccountMapper;
import com.xudaning.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

/**
 * 账户服务类
 */
@Service
public class AccountImpl implements IAccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public void insertAccount(Account account) {

    }

    @Override
    public void deleteAccount(int id) {

    }

    @Override
    public Account findAccountById(Integer id) {
        return null;
    }

    @Override
    public List<Account> findAllAccounts() {
        return null;
    }

    @Override
    public void updateAccount(Account account) {

    }

    @Transactional
    @Override
    public void accountMoney(String transferUser, String receiveUser, Integer salary) {
        accountMapper.lessMoney(transferUser, salary);
        //模拟断电
        int i = 10 / 0;
        accountMapper.moreMoney(receiveUser, salary);
    }
}
