package com.smarthousehold.mapper;

import com.smarthousehold.pojo.User;

/**
 * @Auther:胡坚涛
 * @Data:2020/05/25/21:11
 */
public interface UserMapper {
    User findByUsername(String username);

    void addUser(User user);

    User findByCode(String code);

    void updateStatus(User user);

    void updateUser(User user);

}
