package com.xudaning.service;

import com.xudaning.domain.User;

import java.util.Set;

/**
 * shiro登录接口
 */
public interface ILoginService {
    User getByUsername(String username);

    Set<String> getRoles(String username);

    Set<String> getPermissions(String username);
}
