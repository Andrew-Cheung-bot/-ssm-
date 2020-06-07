package com.smarthousehold.service;

import com.smarthousehold.pojo.User;
import com.smarthousehold.pojo.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * 注册用户
 * @Auther:胡坚涛
 * @Data:2020/05/25/21:10
 */
public interface UserService extends UserDetailsService {
    boolean regist(User user);

    boolean active(String code);

    boolean update(User user);

    //User login(User user);
    List<User> findAll(Integer page, Integer size);

    boolean addUser(User user);

    boolean delUserByUsername(String username);

    User findByUsername(String username);

    boolean editUserByUsername(User user);

    List searchAutoPrompt(String key);

    List<User> searchByUsername(String string);

    Boolean addUserCurtain(String username, String[] id);

    Boolean addUserFan(String username, String[] id);

    UserInfo findDetail(String username);
}
