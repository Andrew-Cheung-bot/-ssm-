package com.how2java.service;

import com.how2java.pojo.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 注册用户
 * @Auther:胡坚涛
 * @Data:2020/05/25/21:10
 */
public interface UserService extends UserDetailsService {
    boolean regist(User user);

    boolean active(String code);

    //boolean update(User user);

    //User login(User user);

}
