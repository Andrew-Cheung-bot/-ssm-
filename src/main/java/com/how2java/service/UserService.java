package com.how2java.service;

import com.how2java.pojo.User;

/**
 * 注册用户
 * @Auther:胡坚涛
 * @Data:2020/05/25/21:10
 */
public interface UserService {
    boolean regist(User user);

    boolean active(String code);

    User login(User user);
}
