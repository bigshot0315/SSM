package com.xudaning.service.impl;

import com.xudaning.domain.User;
import com.xudaning.mapper.LoginMapper;
import com.xudaning.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * shiro登录服务类
 */
@Service
public class LoginServiceImpl implements ILoginService {
    @Autowired
    private LoginMapper loginMapper;

    @Override
    public User getByUsername(String username) {
        return loginMapper.getByUsername(username);
    }

    @Override
    public Set<String> getRoles(String username) {
        return loginMapper.getRoles(username);
    }

    @Override
    public Set<String> getPermissions(String username) {
        return loginMapper.getPermissions(username);
    }
}
