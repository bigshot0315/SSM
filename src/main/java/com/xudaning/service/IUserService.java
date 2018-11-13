package com.xudaning.service;

import com.xudaning.domain.User;

import java.util.List;

public interface IUserService {
    void insertUser(User user);

    void deleteUser(int id);

    User findUserById(Integer id);

    List<User> findAllUsers();

    void updateUser(User user);

}
