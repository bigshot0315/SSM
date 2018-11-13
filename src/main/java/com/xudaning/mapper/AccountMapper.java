package com.xudaning.mapper;

import com.xudaning.domain.Account;
import com.xudaning.domain.AccountExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountMapper {
    long countByExample(AccountExample example);

    int deleteByExample(AccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Account record);

    int insertSelective(Account record);

    List<Account> selectByExample(AccountExample example);

    Account selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Account record, @Param("example") AccountExample example);

    int updateByExample(@Param("record") Account record, @Param("example") AccountExample example);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);

    /**
     * 少钱的方法
     *
     * @param username 用户名
     * @param salary   金钱
     */
    void lessMoney(@Param("username") String username, @Param("salary") Integer salary);

    /**
     * 多钱的方法
     *
     * @param username 用户名
     * @param salary   金钱
     */
    void moreMoney(@Param("username") String username, @Param("salary") Integer salary);
}