package com.xudaning.mapper;

import com.xudaning.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * shiro登录控制
 */
@Mapper
public interface LoginMapper {
    User getByUsername(String username);

    Set<String> getRoles(String username);

    Set<String> getPermissions(String username);
}
